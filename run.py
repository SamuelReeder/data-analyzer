from __future__ import absolute_import, division, print_function, unicode_literals

import json

import pandas as pd
import tensorflow as tf
import numpy as np
import matplotlib.pyplot as plt
from IPython.display import clear_output
# import six.moves
from six.moves import urllib
import preprocessing as p
import csvdata as data
from tensorflow.keras import layers

np.set_printoptions(precision=3, suppress=True)

training = 'https://storage.googleapis.com/tf-datasets/titanic/train.csv'
testing = 'https://storage.googleapis.com/tf-datasets/titanic/eval.csv'
csv = data.CSVData(training, testing, False)

actual_data = p.PreProcessing(csv)

train = csv.get_train()
test = csv.get_test()
features = train.copy()

labels = features.pop("survived")

test_features = test.copy()

# NUM_COLS = csv.get_num_cols()
# TEXT_COLS = csv.get_text_cols()

# csv.define_columns()

inputs = {}

for name, column in features.items():
  dtype = column.dtype
  if dtype == object:
    dtype = tf.string
  else:
    dtype = tf.float32

  inputs[name] = tf.keras.Input(shape=(1,), name=name, dtype=dtype)

print(inputs)

numeric_inputs = {name:input for name,input in inputs.items()
                  if input.dtype==tf.float32}

x = layers.Concatenate()(list(numeric_inputs.values()))
norm = layers.Normalization()
norm.adapt(np.array(train[numeric_inputs.keys()]))
all_numeric_inputs = norm(x)

preprocessed_inputs = [all_numeric_inputs]

for name, input in inputs.items():
  if input.dtype == tf.float32:
    continue

  lookup = layers.StringLookup(vocabulary=np.unique(features[name]))
  one_hot = layers.CategoryEncoding(num_tokens=lookup.vocabulary_size())

  x = lookup(input)
  x = one_hot(x)
  preprocessed_inputs.append(x)

preprocessed_inputs_cat = layers.Concatenate()(preprocessed_inputs)

preprocessing = tf.keras.Model(inputs, preprocessed_inputs_cat)

tf.keras.utils.plot_model(model = preprocessing , rankdir="LR", dpi=72, show_shapes=True)

features_dict = {name: np.array(value) 
                 for name, value in features.items()}

feat_dict = {name:values[:1] for name, values in features_dict.items()}
preprocessing(feat_dict)

test_features_dict = {name: np.array(value) 
                 for name, value in test_features.items()}

test_feat_dict = {name:values[:1] for name, values in test_features_dict.items()}

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

print(type(features_dict))
print(type(labels))

model.fit(x=features_dict, y=labels, epochs=1)

results = model.evaluate(x=features_dict, y=labels, batch_size=128)
print(results)

sample = {
  'sex': 'female',	
  'age': 22,
  'n_siblings_spouses': 1,
  'parch': 0,
  'fare': 7.25,	
  'class': 'first',	
  'deck': 'unknown',
  'embark_town': 'Southampton',
  'alone': 'n'

}

input_dict = {name: tf.convert_to_tensor([value]) for name, value in sample.items()}

predict = model.predict(input_dict)
prob = tf.nn.sigmoid(predict[0])

print(
    "This persoj had a %.1f percent probability "
    "of surviving." % (100 * prob)
)

# print(results)


# titanic_model.save('saved_models/model')


# print(NUM_COLS)
# print(TEXT_COLS)

# normalizer = layers.Normalization()

# model = tf.keras.Sequential([
#     normalizer,
#     layers.Dense(64), 
#     layers.Dense(1)
# ])

# model.compile(loss = tf.keras.losses.MeanSquaredError(),
#     optimizer = tf.optimizers.Adam())

# model.fit(features, labels, epochs=2)
