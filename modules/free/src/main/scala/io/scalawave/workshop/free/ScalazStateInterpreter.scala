package io.scalawave.workshop.free

import io.scalawave.workshop.common.ActionType.ActionType
import io.scalawave.workshop.common.Currency.Currency
import io.scalawave.workshop.common.DataSource.DataSource
import io.scalawave.workshop.common.{ Config, ParsingError, ScalazConfigState }

import scala.annotation.tailrec
import scalaz._

final class ScalazCommandStateInterpreter(
    readLine:        () => String,
    writeLine:       String => Unit,
    parseActionType: String => ValidationNel[ParsingError, ActionType],
    parseConfig:     (String, String) => ValidationNel[ParsingError, Config]
) extends (ScalazCommand ~> ScalazConfigState) {

  import ScalazCommand._

  override def apply[A](fa: ScalazCommand[A]): ScalazConfigState[A] = fa match {
    case GetNextAction(question) => State.state[Config, ActionType](getNextAction(question))
    case Configure(question)     => State.put(configure(question))
    case Quit                    => State.state[Config, Unit](quit())
  }

  @tailrec
  private def getNextAction(question: String): ActionType = {
    writeLine(question)
    parseActionType(readLine()) match {
      case Success(value) => value
      case Failure(errors) =>
        writeLine("Errors:")
        errors foreach { error => writeLine(s" - $error") }
        writeLine("Try again\n")
        getNextAction(question)
    }
  }

  @tailrec
  private def configure(question: String): Config = {
    writeLine(question)
    parseConfig(readLine(), readLine()) match {
      case Success(value) => value
      case Failure(errors) =>
        writeLine("Errors:")
        errors foreach { error => writeLine(s" - $error") }
        writeLine("Try again\n")
        configure(question)
    }
  }

  private def quit(): Unit = writeLine("Exiting program")
}

final class ScalazCalculationStateInterpreter(
    readLine:      () => String,
    writeLine:     String => Unit,
    parseCurrency: String => ValidationNel[ParsingError, Currency],
    parseDouble:   String => ValidationNel[ParsingError, Double],
    currencyQuery: Map[DataSource, Currency => Double]
) extends (ScalazCalculation ~> ScalazConfigState) {

  import ScalazCalculation._

  override def apply[A](fa: ScalazCalculation[A]): ScalazConfigState[A] = fa match {
    case GetCurrency(question)     => State.state[Config, Currency](getCurrency(question))
    case GetAmount(question)       => State.state[Config, Double](getAmount(question))
    case Convert(from, to, amount) => State.gets[Config, Double](convert(_, from, to, amount))
    case DisplayValue(value)       => State.gets[Config, Unit](displayValue(_, value))
  }

  @tailrec
  private def getCurrency(question: String): Currency = {
    writeLine(question)
    parseCurrency(readLine()) match {
      case Success(value) => value
      case Failure(errors) =>
        writeLine("Errors:")
        errors foreach { error => writeLine(s" - $error") }
        writeLine("Try again\n")
        getCurrency(question)
    }
  }

  @tailrec
  private def getAmount(question: String): Double = {
    writeLine(question)
    parseDouble(readLine()) match {
      case Success(value) => value
      case Failure(errors) =>
        writeLine("Errors:")
        errors foreach { error => writeLine(s" - $error") }
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
