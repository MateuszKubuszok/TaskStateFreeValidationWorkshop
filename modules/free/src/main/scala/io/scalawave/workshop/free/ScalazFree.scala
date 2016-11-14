package io.scalawave.workshop.free

import io.scalawave.workshop.common.ActionType.ActionType
import io.scalawave.workshop.common.Currency.Currency

import scalaz.{ :<:, Free }

sealed trait ScalazCommand[Result]

object ScalazCommand {

  final case class GetNextAction(question: String) extends ScalazCommand[ActionType]
  final case class Configure(question: String) extends ScalazCommand[Unit]
  case object Quit extends ScalazCommand[Unit]

  class Ops[S[_]](implicit s0: ScalazCommand :<: S) {

    def getNextAction(question: String): Free[ScalazCommand, ActionType] = Free.liftF(GetNextAction(question))
    def configure(question: String): Free[ScalazCommand, Unit] = Free.liftF(Configure(question))
    def quit: Free[ScalazCommand, Unit] = Free.liftF(Quit)
  }
}

sealed trait ScalazCalculation[Result]

object ScalazCalculation {

  final case class GetCurrency(question: String) extends ScalazCalculation[Currency]
  final case class GetAmount(question: String) extends ScalazCalculation[Double]
  final case class Convert(from: Currency, to: Currency, amount: Double) extends ScalazCalculation[Double]
  final case class DisplayValue(value: Double) extends ScalazCalculation[Unit]

  class Ops[S[_]](implicit s0: ScalazCalculation :<: S) {

    def getCurrency(question: String): Free[ScalazCalculation, Currency] =
      Free.liftF(GetCurrency(question))
    def getAmount(question: String): Free[ScalazCalculation, Double] =
      Free.liftF(GetAmount(question))
    def convert(from: Currency, to: Currency, amount: Double): Free[ScalazCalculation, Double] =
      Free.liftF(Convert(from, to, amount))
    def displayValue(value: Double): Free[ScalazCalculation, Unit] =
      Free.liftF(DisplayValue(value))
  }
}
