package io.scalawave.workshop.validation

import io.scalawave.workshop.common._
import ActionType.ActionType
import Currency.Currency
import DataSource.DataSource

import scalaz._
import Scalaz._

object ScalazValidation {

  def parseDouble(double: String): ValidationNel[ParsingError, Double] =
    Validation.fromTryCatchNonFatal { double.trim.toDouble }
      .leftMap { _ => NotANumber(double) }
      .toValidationNel

  def parseNatural(natural: String): ValidationNel[ParsingError, Int] =
    Validation.fromTryCatchNonFatal { natural.trim.toInt }
      .leftMap { _ => NotANumber(natural) }
      .ensure(NotNatural(natural)) { _ >= 0 }
      .toValidationNel

  def parseActionType(actionType: String): ValidationNel[ParsingError, ActionType] =
    Validation.fromTryCatchNonFatal { ActionType.withName(actionType.trim.toLowerCase) }
      .leftMap { _ => InvalidActionType(actionType) }
      .toValidationNel

  def parseCurrency(currency: String): ValidationNel[ParsingError, Currency] =
    Validation.fromTryCatchNonFatal { Currency.withName(currency.trim.toLowerCase) }
      .leftMap { _ => InvalidCurrency(currency) }
      .toValidationNel

  def parseDataSource(dataSource: String): ValidationNel[ParsingError, DataSource] =
    Validation.fromTryCatchNonFatal { DataSource.withName(dataSource.trim.toLowerCase) }
      .leftMap { _ => InvalidDataSource(dataSource) }
      .toValidationNel

  def parseConfig(accuracy: String, dataSource: String): ValidationNel[ParsingError, Config] =
    (parseNatural(accuracy) |@| parseDataSource(dataSource)) { Config }
}
