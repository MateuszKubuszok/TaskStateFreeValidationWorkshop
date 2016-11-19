package io.scalawave.workshop.free

import io.scalawave.workshop.common.{ Currency, Config, DataSource, ActionType }

import scala.util.Try

/**
 * Free demo
 *
 * You can use this main function for checking how your free functions would work in action.
 *
 * Make sure to look at the test to see how one can test free programs and interpreters.
 */
object Main {

  def main(args: Array[String]): Unit = {
    println("Cats test")
    println()
    catsIdTest()
    println()
    println("Scalaz test")
    scalazIdTest()
  }

  def catsIdTest(): Unit = {
    import cats.data.Validated._

    val configStore = ConfigStore(Config(2, DataSource.DataBase))
    val commandInterpreter = new CatsCommandIdInterpreter(
      () => scala.io.StdIn.readLine(),
      println,
      name => Valid(Try(ActionType.withName(name)).getOrElse(ActionType.Convert)),
      (_, _) => Valid(Config(2, DataSource.DataBase)),
      configStore
    )
    val calculationInterpreter = new CatsCalculationIdInterpreter(
      () => scala.io.StdIn.readLine(),
      println,
      name => Valid(Try(Currency.withName(name)).getOrElse(Currency.USD)),
      amount => Valid(Try(amount.toDouble).getOrElse(1.0)),
      Map.empty[DataSource.DataSource, Currency.Currency => Double].withDefaultValue(_ => 1.0),
      configStore
    )
    val interpreter = commandInterpreter or calculationInterpreter

    CatsProgram.program.foldMap(interpreter)
  }

  def scalazIdTest(): Unit = {
    import scalaz._
    import Scalaz._
    import ScalazUtils._

    val configStore = ConfigStore(Config(2, DataSource.DataBase))
    val commandInterpreter = new ScalazCommandIdInterpreter(
      () => scala.io.StdIn.readLine(),
      println,
      name => Try(ActionType.withName(name)).getOrElse(ActionType.Convert).success,
      (_, _) => Config(2, DataSource.DataBase).success,
      configStore
    )
    val calculationInterpreter = new ScalazCalculationIdInterpreter(
      () => scala.io.StdIn.readLine(),
      println,
      name => Try(Currency.withName(name)).getOrElse(Currency.USD).success,
      amount => Try(amount.toDouble).getOrElse(1.0).success,
      Map.empty[DataSource.DataSource, Currency.Currency => Double].withDefaultValue(_ => 1.0),
      configStore
    )
    val interpreter = commandInterpreter :+: calculationInterpreter

    ScalazProgram.program.foldMap(interpreter)
  }
}
