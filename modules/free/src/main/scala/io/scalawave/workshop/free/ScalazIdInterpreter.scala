package io.scalawave.workshop.free

import io.scalawave.workshop.common.ActionType.ActionType
import io.scalawave.workshop.common.Currency.Currency
import io.scalawave.workshop.common.DataSource.DataSource
import io.scalawave.workshop.common.{ Config, ParsingError }

import scala.annotation.tailrec
import scalaz._
import Scalaz._

final class ScalazCommandIdInterpreter(
    readLine:        () => String,
    writeLine:       String => Unit,
    parseActionType: String => ValidationNel[ParsingError, ActionType],
    parseConfig:     (String, String) => ValidationNel[ParsingError, Config],
    configStore:     ConfigStore
) extends (ScalazCommand ~> Id) {

  import ScalazCommand._

  override def apply[A](fa: ScalazCommand[A]): Id[A] = ???

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

final class ScalazCalculationIdInterpreter(
    readLine:      () => String,
    writeLine:     String => Unit,
    parseCurrency: String => ValidationNel[ParsingError, Currency],
    parseDouble:   String => ValidationNel[ParsingError, Double],
    currencyQuery: Map[DataSource, Currency => Double],
    configStore:   ConfigStore
) extends (ScalazCalculation ~> Id) {

  import ScalazCalculation._

  override def apply[A](fa: ScalazCalculation[A]): Id[A] = ???

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
