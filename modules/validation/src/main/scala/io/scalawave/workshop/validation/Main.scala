package io.scalawave.workshop.validation

import io.scalawave.workshop.common.Logging

import scala.annotation.tailrec

object Main extends Logging {

  def main(args: Array[String]): Unit = {
    logger info "Run scalaz validation"
    val scalazConfig = testScalaz
    logger info s"Config resolved to $scalazConfig"
    logger info "Run cats validation"
    val catsConfig = testCats
    logger info s"Config resolved to $catsConfig"
  }

  @tailrec
  def testScalaz: Config = {
    logger info "Enter accuracy:"
    val accuracy = scala.io.StdIn.readLine
    logger info "Enter data source:"
    val dataSource = scala.io.StdIn.readLine
    ScalazValidation.parse(accuracy, dataSource) match {
      case scalaz.Failure(errors) =>
        errors.foreach { error => logger error error.toString }
        testScalaz
      case scalaz.Success(value) => value
    }
  }

  @tailrec
  def testCats: Config = {
    logger info "Enter accuracy:"
    val accuracy = scala.io.StdIn.readLine
    logger info "Enter data source:"
    val dataSource = scala.io.StdIn.readLine
    CatsValidation.parse(accuracy, dataSource) match {
      case cats.data.Validated.Invalid(errors) =>
        errors.toList.foreach { error => logger error error.toString }
        testCats
      case cats.data.Validated.Valid(value) => value
    }
  }
}
