from numpy import float64
import tensorflow as tf
import pandas as pd
import sys
import numpy as np

sample = {}

with open('predict.txt') as f:
    if f.readable:
      sample = f.read()

def config_type(val):
    try: 
        return float64(val)
    except:
        return val

def convert(lst):
    pred_list = lst.split()
    res_dct = {pred_list[i].strip(): config_type(pred_list[i + 1]) for i in range(0, len(pred_list) - 1, 2)}
    return res_dct 

converted = convert(sample)

# loaded_model = tf.keras.models.load_model(sys.argv[1])

loaded_model = tf.keras.models.load_model('models/new_model')

print(len(loaded_model.layers))

input_dict = {name: tf.convert_to_tensor([value]) for name, value in converted.items()}

print(input_dict)

predictions = loaded_model.predict(input_dict)

print(predictions)
print(predictions[0])
print(np.argmax(predictions[0]))

# try:
#     test = pd.DataFrame(input_dict)

#     predict = loaded_model.predict(test)

#     print(predict)
#     with open('results.txt', 'w+') as f:
#         f.write(str(predict))
    
#     print("FINISHED")

# except ValueError:
#     predict = loaded_model.predict(input_dict)

#     prob = tf.nn.sigmoid(predict[0])

#     with open('results.txt', 'w+') as f:
#         f.write("%.1f" % (100 * prob))
    
#     print("%.1f" % (100 * prob))
# finally:
#     print("The prediction has completed")