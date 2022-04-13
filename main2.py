from __future__ import absolute_import, division, print_function, unicode_literals

import json

import pandas as pd
import tensorflow as tf
import numpy as np
import csvdata as data
from tensorflow.keras import layers

training = input()
testing = input()
csv = data.CSVData(training, testing, False)

responding = csv.get_responding()

training_data = csv.get_train()
features = training_data.copy()
labels = features.pop(responding)

csv.define_columns()

def preprocessing():
    # Will preprocess the data to maximuze optimization to train the model.
    return

def epochs():
    # Attempt to identify optimal number of epochs
    return

def model(preprocessing_head, inputs):
  network = tf.keras.Sequential([
    # Define the layers of the neural network according to the dataset
  ])

  return

model = model(preprocessing())

model.fit(epochs=epochs())

json_object = json.dumps(str(model), indent = 4)
  
with open("data.json", "w") as f:
    f.write(json_object)
    # Write to json file 