import tensorflow as tf

loaded_model = tf.keras.models.load_model('models/model')

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

input_dict = {name: tf.convert_to_tensor([value]) for name, value in sample.items()}

predict = loaded_model.predict(input_dict)
prob = tf.nn.sigmoid(predict[0])

print(
    "This persoj had a %.1f percent probability "
    "of surviving." % (100 * prob)
)
