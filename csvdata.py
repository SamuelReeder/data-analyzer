import pandas as pd
import numpy as np

from pandas.api.types import is_string_dtype
from pandas.api.types import is_numeric_dtype


class CSVData:
    def __init__(self, train, test):
        self.train = pd.read_csv(train)
        self.test = pd.read_csv(test)

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

    def get_numeric_columns(self):
        arr = []
        for col in self.train:
            if is_numeric_dtype(col[0]):
                arr.append(col)
        return arr

    def get_text_columns(self):
        arr = []
        for col in self.train:
            if is_string_dtype(col[0]):
                arr.append(col)
        return arr