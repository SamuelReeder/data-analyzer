venv:
	python -m venv --system-site-packages .\venv

start:
	.\venv\Scripts\activate

run:
	py main.py

setup:
	pip install --upgrade pip
	pip install -r requirements.txt