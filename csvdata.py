import pandas as pd
import numpy as np
from tensorflow.keras.preprocessing.text import Tokenizer
import sys

class CSVData:

    def __init__(self, train, test, together, responding):
        column_names = pd.read_csv(train, nrows=1).columns.tolist()
        column_names = [str(x).lower() for x in column_names]
        try:
            if not together:
                temp_train = pd.read_csv(train, names=column_names, skipinitialspace=True, skiprows=1)
                temp_test = pd.read_csv(test, names=column_names, skipinitialspace=True, skiprows=1)
                arr = [temp_train, temp_test]
                data = pd.concat(arr)
            else:
                data = pd.read_csv(train, names=column_names, skipinitialspace=True, skiprows=1)
                
            data.isna().sum()
            data = data.dropna()
            if data[responding].dtype == object:
                data[responding], self.key = CSVData.tokenize(data[responding])
            else:
                self.key = {str(i):i for i in data[responding].unique()}
            
            self.train, self.test = np.split(data.sample(frac=1), [int(0.9*len(data))])
        except FileNotFoundError:
            CSVData.write_err("ERROR: The file you provided could not be found. Please ensure the file exists and path is correct.") 
            sys.exit()      
            
    def get_train(self):
        return self.train

    def get_test(self):
        return self.test
    
    def get_key(self):            
        return self.key

    @staticmethod
    def write_err(err):
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
        if len(vals) < 100:
            return True
        return False  

    @staticmethod
    def numOfClassifications(data, responding):
        return data[responding].unique()
    
    @staticmethod
    def tokenize(col):
        myTokenizer = Tokenizer(num_words=100)
        myTokenizer.fit_on_texts(col)
        sequences = myTokenizer.texts_to_sequences(col)

        arr = []
        for i, num in enumerate(sequences):
            arr.append(num[0])
        
        return arr, myTokenizer.word_index
