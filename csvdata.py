import pandas as pd
import numpy as np
import sys

class CSVData:

    def __init__(self, train, test, together):
        # column_names = ['MPG', 'Cylinders', 'Displacement', 'Horsepower', 'Weight',
        #         'Acceleration', 'ModelYear', 'Origin']
        column_names = pd.read_csv(train, nrows=1).columns.tolist()

        try:
            if not together:
                self.train = pd.read_csv(train, names=column_names, skipinitialspace=True, skiprows=1)
                self.test = pd.read_csv(test, names=column_names, skipinitialspace=True, skiprows=1)
                
                self.train.isna().sum()
                self.train = self.train.dropna()
                self.test.isna().sum()
                self.test = self.train.dropna()
            else:
                # data = pd.read_csv(train, names=column_names,
                #               na_values='?', comment='\t',
                #               sep=' ', skipinitialspace=True)
                data = pd.read_csv(train, names=column_names, skipinitialspace=True, skiprows=1)
                self.train = data.sample(frac=0.8, random_state=0)
                self.test = data.drop(self.train.index)

            self.train.isna().sum()
            self.train = self.train.dropna()
            self.test.isna().sum()
            self.test = self.train.dropna()
        except FileNotFoundError:
            # CSVData.write_err("ERROR: The file you provided could not be found. Please ensure the path is correct.") 
            with open('results.txt', 'w+') as f:
                f.write('ERROR: The file you provided could not be found. Please ensure the path is correct.') 
            # sys.exit()      
            
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

    @staticmethod
    def write_err(err):
        print(err)
        with open('results.txt', 'w+') as f:
            f.write(str(err))

    @staticmethod
    def isItClassification(data, col):
        vals = []
        for i in data[col]:
            try:
                if vals.index(i):
                    continue
            except ValueError:
                vals.append(i)

        if len(vals) < len(data[col]) / 4:
            return True
        return False  

    @staticmethod
    def numOfClassifications(data, responding):
        return data[responding].unique()
