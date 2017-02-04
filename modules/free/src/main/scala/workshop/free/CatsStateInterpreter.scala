package workshop.free

import workshop.common._
import workshop.common.ActionType.ActionType
import workshop.common.Currency.Currency
import workshop.common.DataSource._

import scala.annotation.tailrec
import cats.~>
import cats.data._
import cats.data.Validated._

final class CatsCommandStateInterpreter(
    readLine:        () => String,
    writeLine:       String => Unit,
    parseActionType: String => ValidatedNel[ParsingError, ActionType],
    parseConfig:     (String, String) => ValidatedNel[ParsingError, Config]
) extends (CatsCommand ~> CatsConfigState) {

  import CatsCommand._

  override def apply[A](fa: CatsCommand[A]): CatsConfigState[A] = ???

  @tailrec
  private def getNextAction(question: String): ActionType = {
    writeLine(question)
    parseActionType(readLine()) match {
      case Valid(value) => value
      case Invalid(errors) =>
        writeLine("Errors:")
        errors.toList foreach { error => writeLine(s" - $error") }
        writeLine("Try again\n")
        getNextAction(question)
    }
  }

  @tailrec
  private def configure(question: String): Config = {
    writeLine(question)
    parseConfig(readLine(), readLine()) match {
      case Valid(value) => value
      case Invalid(errors) =>
        writeLine("Errors:")
        errors.toList foreach { error => writeLine(s" - $error") }
        writeLine("Try again\n")
        configure(question)
    }
  }

  private def quit(): Unit = writeLine("Exiting program")
}

final class CatsCalculationStateInterpreter(
    readLine:      () => String,
    writeLine:     String => Unit,
    parseCurrency: String => ValidatedNel[ParsingError, Currency],
    parseDouble:   String => ValidatedNel[ParsingError, Double],
    currencyQuery: Map[DataSource, Currency => Double]
) extends (CatsCalculation ~> CatsConfigState) {

  import CatsCalculation._

  override def apply[A](fa: CatsCalculation[A]): CatsConfigState[A] = fa match {
    case GetCurrency(question)     => State.pure[Config, Currency](getCurrency(question))
    case GetAmount(question)       => State.pure[Config, Double](getAmount(question))
    case Convert(from, to, amount) => State.inspect[Config, Double](convert(_, from, to, amount))
    case DisplayValue(value)       => State.inspect[Config, Unit](displayValue(_, value))
  }

  @tailrec
  private def getCurrency(question: String): Currency = {
    writeLine(question)
    parseCurrency(readLine()) match {
      case Valid(value) => value
      case Invalid(errors) =>
        writeLine("Errors:")
        errors.toList foreach { error => writeLine(s" - $error") }
        writeLine("Try again\n")
        getCurrency(question)
    }
  }

  @tailrec
  private def getAmount(question: String): Double = {
    writeLine(question)
    parseDouble(readLine()) match {
      case Valid(value) => value
      case Invalid(errors) =>
        writeLine("Errors:")
        errors.toList foreach { error => writeLine(s" - $error") }
        writeLine("Try again\n")
        getAmount(question)
    }
  }

  private def convert(config: Config, from: Currency, to: Currency, amount: Double): Double = {
    val dollarToX = currencyQuery(config.dataSource)(from)
    val xToDollar = 1.0 / dollarToX
    val dollarToY = currencyQuery(config.dataSource)(to)
    xToDollar * dollarToY * amount
  }

  private def displayValue(conf: Config, value: Double): Unit = writeLine(s"Result: %.${conf.accuracy}f" format value)
}
