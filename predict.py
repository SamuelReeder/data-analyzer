from numpy import float64
import tensorflow as tf
import pandas as pd
import sys
import numpy as np
import json

with open('predict.txt') as f:
    if f.readable:
      global sample
      sample = f.read()


with open(sys.argv[1] + '/config.txt') as f:
    if f.readable:
      global info
      info = f.readlines()

model_type = info[0].strip()
print(model_type)
if "Regression" not in model_type:
    info[1] = info[1].replace('\'', '"')
    key = json.loads(info[1])
    print(key)

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

loaded_model = tf.keras.models.load_model(sys.argv[1])

# loaded_model = tf.keras.models.load_model('models/new_model')

print(len(loaded_model.layers))

input_dict = {str(name).lower(): tf.convert_to_tensor([value]) for name, value in converted.items()}

try:
    if model_type == "Regression True":
        test = pd.DataFrame(input_dict)
        predict = loaded_model.predict(test)
        with open('results.txt', 'w+') as f:
            f.write(str(predict))
    elif model_type == "Regression False":
        predict = loaded_model.predict(input_dict)
        with open('results.txt', 'w+') as f:
            f.write(str(predict))
    elif model_type == "Sparse":
        predict = loaded_model.predict(input_dict)
        prob = tf.nn.softmax(predict[0]).numpy()
        max_prob = np.amax(prob)
        result = np.argmax(predict[0])
        for i in key:
            if key[i] == result:
                with open('results.txt', 'w+') as f:
                    f.write(("%.1f" % (100 * (max_prob))) + "% certain of " + str(i) + " classification.")
    elif model_type == "Binary":
        predict = loaded_model.predict(input_dict)
        print(type(predict))
        print(predict)
        print(loaded_model)
        print(loaded_model.distribute_strategy)
        prob = tf.nn.sigmoid(predict[0])
        result = np.argmax(predict[0])
        print(result)
        # for i in key:
        #     if key[i] == result:
        with open('results.txt', 'w+') as f:
            f.write(("%.1f" % (100 * (prob))) + "% certain of " + str(list(key)[0]) + " classification.")
except ValueError as err:
    try:
        with open('results.txt', 'w+') as i:
            e = str(err)
            formatted_err = e[e.index("ValueError: "):]
            i.write("ERROR: " + formatted_err)
    except Exception as exc:
        with open('results.txt', 'w+') as i:
            i.write("ERROR: An unknown error occured.")
            print(str(exc))
except Exception as err:
    with open('results.txt', 'w+') as i:
        i.write("ERROR: An unknown error occured.")
        print(str(err))