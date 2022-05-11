from __future__ import absolute_import, division, print_function, unicode_literals

import sys
import tensorflow as tf
import preprocessing as p
import numpy as np
from tensorflow.keras import layers

# class CustomModel():
#   def __init__(self) -> None:
      
# training = sys.argv[1]
# testing = ''
# if sys.argv[2] != 'none':
#     testing = sys.argv[2]

# alg = sys.argv[3]

# responsive = sys.argv[4]

# if len(sys.argv) > 5:
#     responsive = []
#     for i in len(sys.argv) - 4:
#         responsive[i] = sys.argv[i + 4]

# training = 'https://storage.googleapis.com/tf-datasets/titanic/train.csv'
# testing = 'https://storage.googleapis.com/tf-datasets/titanic/eval.csv'

training = "http://archive.ics.uci.edu/ml/machine-learning-databases/auto-mpg/auto-mpg.data"
testing = ""

responsive = "MPG"

alg = "opt"

d = p.PreProcessing(responsive, training, testing, True)

inputs, numeric_inputs = d.defineInput(False)

r_inputs, r_numeric_inputs = d.defineInput(True)

print(d.isItClassification(responsive))

def trainClassificationNeuralNet():
  preprocessed_inputs = d.preprocess()

  preprocessing = tf.keras.Model(inputs, preprocessed_inputs)

  features_dict, feat_dict = d.defineFeaturesDict(d.features)

  preprocessing(feat_dict)

  test_features_dict, test_feat_dict = d.defineFeaturesDict(d.test_features)

  def model(preprocessing_head, inputs):
    body = tf.keras.Sequential([
      layers.Dense(64),
      layers.Dense(1)
    ])

    preprocessed_inputs = preprocessing_head(inputs)
    result = body(preprocessed_inputs)
    temp_model = tf.keras.Model(inputs, result)

    temp_model.compile(loss=tf.losses.BinaryCrossentropy(from_logits=True),
                  optimizer=tf.optimizers.Adam())
    return temp_model

  model = model(preprocessing, inputs)
  model.fit(x=features_dict, y=d.labels, epochs=10)

  results = model.evaluate(x=features_dict, y=d.labels, batch_size=128)
  print(results)

  model.save('models/model')

def build_and_compile_model(norm):
  model = tf.keras.Sequential([
      norm,
      layers.Dense(64, activation='relu'),
      layers.Dense(64, activation='relu'),
      layers.Dense(1)
  ])

  model.compile(loss='mean_absolute_error',
                optimizer=tf.keras.optimizers.Adam(0.001))
  return model


def trainRegressionNeuralNet():
  normalizer = tf.keras.layers.Normalization(axis=-1)
  normalizer.adapt(np.array(d.features))

  dnn_model = build_and_compile_model(normalizer)
  
  history = dnn_model.fit(
    d.features,
    d.labels,
    validation_split=0.2,
    verbose=0, epochs=100)
  
  test_results = {}

  test_results['dnn_model'] = dnn_model.evaluate(d.test_features, d.test_labels, verbose=0)

  test = 5
  print(d.test[:test])
  print(d.test_features[:test])

  test_predictions = dnn_model.predict(d.test_features[:test])

  print(test_predictions)

  dnn_model.save('models/dnn_model')



if r_inputs[responsive].dtype == 'float32' and (alg == 'regress' or alg == 'opt') and not d.isItClassification(responsive):
  print("Hey wassup")
  trainRegressionNeuralNet()
else:
  trainClassificationNeuralNet()
