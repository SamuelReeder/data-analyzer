import csvdata as csv
import tensorflow as tf
import numpy as np
from tensorflow.keras import layers


class PreProcessing (csv.CSVData):

    def __init__(self, responding, train, test, together, alg):

        csv.CSVData.__init__(self, train, test, together, responding, alg)
        self.key = self.get_key()
        self.responding = responding
        self.together= together
        self.train = self.get_train()
        self.test = self.get_test()
        self.features = self.train.copy()
        self.labels = self.features.pop(responding) 
        self.test_features = self.test.copy()
        self.test_labels = self.test_features.pop(responding)

    def defineInput(self, responding):

        self.inputs = {}
        self.string_inputs = []
        for name, column in (self.train.items() if responding else self.features.items()):
            dtype = column.dtype
            if dtype == object:
                dtype = tf.string
                self.string_inputs.append(name)
            else:
                dtype = tf.float32

            self.inputs[name] = tf.keras.Input(shape=(1,), name=name, dtype=dtype)

        self.numeric_inputs = {name:input for name,input in self.inputs.items()
                  if input.dtype==tf.float32}

        return self.inputs, self.numeric_inputs, self.string_inputs

    def preprocess(self):

        x = layers.Concatenate()(list(self.numeric_inputs.values()))
        self.norm = layers.Normalization(axis=-1)
        self.norm.adapt(np.array(self.train[self.numeric_inputs.keys()]))
        all_numeric_inputs = self.norm(x)

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

    def df_to_dataset(self, dataframe, shuffle=True, batch_size=32):
        dataframe = dataframe.copy()
        labels = dataframe.pop(self.responding)
        ds = tf.data.Dataset.from_tensor_slices((dict(dataframe), labels))
        if shuffle:
            ds = ds.shuffle(buffer_size=len(dataframe))
        ds = ds.batch(batch_size)
        return ds