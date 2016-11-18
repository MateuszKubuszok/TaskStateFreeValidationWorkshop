package io.scalawave.workshop.summary

import io.scalawave.workshop.common.Config
import io.scalawave.workshop.common.Currency._
import io.scalawave.workshop.common.DataSource._
import io.scalawave.workshop.free.{ ScalazCalculationStateInterpreter, ScalazCommandStateInterpreter, ScalazProgram }
import io.scalawave.workshop.free.ScalazUtils._
import io.scalawave.workshop.task.{ CurrencyOnlineServiceHandler, CurrencyDataBaseHandler }
import io.scalawave.workshop.validation.ScalazValidation._

object ScalazMain {

  val program = ScalazProgram.program

  val dataSources = Map[DataSource, Currency => Double](
    DataBase -> CurrencyDataBaseHandler,
    Internet -> CurrencyOnlineServiceHandler
  )

  val interpreter = {
    val commandInterpreter = new ScalazCommandStateInterpreter(
      scala.io.StdIn.readLine,
      println,
      parseActionType,
      parseConfig
    )

    val calculationInterpreter = new ScalazCalculationStateInterpreter(
      scala.io.StdIn.readLine,
      println,
      parseCurrency,
      parseDouble,
      dataSources
    )

    commandInterpreter :+: calculationInterpreter
  }

  val initialState = Config(2, DataBase)

  def main(args: Array[String]): Unit = program.foldMap(interpreter).eval(initialState)
}
