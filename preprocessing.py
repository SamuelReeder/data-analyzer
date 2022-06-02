import csvdata as csv
import enum

import tensorflow as tf
import numpy as np
from tensorflow.keras import layers
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras import layers


class PreProcessing (csv.CSVData):

    def __init__(self, responding, train, test, together):

        csv.CSVData.__init__(self, train, test, together)

        self.train = self.get_train()
        self.test = self.get_test()

        # text_vector = layers.TextVectorization()
        # layers.adapt
        # self.train[responding], key1 = self.tokenize(self.train[responding])
        # self.test[responding], key2 = self.tokenize(self.test[responding])

        self.features = self.train.copy()
        self.labels = self.features.pop(responding) 
        self.test_features = self.test.copy()
        self.test_labels = self.test_features.pop(responding)

    def defineInput(self, responding):

        self.inputs = {}
        for name, column in (self.train.items() if responding else self.features.items()):
            dtype = column.dtype
            if dtype == object:
                dtype = tf.string
            else:
                dtype = tf.float32

            self.inputs[name] = tf.keras.Input(shape=(1,), name=name, dtype=dtype)
            # print(self.inputs[name])
            # print(tf.keras.Input(shape=(3,), name=name, dtype=dtype))

        self.numeric_inputs = {name:input for name,input in self.inputs.items()
                  if input.dtype==tf.float32}

        return self.inputs, self.numeric_inputs

    def preprocess(self):

        # possibly use text vectorization 

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


    def isItClassification(self, col):
        vals = []
        for i in self.train[col]:
            try:
                if vals.index(i):
                    continue
            except ValueError:
                vals.append(i)

        if len(vals) < len(self.train[col]) / 4:
            return True
        return False  

    
    def numOfClassifications(self, responding):
        return self.train[responding].unique()

    def tokenize(self, col):
        myTokenizer = Tokenizer(num_words=100)
        myTokenizer.fit_on_texts(col)
        sequences = myTokenizer.texts_to_sequences(col)

        arr = []
        for i, num in enumerate(sequences):
            arr.append(num[0])
        
        return arr, myTokenizer.word_index
