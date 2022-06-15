import sys
from csvdata import CSVData
import preprocessing as p
import numpy as np
import tensorflow as tf
from tensorflow.keras import layers
import pandas as pd

if sys.argv[1] == "test":
  training = 'https://storage.googleapis.com/tf-datasets/titanic/train.csv'
  testing = 'https://storage.googleapis.com/tf-datasets/titanic/eval.csv'
  # training = 'C:/Users/Sam/Downloads/train.csv' 
  # testing = 'C:/Users/Sam/Downloads/eval.csv'
  responsive = "fare"
  alg = "Regression"
  epochs = 14
elif sys.argv[1] == "test2":
  training = "C:/Users/Sam/Downloads/auto-mpg.csv"
  training = "C:/Users/Sam/Downloads/wine.csv"
  training = "C:/Users/Sam/Downloads/Admission_Predict.csv"
  # training = "C:/Users/Sam/Downloads/jena_climate_2009_2016.csv"
  # training = "C:/Users/Sam/Downloads/taxi-fares.csv"
  testing = None
  responsive = "MPG".lower()
  # responsive = "fare_amount"
  # responsive = "p (mbar)"
  alg = "Regression"
  epochs = 10
elif sys.argv[1] == "test3":
  training = "C:/Users/Sam/Downloads/attacking.csv"
  testing = None
  responsive = "position"
  alg = "Optimization"
  epochs = 10
else:
  training = sys.argv[1]
  testing = None
  if sys.argv[2] != 'none':
      testing = sys.argv[2]

  alg = sys.argv[3]

  epochs = int(sys.argv[4])

  responsive = str(sys.argv[5]).lower()
  
  path = 'models/' + str(sys.argv[6])

try:
  d = p.PreProcessing(responsive, training, testing, True if testing is None else False, alg)

  r_inputs, r_numeric_inputs, r_string_inputs = d.defineInput(True)
except Exception as e:
  with open('results.txt', 'w+') as f:
    f.write("ERROR: " + str(e))
  print(str(e))
  sys.exit()
  
learning_rate = 0.001
dropout_rate = 0.1

def sparse_model(preprocessing_head, inputs):
    body = tf.keras.Sequential([
      layers.Dense(64, activation="relu"),
      layers.Dropout(dropout_rate),
      layers.Dense(64, activation="relu"),
      layers.Dropout(dropout_rate),
      layers.Dense(len(d.labels.unique()), activation="softmax")
    ])

    preprocessed_inputs = preprocessing_head(inputs)
    result = body(preprocessed_inputs)
        
    temp_model = tf.keras.Model(inputs, result)

    temp_model.compile(
        optimizer=tf.optimizers.Adam(learning_rate=learning_rate),
        loss=tf.losses.SparseCategoricalCrossentropy(),
        metrics=[tf.metrics.SparseCategoricalAccuracy()],
    )
     
    return temp_model

def binary_model(preprocessing_head, inputs):
  body = tf.keras.Sequential([
      layers.Dense(64, activation="relu"),
      layers.Dropout(dropout_rate),
      layers.Dense(64, activation="relu"),
      layers.Dropout(dropout_rate),
      layers.Dense(1)
  ])
  
  preprocessed_inputs = preprocessing_head(inputs)
  result = body(preprocessed_inputs)
        
  temp_model = tf.keras.Model(inputs, result)
  
  temp_model.compile(loss=tf.losses.BinaryCrossentropy(from_logits=True),
                  optimizer=tf.optimizers.Adam(learning_rate=learning_rate),
                  metrics=["accuracy"])
  
  return temp_model

def alt_regression_model(norm):
  temp_model = tf.keras.Sequential([
      norm,
      layers.Dense(64, activation='relu'),
      layers.Dense(64, activation='relu'),
      layers.Dense(1)
  ])

  temp_model.compile(loss=tf.losses.MeanAbsoluteError(),
                optimizer=tf.optimizers.Adam(learning_rate=learning_rate))
  
  return temp_model

