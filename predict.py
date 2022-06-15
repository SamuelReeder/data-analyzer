from numpy import float64
import tensorflow as tf
import pandas as pd
import sys
import numpy as np
import json
import re
from tensorflow.python.ops.numpy_ops import np_config        
np_config.enable_numpy_behavior()

def predict():
    with open('predict.txt') as f:
        if f.readable:
            global sample
            sample = f.read()
    with open(sys.argv[1] + '/config.txt') as f:
        if f.readable:
            global info
            info = f.readlines()
    
    print("Collection of input completed")

    model_type = info[0].strip()
    if "Regression" not in model_type:
        info[1] = info[1].replace('\'', '"')
        key = json.loads(info[1])
    else: 
        responsive = info[1]

    def config_type(val):
        try: 
            return float64(val)
        except:
            return val

    def convert(lst):
        pred_list = lst.split("\n")
        res_dct = {pred_list[i].strip(): config_type(pred_list[i + 1]) for i in range(0, len(pred_list) - 1, 2)}
        return res_dct 

    converted = convert(sample)

    loaded_model = tf.keras.models.load_model(sys.argv[1])

    input_dict = {re.sub(r'[^\w\s]', '_', str(name).lower().replace(" ", "_")): tf.convert_to_tensor([value]) for name, value in converted.items()}

    print("Preprocessing of input completed")
    
    if model_type == "Regression True":
        test = pd.DataFrame(input_dict)
        predict = loaded_model.predict(test)[0][0]
        with open('results.txt', 'w+') as f:
            f.write("\"" + str(responsive) + "\" has a predicted value of: " + str(round(predict, 2)))
    elif model_type == "Regression False":
        predict = loaded_model.predict(input_dict)
        with open('results.txt', 'w+') as f:
            f.write("\"" + str(responsive) + "\" has a predicted value of: " + str(round(predict, 2)))
    elif model_type == "Sparse":
        predict = loaded_model.predict(input_dict)
        max_prob = max(predict[0])
        result = np.argmax(predict[0])

        final = round((100 * max_prob), 2)
        for i in key:
            if key[i] - 1 == result:
                with open('results.txt', 'w+') as f:
                    f.write(("%.1f" % final) + "% certain of \"" + str(i) + "\" classification.")
                break
    elif model_type == "Binary":
        predict = loaded_model.predict(input_dict)
        prob = tf.nn.sigmoid(predict[0])

        label = 2
        if prob < 0.5:
            label = 1
            prob = 1 - prob
        
        final = round((100 * prob), 2)
        for i in key:
            if key[i] == label:
                with open('results.txt', 'w+') as f:
                    f.write(("%.1f" % final) + "% certain of \"" + str(i) + "\" classification.")
                break
            
try:
    predict()
except Exception as e:
    with open('results.txt', 'w+') as i:
        i.write("ERROR: " + str(e))
    print(str(e))
    sys.exit()

