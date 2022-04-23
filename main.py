from __future__ import absolute_import, division, print_function, unicode_literals

import sys
import tensorflow as tf
import preprocessing as p
from tensorflow.keras import layers

training = sys.argv[1]
testing = ''
if sys.argv[2] != 'none':
    testing = sys.argv[2]

d = p.PreProcessing(sys.argv[3], training, testing, False)

inputs, numeric_inputs = d.defineInput()

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