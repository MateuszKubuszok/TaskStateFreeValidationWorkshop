package workshop.free

import workshop.common.ActionType.ActionType
import workshop.common.Currency.Currency

import scalaz.{ :<:, Free }

/*
  * Free exercises - Scalaz
  *
  * 1. Simple free operations
  *
  * In the first exercise we'll try to build free DSLs in the simplest possible way. Such DSLs will be pretty
  * troublesome to combine into bigger, more powerful DSLs, but they should give us some intuition into how free works.
  *
  * 2. Composable free
  *
  * In our next exercise we will modify Ops to make DSL composable. With that our 2 DSLs could be merged together into
  * something resembling our intended program. Our first attempt will use Id - we can think of it as a transparent
  * wrapper type. It can be used each time, code expects parametrized type and we don't need to wrap values int anything
  * in particular.
  */

sealed trait ScalazCommand[Result]

object ScalazCommand {

  final case class GetNextAction(question: String) extends ScalazCommand[ActionType]
  final case class Configure(question: String) extends ScalazCommand[Unit]
  case object Quit extends ScalazCommand[Unit]

  class Ops {

    def getNextAction(question: String): Free[ScalazCommand, ActionType] = ???
    def configure(question: String): Free[ScalazCommand, Unit] = ???
    def quit: Free[ScalazCommand, Unit] = ???
  }
}

sealed trait ScalazCalculation[Result]

object ScalazCalculation {

  final case class GetCurrency(question: String) extends ScalazCalculation[Currency]
  final case class GetAmount(question: String) extends ScalazCalculation[Double]
  final case class Convert(from: Currency, to: Currency, amount: Double) extends ScalazCalculation[Double]
  final case class DisplayValue(value: Double) extends ScalazCalculation[Unit]

  class Ops {

    def getCurrency(question: String): Free[ScalazCalculation, Currency] = ???
    def getAmount(question: String): Free[ScalazCalculation, Double] = ???
    def convert(from: Currency, to: Currency, amount: Double): Free[ScalazCalculation, Double] =
      ???
    def displayValue(value: Double): Free[ScalazCalculation, Unit] = ???
  }
}
