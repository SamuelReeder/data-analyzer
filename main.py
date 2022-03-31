
from __future__ import absolute_import, division, print_function, unicode_literals

import os
import sys

import pandas as pd
import tensorflow as tf
import numpy as np
import matplotlib.pyplot as plt
from IPython.display import clear_output
from six.moves import urllib
import csvdata as data

import tensorflow.compat.v2.feature_column as fc
# import csvdata as data

training = 'https://storage.googleapis.com/tf-datasets/titanic/train.csv'
testing = 'https://storage.googleapis.com/tf-datasets/titanic/eval.csv'
dfeval = pd.read_csv('https://storage.googleapis.com/tf-datasets/titanic/eval.csv')
csv = data.CSVData(training, testing)
y_train = csv.get_train().pop('survived')
y_eval = csv.get_test().pop('survived')

NUM_COLS = csv.get_numeric_columns()
TEXT_COLS = csv.get_numeric_columns()

print(csv.get_numeric_columns())
print(csv.get_text_columns())

feature_columns = []
for feature in TEXT_COLS:
  vocab = csv.get_train()[feature].unique()
  feature_columns.append(tf.feature_column.categorical_column_with_vocabulary_list(feature, vocab))

for feature in NUM_COLS:
  feature_columns.append(tf.feature_column.numeric_column(feature, dtype=tf.float32))

# def make_input_fn(data_df, label_df, num_epochs=10, shuffle=True, batch_size=32):
#     def input_function():
#         ds = tf.data.Dataset.from_tensor_slices((dict(data_df), label_df))
#         if shuffle:
#         ds = ds.shuffle(1000)
#         ds = ds.batch(batch_size).repeat(num_epochs)
#         return ds
#     return input_function

train_input_fn = make_input_fn(dftrain, y_train)
eval_input_fn = make_input_fn(dfeval, y_eval, num_epochs=1, shuffle=False)

# mnist = tf.keras.datasets.mnist

# (x_train, y_train),(x_test, y_test) = mnist.load_data()
# x_train, x_test = x_train / 255.0, x_test / 255.0

# model = tf.keras.models.Sequential([
#   tf.keras.layers.Flatten(input_shape=(csv.get_features(), 1)),
#   tf.keras.layers.Dense(128, activation='relu'),
#   tf.keras.layers.Dropout(0.2),
#   tf.keras.layers.Dense(csv.get_responding(), activation='softmax')
# ])

# model.compile(optimizer='adam',
#               loss='sparse_categorical_crossentropy',
#               metrics=['accuracy'])
#
# model.fit(x_train, y_train, epochs=5)
# model.evaluate(x_test, y_test)