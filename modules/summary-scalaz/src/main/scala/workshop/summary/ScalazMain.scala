package workshop.summary

import workshop.common.Config
import workshop.common.Currency._
import workshop.common.DataSource._
import workshop.free.{ ScalazCalculationStateInterpreter, ScalazCommandStateInterpreter, ScalazProgram }
import workshop.free.ScalazCoproductUtils._
import workshop.task.{ CurrencyOnlineServiceHandler, CurrencyDataBaseHandler }
import workshop.validation.ScalazValidation._

object ScalazMain {

  // val program = ScalazProgram.program

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

  def main(args: Array[String]): Unit = () // program.foldMap(interpreter).eval(initialState)
}
