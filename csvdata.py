import pandas as pd
import numpy as np
from tensorflow.keras.preprocessing.text import Tokenizer
import sys
import re

class CSVData:

    def __init__(self, train, test, together, responding, alg):
        column_names = pd.read_csv(train, nrows=1).columns.tolist()
        column_names = [re.sub(r'[^\w\s]', '_', str(x).strip().lower().replace(" ", "_")) for x in column_names]
        print(column_names)
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
            self.r_dtype = data[responding].dtype
            print(alg)
            if CSVData.isItClassification(data, responding) and alg != "Regression":
                print("Tokenized")
                data[responding] = data[responding].astype(str)
                data[responding], self.key = CSVData.tokenize(data[responding])
            else:
                print("Not tokenized")
                self.key = {str(i):i for i in data[responding].unique()}
            
            self.train, self.test = np.split(data.sample(frac=1), [int(0.9*len(data))])
        except FileNotFoundError as e:
            with open('results.txt', 'w+') as i:
                i.write("ERROR: " + str(e))
            print(str(e))
            sys.exit()
            
    def get_train(self):
        return self.train

    def get_test(self):
        return self.test
    
    def get_key(self):            
        return self.key

    @staticmethod
    def isItClassification(data, col):
        vals = data[col].unique()
        if len(vals) < 25:
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
            arr.append(num[0] - 1)
        
        return arr, myTokenizer.word_index
