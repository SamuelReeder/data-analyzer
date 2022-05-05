import sys
import preprocessing as p
import train as t

# training = sys.argv[1]
# testing = ''
# if sys.argv[2] != 'none':
#     testing = sys.argv[2]

# responsive = sys.argv[3]

# if len(sys.argv) > 4:
#     responsive = []
#     for i in len(sys.argv) - 3:
#         responsive[i] = sys.argv[i + 3]

training = 'https://storage.googleapis.com/tf-datasets/titanic/train.csv'
testing = 'https://storage.googleapis.com/tf-datasets/titanic/eval.csv'

responsive = ["survived", "fare", "alone"]

d = p.PreProcessing(responsive, training, testing, False)

training_model = t.Train(d)

