venv:
	python -m venv .\venv

start:
	.\venv\Scripts\activate

run:
	py main.py

setup:
	pip install --upgrade pip
	pip install -r requirements.txt

make test:
	py main.py test