def regression_model(preprocessing_head, inputs):

  body = tf.keras.Sequential([
      layers.BatchNormalization(),
      layers.Dense(64, activation="relu"),
      layers.Dropout(0.1),
      layers.Dense(64, activation="relu"),
      layers.Dropout(0.1),
      layers.Dense(1)
  ])
  
  preprocessed_inputs = preprocessing_head(inputs)
  result = body(preprocessed_inputs)
  
  temp_model = tf.keras.Model(inputs, result)
  
  temp_model.compile(loss=tf.losses.MeanAbsoluteError(),
                optimizer=tf.optimizers.Adam(learning_rate=0.0001))
  
  return temp_model

def trainClassificationNeuralNet(d):
  
  binary = True
  if len(d.train[responsive].unique()) > 2:
    binary = False
    
  inputs, numeric_inputs, string_inputs = d.defineInput(False)

  preprocessed_inputs = d.preprocess()

  preprocessing = tf.keras.Model(inputs, preprocessed_inputs)

  features_dict, feat_dict = d.defineFeaturesDict(d.features)

  preprocessing(feat_dict)

  test_features_dict, test_feat_dict = d.defineFeaturesDict(d.test_features)

  model = binary_model(preprocessing, inputs) if binary else sparse_model(preprocessing, inputs)
  model.fit(x=features_dict, y=d.labels, epochs=epochs)

  loss, accuracy = model.evaluate(x=test_features_dict, y=d.test_labels, batch_size=128)
  
  result_text = "The model has completed training with an accuracy of " + str(round(accuracy * 100, 2)) + "% and a loss function of " + str(round(loss, 2))
  with open('results.txt', 'w+') as f:
        f.write(result_text)
        
  save_model(model, d.key, "Binary" if binary else "Sparse", path)

def trainRegressionNeuralNet():
  
  normalized = False
  try:
    normalized = True
    normalizer = tf.keras.layers.Normalization(axis=-1)
    x = np.asarray(d.features).astype('float32')
    normalizer.adapt(np.array(x))

    model = alt_regression_model(normalizer)
    model.fit(x, d.labels, validation_split=0.15, epochs=epochs)

    loss = model.evaluate(d.test_features, d.test_labels)
  
  except Exception:
    normalized = False
    inputs, numeric_inputs, string_inputs = d.defineInput(False)
    preprocessed_inputs = d.preprocess()
    preprocessing = tf.keras.Model(inputs, preprocessed_inputs)
    features_dict, feat_dict = d.defineFeaturesDict(d.features)
    preprocessing(feat_dict)
    test_features_dict, test_feat_dict = d.defineFeaturesDict(d.test_features)
  
    model = regression_model(preprocessing, inputs)  
    model.fit(features_dict, d.labels, validation_split=0.2, epochs=epochs, batch_size=128)
    loss = model.evaluate(test_features_dict, d.test_labels)
  
  result_text = "A regressive approach was taken to training this model, therefore, accuracy is not applicable. However, the loss was " + str(round(loss, 2)) + "."
  
  with open('results.txt', 'w+') as f:
    f.write(result_text)
    
  save_model(model, None, "Regression " + str(normalized), path)

def save_model(model, keys, nn_type, path):
  model.save(path)
  with open(path + '/config.txt', 'w+') as f:
        f.write(str(nn_type) + "\n" + str(responsive if keys is None else keys))

try:
  if d.r_dtype != object and (alg == 'Regression' or alg == 'Optimize'):
    print("Regression")
    trainRegressionNeuralNet()
  elif CSVData.isItClassification(d.train, responsive):
    print("Classification")
    trainClassificationNeuralNet(d)
except Exception as e:
  with open('results.txt', 'w+') as f:
    f.write("ERROR: " + str(e))
  print(str(e))
  sys.exit()

