package io.scalawave.workshop.free

import cats.free.{ Free, :<: }
import io.scalawave.workshop.common.ActionType.ActionType
import io.scalawave.workshop.common.Currency.Currency

sealed trait CatsCommand[Result]

object CatsCommand {

  final case class GetNextAction(question: String) extends CatsCommand[ActionType]
  final case class Configure(question: String) extends CatsCommand[Unit]
  case object Quit extends CatsCommand[Unit]

  class Ops[S[_]](implicit s0: CatsCommand :<: S) {

    def getNextAction(question: String): Free[CatsCommand, ActionType] = Free.liftF(GetNextAction(question))
    def configure(question: String): Free[CatsCommand, Unit] = Free.liftF(Configure(question))
    def quit: Free[CatsCommand, Unit] = Free.liftF(Quit)
  }
}

sealed trait CatsCalculation[Result]

object CatsCalculation {

  final case class GetCurrency(question: String) extends CatsCalculation[Currency]
  final case class GetAmount(question: String) extends CatsCalculation[Double]
  final case class Convert(from: Currency, to: Currency, amount: Double) extends CatsCalculation[Double]
  final case class DisplayValue(value: Double) extends CatsCalculation[Unit]

  class Ops[S[_]](implicit s0: CatsCalculation :<: S) {

    def getCurrency(question: String): Free[CatsCalculation, Currency] =
      Free.liftF(GetCurrency(question))
    def getAmount(question: String): Free[CatsCalculation, Double] =
      Free.liftF(GetAmount(question))
    def convert(from: Currency, to: Currency, amount: Double): Free[CatsCalculation, Double] =
      Free.liftF(Convert(from, to, amount))
    def displayValue(value: Double): Free[CatsCalculation, Unit] =
      Free.liftF(DisplayValue(value))
  }
}
