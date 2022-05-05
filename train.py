import tensorflow as tf
from tensorflow.keras import layers

class Train:

    def __init__(self, data):
        self.d = data

    def neuralNet(self):

        inputs, numeric_inputs = self.d.defineInput()

        print("String inputs", inputs)
        print("numeric inputs", numeric_inputs)

        preprocessed_inputs = self.d.preprocess()

        preprocessing = tf.keras.Model(inputs, preprocessed_inputs)

        features_dict, feat_dict = self.d.defineFeaturesDict(self.d.features)

        preprocessing(feat_dict)

        # test_features_dict, test_feat_dict = self.d.defineFeaturesDict(self.d.test_features)

        def model(preprocessing_head, inputs):
            body = tf.keras.Sequential([
                layers.Dense(64),
                layers.Dense(1))
            ])

            preprocessed_inputs = preprocessing_head(inputs)
            result = body(preprocessed_inputs)
            temp_model = tf.keras.Model(inputs, result)

            temp_model.compile(loss=tf.losses.BinaryCrossentropy(from_logits=True),
                            optimizer=tf.optimizers.Adam())
            return temp_model

        model = model(preprocessing, inputs)
        model.fit(x=features_dict, y=self.d.labels, epochs=10)

        results = model.evaluate(x=features_dict, y=self.d.labels, batch_size=128)
        print(results)

        model.save('models/model')


    # def regress(self):

    # def compileMultipleFeatures(self):

    # def saveModel(self):


