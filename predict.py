import tensorflow as tf

sample = {
  'sex': 'male',	
  'age': 34,
  'n_siblings_spouses': 1,
  'parch': 0,
  'fare': 7.25,	
  'class': 'third',	
  'deck': 'unknown',
  'embark_town': 'Southampton',
  'alone': 'n'

}

with open('predict.txt') as f:
    sample = f.readlines()
    print(sample)
    print(type(sample))
    print(len(sample))

def Convert(lst):
    res_dct = {lst[i]: lst[i + 1] for i in range(0, len(sample), 2)}
    return res_dct

converted = Convert(sample)
loaded_model = tf.keras.models.load_model('models/model')

input_dict = {name: tf.convert_to_tensor([value]) for name, value in converted.items()}

predict = loaded_model.predict(input_dict)
prob = tf.nn.sigmoid(predict[0])

print(
    "This persoj had a %.1f percent probability "
    "of surviving." % (100 * prob)
)
