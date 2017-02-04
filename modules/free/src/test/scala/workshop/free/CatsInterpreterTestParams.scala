package workshop.free

import cats.data.Validated._
import cats.data.ValidatedNel
import cats.syntax.cartesian._
import workshop.common._
import workshop.common.ActionType.ActionType
import workshop.common.Currency.Currency
import workshop.common.DataSource.DataSource

import scala.collection.mutable
import scala.util.Try

trait CatsInterpreterTestParams {

  protected val constantConversionRates =
    Map.empty[DataSource, Currency => Double].withDefaultValue((_: Currency) => 1.0)

  protected def readLineSimulation(lines: mutable.Queue[String])(): String =
    if (lines.nonEmpty) lines.dequeue else throw new IllegalStateException("More line reads than expected")

  protected def writeLineSimulation(lines: mutable.MutableList[String])(line: String): Unit = lines += line

  protected def simpleActionTypeValidation(name: String): ValidatedNel[ParsingError, ActionType] =
    Try { Valid(ActionType.withName(name)) } getOrElse { Invalid(InvalidActionType(name)) } toValidatedNel

  protected def simpleCurrencyValidation(name: String): ValidatedNel[ParsingError, Currency] =
    Try { Valid(Currency.withName(name)) } getOrElse { Invalid(InvalidCurrency(name)) } toValidatedNel

  protected def simpleDataTypeValidation(name: String): ValidatedNel[ParsingError, DataSource] =
    Try { Valid(DataSource.withName(name)) } getOrElse { Invalid(InvalidDataSource(name)) } toValidatedNel

  protected def simpleDoubleValidation(value: String): ValidatedNel[ParsingError, Double] =
    Try { Valid(value.toDouble) } getOrElse { Invalid(NotANumber(value)) } toValidatedNel

  protected def simpleNumberValidation(value: String): ValidatedNel[ParsingError, Int] =
    Try { Valid(value.toInt) } getOrElse { Invalid(NotNatural(value)) } toValidatedNel

  protected def simpleConfigValidation(accuracy: String, dataSource: String): ValidatedNel[ParsingError, Config] =
    (simpleNumberValidation(accuracy) |@| simpleDataTypeValidation(dataSource)) map { Config }
}
