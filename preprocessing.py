from __future__ import absolute_import, division, print_function, unicode_literals
from unicodedata import numeric

import pandas as pd
import tensorflow as tf
import numpy as np
from IPython.display import clear_output
# import six.moves
import csvdata as data

class PreProcessing:

    def __init__(self, data):
        self.data = data

        self.train = self.data.get_train()
        self.test = self.data.get_test()
        self.features = self.train.copy()
        self.labels = self.features.pop("survived")
        self.test_features = self.test.copy()


    def convertToTensor(self):
        return

    def defineInput(self, dataset):

        inputs = {}
        for name, column in self.features.items():
            dtype = column.dtype
            if dtype == object:
                dtype = tf.string
            else:
                dtype = tf.float32

            inputs[name] = tf.keras.Input(shape=(1,), name=name, dtype=dtype)

        numeric_inputs = {name:input for name,input in self.inputs.items()
                  if input.dtype==tf.float32}

        return inputs, numeric_inputs
    

    def numericInputs(self):
        return   
       