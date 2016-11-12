package io.scalawave.workshop.validation

import cats.data.{ Validated, ValidatedNel }
import cats.syntax.cartesian._
import io.scalawave.workshop.common._
import ActionType.ActionType
import Currency.Currency
import DataSource.DataSource

object CatsValidation {

  def parseDouble(accuracy: String): ValidatedNel[ParsingError, Double] =
    Validated.catchNonFatal { accuracy.trim.toDouble }
      .leftMap { _ => NotANumber(accuracy) }
      .toValidatedNel

  def parseNatural(accuracy: String): ValidatedNel[ParsingError, Int] =
    Validated.catchNonFatal { accuracy.trim.toInt }
      .leftMap { _ => NotANumber(accuracy) }
      .ensure(NotNatural(accuracy)) { _ >= 0 }
      .toValidatedNel

  def parseActionType(actionType: String): ValidatedNel[ParsingError, ActionType] =
    Validated.catchNonFatal { ActionType.withName(actionType.trim.toLowerCase) }
      .leftMap { _ => InvalidActionType(actionType) }
      .toValidatedNel

  def parseCurrency(currency: String): ValidatedNel[ParsingError, Currency] =
    Validated.catchNonFatal { Currency.withName(currency.trim.toLowerCase) }
      .leftMap { _ => InvalidCurrency(currency) }
      .toValidatedNel

  def parseDataSource(dataSource: String): ValidatedNel[ParsingError, DataSource] =
    Validated.catchNonFatal { DataSource.withName(dataSource.trim.toLowerCase) }
      .leftMap { _ => InvalidDataSource(dataSource) }
      .toValidatedNel

  def parseConfig(accuracy: String, dataSource: String): ValidatedNel[ParsingError, Config] =
    (parseNatural(accuracy) |@| parseDataSource(dataSource)) map { Config }
}
