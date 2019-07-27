import Dependencies._

name := "LearningScala"
version := "1.0"
scalaVersion := "2.12.7"

lazy val root = (project in file("."))
    .settings(
        name := "LearningScala",
        libraryDependencies += scalaTest % Test,
        libraryDependencies += sparkCore,
        libraryDependencies += sparkMLlib
        //libraryDependencies += "org.vegas-viz" %% "vegas" % "0.3.11"
    )
