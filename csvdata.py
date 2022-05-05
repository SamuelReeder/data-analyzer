from array import array
import pandas as pd
import numpy as np

from pandas.api.types import is_string_dtype
from pandas.api.types import is_numeric_dtype


class CSVData:
    # Various methods to obtain vital information about the dataset
    # This info will be, in part, used to define the optimal parameters of the neural network, including:
    #   - density of the layers
    #   - amount of possible prediction outputs
    def __init__(self, train, test, together):
        self.train = pd.read_csv(train)
        self.test = pd.read_csv(test)
        self.text_cols = []
        self.num_cols = []
        self.together = together

    def get_features(self):
        return self.train.shape[1]

    def get_entries(self):
        return self.train.shape[0]

    def get_responding(self, responding):
        return self.train[responding]

    def get_train(self):
        return self.train

    def get_test(self):
        return self.test

    def get_text_cols(self):
        return self.text_cols

    def get_num_cols(self):
        return self.num_cols

    def define_columns(self):
        for (columnName, columnData) in self.train.iteritems():
            
            text = []

            if type(columnData[1]) is str:
                self.text_cols.append(columnName)
                continue
            elif type(columnData[1]) is np.float64:
                self.num_cols.append(columnName)
                continue
            else:
                for values in columnData:
                    try:
                        check = text.index(values)
                    except ValueError:
                        text.append(values)

                # divided by arbitrary number 
                if len(text) < len(columnData) / 4:
                    self.text_cols.append(columnName)
                else:
                    self.num_cols.append(columnName)

            print(type(columnData[1]))      

    def defineResponding(self, responding):
        print("responding is occuring")
        if type(responding) is array:
            return pd.concat([self.features.pop(i) for i in responding], axis=1) 
        return responding
             