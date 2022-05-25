import pandas as pd
import numpy as np

from pandas.api.types import is_string_dtype
from pandas.api.types import is_numeric_dtype


class CSVData:

    def __init__(self, train, test, together):
        column_names = ['MPG', 'Cylinders', 'Displacement', 'Horsepower', 'Weight',
                'Acceleration', 'ModelYear', 'Origin']
        if not together:
            self.train = pd.read_csv(train)
            self.test = pd.read_csv(test)
            self.train.isna().sum()
            self.train = self.train.dropna()
            self.test.isna().sum()
            self.test = self.train.dropna()
        else:
            # data = pd.read_csv(train, names=column_names,
            #               na_values='?', comment='\t',
            #               sep=' ', skipinitialspace=True)
            # data.isna().sum()
            # data = data.dropna()
            data = pd.read_csv(train)
            self.train = data.sample(frac=0.8, random_state=0)
            self.test = data.drop(self.train.index)
            
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