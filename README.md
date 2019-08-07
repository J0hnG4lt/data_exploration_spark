# Data Exploration with Spark and Scala

Just doing this to learn Apache Spark and Scala

## Dataset

1. Go to https://www.kaggle.com/felixzhao/productdemandforecasting
2. Download the Dataset in zip format
3. Extract the CSV file and place it in this folder

## Usage

Run the following command:

```sh
sbt "run \"<path-to-csv-file>\""
```

For example:

```sh
sbt "run \"src/main/resources/Historical Product Demand.csv\""
```