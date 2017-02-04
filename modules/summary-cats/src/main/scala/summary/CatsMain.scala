package workshop.summary

import workshop.common.Config
import workshop.common.Currency._
import workshop.common.DataSource._
import workshop.free.{ CatsCalculationStateInterpreter, CatsCommandStateInterpreter, CatsProgram }
import workshop.task.{ CurrencyOnlineServiceHandler, CurrencyDataBaseHandler }
import workshop.validation.CatsValidation._

object CatsMain {

  // val program = CatsProgram.program

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

  def main(args: Array[String]): Unit = () // program.foldMap(interpreter).run(initialState).value
}
