from __future__ import absolute_import, division, print_function, unicode_literals

import sys
import tensorflow as tf
import preprocessing as p
import numpy as np
from tensorflow.keras import layers
from tensorflow import feature_column


if sys.argv[1] == "test":
  training = 'https://storage.googleapis.com/tf-datasets/titanic/train.csv'
  testing = 'https://storage.googleapis.com/tf-datasets/titanic/eval.csv'
  responsive = "fare"
  alg = "Regression"
  epochs = 7

  # training = 'C:/Users/samue/Downloads/epidemiology.csv'
  # testing = None
  # responsive = "new_recovered"
  # alg = "Regression"
elif sys.argv[1] == "test2":
  training = "http://archive.ics.uci.edu/ml/machine-learning-databases/auto-mpg/auto-mpg.data"
  testing = ""
  responsive = "MPG"
  alg = "Optimize"
else:
  training = sys.argv[1]
  testing = None
  if sys.argv[2] != 'none':
      testing = sys.argv[2]

  alg = sys.argv[3]

  epochs = int(sys.argv[4])

  responsive = sys.argv[5]

d = p.PreProcessing(responsive, training, testing, True if testing is None else False)

r_inputs, r_numeric_inputs = d.defineInput(True)


def model(preprocessing_head, inputs):

    body = tf.keras.Sequential([
      layers.Dense(64),
      # layers.Dense(len(d.train.columns), activation="relu"),
      layers.Dense(1)
    ])

    preprocessed_inputs = preprocessing_head(inputs)
    result = body(preprocessed_inputs)
    temp_model = tf.keras.Model(inputs, result)

    temp_model.compile(loss=tf.losses.BinaryCrossentropy(from_logits=True),
                  optimizer=tf.optimizers.Adam())
    return temp_model

def trainClassificationNeuralNet(d):

  inputs, numeric_inputs = d.defineInput(False)

  # print(inputs)

  preprocessed_inputs = d.preprocess()

  # print(preprocessed_inputs)

  preprocessing = tf.keras.Model(inputs, preprocessed_inputs)

  features_dict, feat_dict = d.defineFeaturesDict(d.features)

  preprocessing(feat_dict)

  test_features_dict, test_feat_dict = d.defineFeaturesDict(d.test_features)

  class_model = model(preprocessing, inputs)
  class_model.fit(x=features_dict, y=d.labels, epochs=epochs)

  results = class_model.evaluate(x=features_dict, y=d.labels, batch_size=128)
  print(results)

  class_model.save('models/test_model')

  with open('results.txt', 'w+') as f:
        f.write(str(results))

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
    verbose=0, epochs=epochs)
  
  test_results = {}

  test_results['dnn_model'] = dnn_model.evaluate(d.test_features, d.test_labels, verbose=0)

  test = 1
  print(d.test_features[:test])

  test_predictions = dnn_model.predict(d.test_features[:test])

  print(test_predictions)

  dnn_model.save('models/test_dnn_model')

def trainAlternate():
  batch_size = 5
  train_ds = d.df_to_dataset(d.train, batch_size=batch_size)
  # val_ds = df_to_dataset(d.val, shuffle=False, batch_size=batch_size)
  test_ds = d.df_to_dataset(d.test, shuffle=False, batch_size=batch_size)
  example_batch = next(iter(train_ds))[0]

  print(train_ds)

  def demo(feature_column):
    feature_layer = layers.DenseFeatures(feature_column)
    print(feature_layer(example_batch).numpy())

  # photo_count = feature_column.numeric_column('fare')
  # demo(photo_count)



if r_inputs[responsive].dtype == 'float32' and (alg == 'Regression' or alg == 'Optimize') and not p.PreProcessing.isItClassification(d.train, responsive):
  trainRegressionNeuralNet()
else:
  # trainClassificationNeuralNet(d)
  trainAlternate()
