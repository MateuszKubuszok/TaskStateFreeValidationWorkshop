package io.scalawave.workshop.validation

import cats.data.{ Validated, ValidatedNel }
import cats.syntax.cartesian._
import io.scalawave.workshop.validation.DataSource.DataSource

object CatsValidation {

  def parseAccuracy(accuracy: String): ValidatedNel[ConfigError, Int] =
    Validated.catchNonFatal { accuracy.toInt }
      .leftMap { _ => NotANumber(accuracy) }
      .ensure(NotNatural(accuracy)) { _ >= 0 }
      .toValidatedNel

  def parseDataSource(dataSource: String): ValidatedNel[ConfigError, DataSource] =
    Validated.catchNonFatal { DataSource.withName(dataSource) }
      .leftMap { _ => InvalidDataSource(dataSource) }
      .toValidatedNel

  def parse(accuracy: String, dataSource: String): ValidatedNel[ConfigError, Config] =
    (parseAccuracy(accuracy) |@| parseDataSource(dataSource)) map { Config }
}
