# Data Analyzer

Getting started:
Ensure Python is installed, this software was built on Python 3.10.

##### Compatibility
Only tested on Windows 10. Should hypothetically be compatible with Linux and MacOS.

##### Setup
The software will automaticallly install all required dependencies using the "setup" button in the GUI.

##### Training
Input the following text into the top three text fields respectively:

```sh
https://storage.googleapis.com/tf-datasets/titanic/train.csv
https://storage.googleapis.com/tf-datasets/titanic/eval.csv
survived
```
The format is as follows:
```sh
Training data
Testing data
Responding variable
```
Click the "Train" button, and await a notice of completion in the Netbeans output.

The program is not optimized to work with virtually any dataset yet. So far, compatible data will only have 1 responsive variable with exactly two possible outputs. The dataset linked above is conveniently provided by TensorFlow for testing.

##### Predictions
Input the following into the top box in the prediction section:
sex
female
age 
19
n_siblings_spouses 
1
parch 
0
fare
8.72
class
first
deck
unknown
embark_town
Southampton
alone
n

The format is:
> key
> value
> 
And so forth... as can be seen in the above example. Feel free to change the values however you desire; assuming the type remains the same.

Then, import the model you previously trained by navigating to /models/model/ within the project directory and selecting a file.

Now, you are ready to press "Predict". Do so and witness the model make a prediction based on this simple data.
