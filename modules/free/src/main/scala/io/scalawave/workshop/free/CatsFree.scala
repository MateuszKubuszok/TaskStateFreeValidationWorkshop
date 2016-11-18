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

    def getNextAction(question: String): Free[S, ActionType] = Free.liftF(s0.inj(GetNextAction(question)))
    def configure(question: String): Free[S, Unit] = Free.liftF(s0.inj(Configure(question)))
    def quit: Free[S, Unit] = Free.liftF(s0.inj(Quit))
  }
}

sealed trait CatsCalculation[Result]

object CatsCalculation {

  final case class GetCurrency(question: String) extends CatsCalculation[Currency]
  final case class GetAmount(question: String) extends CatsCalculation[Double]
  final case class Convert(from: Currency, to: Currency, amount: Double) extends CatsCalculation[Double]
  final case class DisplayValue(value: Double) extends CatsCalculation[Unit]

  class Ops[S[_]](implicit s0: CatsCalculation :<: S) {

    def getCurrency(question: String): Free[S, Currency] = Free.liftF(s0.inj(GetCurrency(question)))
    def getAmount(question: String): Free[S, Double] = Free.liftF(s0.inj(GetAmount(question)))
    def convert(from: Currency, to: Currency, amount: Double): Free[S, Double] =
      Free.liftF(s0.inj(Convert(from, to, amount)))
    def displayValue(value: Double): Free[S, Unit] = Free.liftF(s0.inj(DisplayValue(value)))
  }
}
