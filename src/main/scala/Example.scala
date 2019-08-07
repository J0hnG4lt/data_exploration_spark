/**
* This module implements simple data exploration methods
*/

import org.apache.spark.mllib.stat.MultivariateStatisticalSummary
import org.apache.spark.sql.functions.{to_date, asc, desc}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SQLContext
import org.apache.spark.ml.Pipeline

//import vegas._
//import vegas.render.WindowRenderer._

//https://medium.com/@pedrodc/setting-up-a-spark-machine-learning-project-with-scala-sbt-and-mllib-831c329907ea

object SparkApp {

  private def loadSparkSession() : SparkSession = {
    val spark = SparkSession.builder
    .appName("SparkAppAmazonReviews") 
    .master("local") 
    .config("spark.network.timeout", "800")
    .getOrCreate()
    return spark
  }

  private def loadSparkContext() : SparkContext = {
    //Create a SparkContext to initialize Spark
    val conf = new SparkConf()
    conf.setMaster("localhost:7077")
    conf.setAppName("SparkAppAmazonReviews")
    conf.set("spark.network.timeout", "800")
    val sc = new SparkContext(conf)
    return sc
  }
  private def loadSqlContext(sc : SparkContext) : SQLContext = {
    val sqlContext = new SQLContext(sc)
    return sqlContext
  }

  def main(args: Array[String]): Unit = {
    val spark = loadSparkSession()
    
    val fileName = args(0)
    println(fileName)

    // case class ProductWholeSaleOrder(
    //   Product_Code : String,
    //   Warehouse : String,
    //   Product_Category : String,
    //   Date : DateType,
    //   Order_Demand : Int
    // )

    // Columns :
    // Product_Code | Warehouse | Product_Category | Date | Order_Demand
    var df = spark.read.format("csv")
      .option("header", "true")
      .option("treatEmptyValuesAsNulls", "true")
      .option("mode", "DROPMALFORMED")
      .option("timestampFormat", "yyyy/MM/dd")
      .option("inferSchema", "true")
      .load(fileName);
    
    // Date column
    df = df.withColumn(
      "Date", 
      to_date(df.col("Date"), "yyyy/MM/dd/")
    )
    println("\nThe first rows of the dataframe: ")
    df.show()

    println("\nIts schema: ")
    df.printSchema()

    println("Sort by Date, Group by Product_Code, and count Order_Demand")
    // Sort by Date
    // Group by Product_Code and Warehouse
    // Count Order_Demand
    df.sort(asc("Date"))
      .groupBy("Product_Code")
      .count()
      .sort(desc("count"))
      .show()

    // Closing Spark contexts
    spark.stop()
    //sqlContext.stop()

  } 
}
