package io.scalawave.workshop.validation

import io.scalawave.workshop.common.Config

import scala.annotation.tailrec

/**
 * Validation demo
 *
 * You can use this main function for checking how your validation functions would work in action.
 *
 * Still, if you want to have it properly tested make use of provided tests.
 */
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
    ScalazValidation.parseConfig(accuracy, dataSource) match {
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
    CatsValidation.parseConfig(accuracy, dataSource) match {
      case cats.data.Validated.Invalid(errors) =>
        errors.toList.foreach { error => println(error) }
        testCats
      case cats.data.Validated.Valid(value) => value
    }
  }
}
