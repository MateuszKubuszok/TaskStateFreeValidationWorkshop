package io.scalawave.workshop.validation

import scala.annotation.tailrec

object Main {

  def main(args: Array[String]): Unit = {
    println("Run scalaz validation")
    val scalazConfig = testScalaz
    println(s"Config resolved to $scalazConfig")
    println()
    println("Run cats validation")
    val catsConfig = testCats
    println(s"Config resolved to $catsConfig")
  }

  @tailrec
  def testScalaz: Config = {
    println("Enter accuracy: ")
    val accuracy = scala.io.StdIn.readLine
    println("Enter data source: ")
    val dataSource = scala.io.StdIn.readLine
    ScalazValidation.parse(accuracy, dataSource) match {
      case scalaz.Failure(errors) =>
        errors.foreach { error => println(error) }
        testScalaz
      case scalaz.Success(value) => value
    }
  }

  @tailrec
  def testCats: Config = {
    println("Enter accuracy: ")
    val accuracy = scala.io.StdIn.readLine
    println("Enter data source: ")
    val dataSource = scala.io.StdIn.readLine
    CatsValidation.parse(accuracy, dataSource) match {
      case cats.data.Validated.Invalid(errors) =>
        errors.toList.foreach { error => println(error) }
        testCats
      case cats.data.Validated.Valid(value) => value
    }
  }
}
