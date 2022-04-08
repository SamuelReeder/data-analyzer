from __future__ import absolute_import, division, print_function, unicode_literals

import json

import pandas as pd
import tensorflow as tf
import numpy as np
import matplotlib.pyplot as plt
from IPython.display import clear_output
# import six.moves
from six.moves import urllib
import csvdata as data
# from keras import layers
# import keras
from tensorflow.keras import layers


# layerss = tf.keras.layers 

np.set_printoptions(precision=3, suppress=True)

# import shutil

# from tensorflow.compat.v2.feature_column as fc

training = 'https://storage.googleapis.com/tf-datasets/titanic/train.csv'
testing = 'https://storage.googleapis.com/tf-datasets/titanic/eval.csv'
dfeval = pd.read_csv('https://storage.googleapis.com/tf-datasets/titanic/eval.csv')
csv = data.CSVData(training, testing)

y_train = csv.get_train()
# y_train = csv.get_train().pop('survived')
# y_eval = csv.get_test().pop('survived')
features = y_train.copy()
labels = features.pop("survived")

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
norm.adapt(np.array(y_train[numeric_inputs.keys()]))
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

titanic_preprocessing = tf.keras.Model(inputs, preprocessed_inputs_cat)

tf.keras.utils.plot_model(model = titanic_preprocessing , rankdir="LR", dpi=72, show_shapes=True)

features_dict = {name: np.array(value) 
                 for name, value in features.items()}

feat_dict = {name:values[:1] for name, values in features_dict.items()}
titanic_preprocessing(feat_dict)

def titanic_model(preprocessing_head, inputs):
  body = tf.keras.Sequential([
    layers.Dense(64),
    layers.Dense(1)
  ])

  preprocessed_inputs = preprocessing_head(inputs)
  result = body(preprocessed_inputs)
  model = tf.keras.Model(inputs, result)

  model.compile(loss=tf.losses.BinaryCrossentropy(from_logits=True),
                optimizer=tf.optimizers.Adam())
  return model

titanic_model = titanic_model(titanic_preprocessing, inputs)

titanic_model.fit(x=features_dict, y=labels, epochs=2)

print(titanic_model)
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
