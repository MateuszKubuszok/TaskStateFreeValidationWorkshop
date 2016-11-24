package io.scalawave.workshop.validation

import cats.data.{ Validated, ValidatedNel }
import cats.syntax.cartesian._
import io.scalawave.workshop.common._
import ActionType.ActionType
import Currency.Currency
import DataSource.DataSource

/**
 * Validation exercise - Cats
 *
 * In this exercise our goal will be very a simple validation of several simple inputs.
 *
 * In our application will read a program configuration (display accuracy, data source), next action to perform in
 * the main loop, converted currencies and amount of money to convert.
 *
 * Since we want to (potentially) store more than one error message, we will prefer ValidatedNel. We might use test
 * in order to verify correctness of our functions.
 *
 * Suggested imports:
 *
 * $ - [[cats.data.Validated]] - functions like
 * $ - [[cats.data.ValidatedNel]]
 * $ - [[cats.syntax.cartesian._]] - for access to applicative builder
 */
object CatsValidation {

  /**
   * Parses passed String into Double or returns error message(s).
   *
   * Hint: you can use Scala's build-in method for converting String into Double. You might also consider trimming
   * the text before conversion and mapping error to required type afterwards. Also pay attention that you'll most
   * likely end up with not-additive error type, while we would like to have NEL instead.
   *
   * @param double value to parse
   * @return error messages or value
   */
  def parseDouble(double: String): ValidatedNel[ParsingError, Double] =
    Validated.catchNonFatal { double.trim.toDouble }
      .leftMap { _ => NotANumber(double) }
      .toValidatedNel

  /**
   * Parses passed String into natural number (non-negative Int) or returns error message(s).
   *
   * Hint: you can solve it in a similar manner to [[parseDouble]] exercise. The difference is that once you end up with
   * Int, can should ensure that result is <= 0.
   *
   * @param natural value to parse
   * @return error messages or value
   */
  def parseNatural(natural: String): ValidatedNel[ParsingError, Int] =
    Validated.catchNonFatal { natural.trim.toInt }
      .leftMap { _ => NotANumber(natural) }
      .ensure(NotNatural(natural)) { _ >= 0 }
      .toValidatedNel

  /**
   * Parses passed String into ActionType or returns error message(s).
   *
   * Hint: each Enumeration type has a method for mapping value name to a value itself - as long as the name is trimmed
   * and has the same cases as String used during value creation it should parse it without exception. In such case we
   * only would have to adjust parsing failures to our error type.
   *
   * @param actionType value to parse
   * @return error messages or value
   */
  def parseActionType(actionType: String): ValidatedNel[ParsingError, ActionType] =
    Validated.catchNonFatal { ActionType.withName(actionType.trim.toLowerCase) }
      .leftMap { _ => InvalidActionType(actionType) }
      .toValidatedNel

  /**
   * Parses passed String into Currency or returns error message(s).
   *
   * Hint: you can reuse here your solution from [[parseActionType]].
   *
   * @param currency value to parse
   * @return error messages or value
   */
  def parseCurrency(currency: String): ValidatedNel[ParsingError, Currency] =
    Validated.catchNonFatal { Currency.withName(currency.trim.toLowerCase) }
      .leftMap { _ => InvalidCurrency(currency) }
      .toValidatedNel

  /**
   * Parses passed String into DataSource or returns error message(s).
   *
   * Hint: you can reuse here your solution from [[parseActionType]].
   *
   * @param dataSource value to parse
   * @return error messages or value
   */
  def parseDataSource(dataSource: String): ValidatedNel[ParsingError, DataSource] =
    Validated.catchNonFatal { DataSource.withName(dataSource.trim.toLowerCase) }
      .leftMap { _ => InvalidDataSource(dataSource) }
      .toValidatedNel

  /**
   * Parses passed Strings into Config or returns error message(s).
   *
   * Hint: we could reuse here [[parseNatural]] and [[parseDataSource]] - if only we had something like cartesian
   * builder which would combine them and apply as arguments to the constructor...
   *
   * @param accuracy String parsed into display accuracy (natual number)
   * @param dataSource String parsed into DataSource
   * @return
   */
  def parseConfig(accuracy: String, dataSource: String): ValidatedNel[ParsingError, Config] =
    (parseNatural(accuracy) |@| parseDataSource(dataSource)) map { Config }
}
