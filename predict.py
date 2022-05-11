from numpy import float64
import tensorflow as tf
import sys

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
print(converted)

# loaded_model = tf.keras.models.load_model(sys.argv[1])

loaded_model = tf.keras.models.load_model('models/dnn_model')


input_dict = {name: tf.convert_to_tensor([value]) for name, value in converted.items()}

predict = loaded_model.predict(input_dict)
prob = tf.nn.sigmoid(predict[0])

with open('results.txt', 'w+') as f:
    f.write("%.1f" % (100 * prob))