from __future__ import absolute_import, division, print_function, unicode_literals

import sys
# from matplotlib import testing
import tensorflow as tf
import numpy as np
import preprocessing as p
import csvdata as data
from tensorflow.keras import layers

np.set_printoptions(precision=3, suppress=True)

print('0 is ' + str(sys.argv[0]))
print('1 is ' + str(sys.argv[1]))
print('2 is ' + str(sys.argv[2]))
print('3 is ' + str(sys.argv[3]))

training = sys.argv[1]
testing = ''
if sys.argv[2] != 'none':
    testing = sys.argv[2]

print('test', testing)
print('train', training)

# training = 'https://storage.googleapis.com/tf-datasets/titanic/train.csv'
# testing = 'https://storage.googleapis.com/tf-datasets/titanic/eval.csv'

# training = 'C:/Users/samue/Downloads/archive/The Top Billionaires.csv'
# testing = 'C:/Users/samue/Downloads/archive/The Top Billionaires.csv'
# csv = data.CSVData(training, testing, False)

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