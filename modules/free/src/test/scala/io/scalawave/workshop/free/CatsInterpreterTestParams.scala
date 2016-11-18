package io.scalawave.workshop.free

import cats.data.Validated._
import cats.data.ValidatedNel
import io.scalawave.workshop.common.Currency.Currency
import io.scalawave.workshop.common.DataSource.DataSource
import io.scalawave.workshop.common._

import scala.collection.mutable
import scala.util.Try

trait CatsInterpreterTestParams {

  protected val constantConversionRates =
    Map.empty[DataSource, Currency => Double].withDefaultValue((_: Currency) => 1.0)

  protected def readLineSimulation(lines: mutable.Queue[String])(): String =
    if (lines.nonEmpty) lines.dequeue else throw new IllegalStateException("More line reads than expected")

  protected def writeLineSimulation(lines: mutable.MutableList[String])(line: String): Unit = lines += line

  protected def simpleActionTypeValidation(name: String): ValidatedNel[ParsingError, ActionType.Value] = Try {
    Valid(ActionType.withName(name))
  } getOrElse {
    Invalid[ParsingError](InvalidActionType(name))
  } toValidatedNel

  protected def simpleCurrencyValidation(name: String): ValidatedNel[ParsingError, Currency.Value] = Try {
    Valid(Currency.withName(name))
  } getOrElse {
    Invalid[ParsingError](InvalidCurrency(name))
  } toValidatedNel

  protected def simpleDataTypeValidation(name: String): ValidatedNel[ParsingError, DataSource.Value] = Try {
    Valid(DataSource.withName(name))
  } getOrElse {
    Invalid[ParsingError](InvalidDataSource(name))
  } toValidatedNel

  protected def simpleDoubleValidation(value: String): ValidatedNel[ParsingError, Double] = Try {
    Valid(value.toDouble)
  } getOrElse {
    Invalid[ParsingError](NotANumber(value))
  } toValidatedNel
}
