import sys
from csvdata import CSVData
import preprocessing as p
import numpy as np
import tensorflow as tf
from tensorflow.keras import layers
# import os
# os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2' 
# tf.get_logger().setLevel('INFO')


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

d = p.PreProcessing(responsive, training, testing, True if testing is None else False)

r_inputs, r_numeric_inputs, r_string_inputs = d.defineInput(True)

learning_rate = 0.001
dropout_rate = 0.1

# path = 'models/new_model'

def sparse_model(preprocessing_head, inputs):
    body = tf.keras.Sequential([
      layers.Dense(32, activation="relu"),
      layers.Dropout(dropout_rate),
      layers.Dense(32, activation="relu"),
      layers.Dropout(dropout_rate),
      layers.Dense(len(d.labels.unique()) + 1, activation="softmax")
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
      layers.Dense(32, activation="relu"),
      layers.Dropout(0.1),
      layers.Dense(32, activation="relu"),
      layers.Dropout(0.1),
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
      layers.Dense(32, activation="relu"),
      layers.Dropout(0.1),
      layers.Dense(32, activation="relu"),
      layers.Dropout(0.1),
      layers.Dense(1)
  ])
  
  preprocessed_inputs = preprocessing_head(inputs)
  result = body(preprocessed_inputs)
  
  temp_model = tf.keras.Model(inputs, result)
  
  print("Batch normalization")
        
  
  
  temp_model.compile(loss=tf.losses.MeanAbsoluteError(),
                optimizer=tf.optimizers.Adam(learning_rate=0.0001))
  
  # temp_model.compile(loss=tf.losses.MeanSquaredLogarithmicError(),
  #               optimizer=tf.optimizers.RMSprop(learning_rate=0.0001))
  
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
  print(loss, accuracy)
  
  with open('results.txt', 'w+') as f:
        f.write(str(loss) + "\n" + str(accuracy))
        
  save_model(model, d.key, "Binary" if binary else "Sparse", path)

def trainRegressionNeuralNet():
  
  normalized = False
  try:
    normalized = True
    normalizer = tf.keras.layers.Normalization(axis=-1)
    print(d.features)
    x = np.asarray(d.features).astype('float32')
    print(x)
    normalizer.adapt(np.array(x))

    model = alt_regression_model(normalizer)
    
    history = model.fit(
      x,
      d.labels,
      validation_split=0.2,
      verbose=0, epochs=epochs)

    loss = model.evaluate(d.test_features, d.test_labels, verbose=0)
    print(loss)
  
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
    print(loss)
  
  with open('results.txt', 'w+') as f:
    f.write(str(loss) + "\n")
    
  save_model(model, None, "Regression " + str(normalized), path)

def save_model(model, keys, nn_type, path):
  model.save(path)
  with open(path + '/config.txt', 'w+') as f:
        f.write(str(nn_type) + "\n" + str("" if keys is None else keys))
  
if r_inputs[responsive].dtype == 'float32' and (alg == 'Regression' or alg == 'Optimize') and not CSVData.isItClassification(d.train, responsive):
  trainRegressionNeuralNet()
else:
  trainClassificationNeuralNet(d)
