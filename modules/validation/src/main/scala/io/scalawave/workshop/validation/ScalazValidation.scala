package io.scalawave.workshop.validation

import io.scalawave.workshop.validation.DataSource.DataSource

import scalaz._
import Scalaz._

object ScalazValidation {

  def parseAccuracy(accuracy: String): ValidationNel[ConfigError, Int] =
    Validation.fromTryCatchNonFatal { accuracy.toInt }
      .leftMap { _ => NotANumber(accuracy) }
      .ensure(NotNatural(accuracy)) { _ >= 0 }
      .toValidationNel

  def parseDataSource(dataSource: String): ValidationNel[ConfigError, DataSource] =
    Validation.fromTryCatchNonFatal { DataSource.withName(dataSource.toLowerCase) }
      .leftMap { _ => InvalidDataSource(dataSource) }
      .toValidationNel

  def parse(accuracy: String, dataSource: String): ValidationNel[ConfigError, Config] =
    (parseAccuracy(accuracy) |@| parseDataSource(dataSource)) { Config }
}
