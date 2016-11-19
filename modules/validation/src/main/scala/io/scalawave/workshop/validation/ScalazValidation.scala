package io.scalawave.workshop.validation

import io.scalawave.workshop.common._
import ActionType.ActionType
import Currency.Currency
import DataSource.DataSource

import scalaz._
import Scalaz._

/** Validation exercise - Scalaz
  *
  * In this exercise our goal will be very a simple validation of several simple inputs.
  *
  * In our application will read a program configuration (display accuracy, data source), next action to perform in
  * the main loop, converted currencies and amount of money to convert.
  *
  * Since we want to (potentially) store more than one error message, we will prefer ValidationNel. We might use test
  * in order to verify correctness of our functions.
  */
object ScalazValidation {

  /** Parses passed String into Double or returns error message(s).
    *
    * @param double value to parse
    * @return error messages or value
    */
  def parseDouble(double: String): ValidationNel[ParsingError, Double] =
    Validation.fromTryCatchNonFatal { double.trim.toDouble }
      .leftMap { _ => NotANumber(double) }
      .toValidationNel

  /** Parses passed String into natural number (positive Int) or returns error message(s).
    *
    * @param natural value to parse
    * @return error messages or value
    */
  def parseNatural(natural: String): ValidationNel[ParsingError, Int] =
    Validation.fromTryCatchNonFatal { natural.trim.toInt }
      .leftMap { _ => NotANumber(natural) }
      .ensure(NotNatural(natural)) { _ >= 0 }
      .toValidationNel

  /** Parses passed String into ActionType or returns error message(s).
    *
    * @param actionType value to parse
    * @return error messages or value
    */
  def parseActionType(actionType: String): ValidationNel[ParsingError, ActionType] =
    Validation.fromTryCatchNonFatal { ActionType.withName(actionType.trim.toLowerCase) }
      .leftMap { _ => InvalidActionType(actionType) }
      .toValidationNel

  /** Parses passed String into Currency or returns error message(s).
    *
    * @param currency value to parse
    * @return error messages or value
    */
  def parseCurrency(currency: String): ValidationNel[ParsingError, Currency] =
    Validation.fromTryCatchNonFatal { Currency.withName(currency.trim.toLowerCase) }
      .leftMap { _ => InvalidCurrency(currency) }
      .toValidationNel

  /** Parses passed String into DataSource or returns error message(s).
    *
    * @param dataSource value to parse
    * @return error messages or value
    */
  def parseDataSource(dataSource: String): ValidationNel[ParsingError, DataSource] =
    Validation.fromTryCatchNonFatal { DataSource.withName(dataSource.trim.toLowerCase) }
      .leftMap { _ => InvalidDataSource(dataSource) }
      .toValidationNel

  /** Parses passed Strings into Config or returns error message(s).
    *
    * @param accuracy String parsed into display accuracy
    * @param dataSource String parsed into DataSource
    * @return
    */
  def parseConfig(accuracy: String, dataSource: String): ValidationNel[ParsingError, Config] =
    (parseNatural(accuracy) |@| parseDataSource(dataSource)) { Config }
}
