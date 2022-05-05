from array import array
import csvdata as csv

import tensorflow as tf
import numpy as np
from tensorflow.keras import layers

class PreProcessing (csv.CSVData):

    def __init__(self, responding, train, test, together):
        # self.data = data

        csv.CSVData.__init__(self, train, test, together)

        self.train = self.get_train()
        self.test = self.get_test()
        self.features = self.train.copy()

        self.responding = responding

        print('responsive', responding)
        self.labels = self.defineResponding(responding)
        self.defineResponding(responding)
        self.test_features = self.test.copy()

    def convertToTensor(self):
        return

    def defineInput(self):

        self.inputs = {}
        for name, column in self.features.items():
            dtype = column.dtype
            if dtype == object:
                dtype = tf.string
            else:
                dtype = tf.float32

            self.inputs[name] = tf.keras.Input(shape=(1,), name=name, dtype=dtype)

        self.numeric_inputs = {name:input for name,input in self.inputs.items()
                  if input.dtype==tf.float32}

        return self.inputs, self.numeric_inputs

       
    def preprocess(self):
        x = layers.Concatenate()(list(self.numeric_inputs.values()))
        norm = layers.Normalization()
        norm.adapt(np.array(self.train[self.numeric_inputs.keys()]))
        all_numeric_inputs = norm(x)

        preprocessed_inputs = [all_numeric_inputs]

        for name, input in self.inputs.items():
            if input.dtype == tf.float32:
                continue

            lookup = layers.StringLookup(vocabulary=np.unique(self.features[name]))
            one_hot = layers.CategoryEncoding(num_tokens=lookup.vocabulary_size())

            x = lookup(input)
            x = one_hot(x)
            preprocessed_inputs.append(x)

        return layers.Concatenate()(preprocessed_inputs)
    
    def defineFeaturesDict(self, features):
        temp_dict = {name: np.array(value) 
                 for name, value in features.items()}
        lower_dimension_dict =  {name:values[:1] for name, values in temp_dict.items()}
        return temp_dict, lower_dimension_dict

    # def setAlgorithym(self):
    #     self.inputs = {}
    #     for name, column in self.features.items():
    #         dtype = column.dtype
    #         if dtype == object:
    #             dtype = tf.string
    #         else:
    #             dtype = tf.float32

    #         self.inputs[name] = tf.keras.Input(shape=(1,), name=name, dtype=dtype)

    #     self.numeric_inputs = {name:input for name,input in self.inputs.items()
    #               if input.dtype==tf.float32}

    #     return self.inputs, self.numeric_inputs


