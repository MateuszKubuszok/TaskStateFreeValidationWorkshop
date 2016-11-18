package io.scalawave.workshop.summary

import io.scalawave.workshop.common.Config
import io.scalawave.workshop.common.Currency._
import io.scalawave.workshop.common.DataSource._
import io.scalawave.workshop.free.{ CatsCalculationStateInterpreter, CatsCommandStateInterpreter, CatsProgram }
import io.scalawave.workshop.task.{ CurrencyOnlineServiceHandler, CurrencyDataBaseHandler }
import io.scalawave.workshop.validation.CatsValidation._

object CatsMain {

  val program = CatsProgram.program

  val dataSources = Map[DataSource, Currency => Double](
    DataBase -> CurrencyDataBaseHandler,
    Internet -> CurrencyOnlineServiceHandler
  )

  val interpreter = {
    val commandInterpreter = new CatsCommandStateInterpreter(
      scala.io.StdIn.readLine,
      println,
      parseActionType,
      parseConfig
    )

    val calculationInterpreter = new CatsCalculationStateInterpreter(
      scala.io.StdIn.readLine,
      println,
      parseCurrency,
      parseDouble,
      dataSources
    )

    commandInterpreter or calculationInterpreter
  }

  val initialState = Config(2, DataBase)

  def main(args: Array[String]): Unit = program.foldMap(interpreter).run(initialState).value
}
