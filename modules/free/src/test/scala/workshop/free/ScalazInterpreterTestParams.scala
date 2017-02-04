package workshop.free

import workshop.common._
import workshop.common.ActionType._
import workshop.common.Currency._
import workshop.common.DataSource._

import scala.collection.mutable
import scala.util.Try
import scalaz._
import Scalaz._

trait ScalazInterpreterTestParams {
  protected val constantConversionRates =
    Map.empty[DataSource, Currency => Double].withDefaultValue((_: Currency) => 1.0)

  protected def readLineSimulation(lines: mutable.Queue[String])(): String =
    if (lines.nonEmpty) lines.dequeue else throw new IllegalStateException("More line reads than expected")

  protected def writeLineSimulation(lines: mutable.MutableList[String])(line: String): Unit = lines += line

  protected def simpleActionTypeValidation(name: String): ValidationNel[ParsingError, ActionType] = Try {
    ActionType.withName(name).successNel[ParsingError]
  } getOrElse {
    (InvalidActionType(name): ParsingError).failureNel[ActionType]
  }

  protected def simpleCurrencyValidation(name: String): ValidationNel[ParsingError, Currency] = Try {
    Currency.withName(name).successNel[ParsingError]
  } getOrElse {
    (InvalidCurrency(name): ParsingError).failureNel[Currency]
  }

  protected def simpleDataTypeValidation(name: String): ValidationNel[ParsingError, DataSource] = Try {
    DataSource.withName(name).successNel[ParsingError]
  } getOrElse {
    (InvalidDataSource(name): ParsingError).failureNel
  }

  protected def simpleDoubleValidation(value: String): ValidationNel[ParsingError, Double] = Try {
    value.toDouble.successNel[ParsingError]
  } getOrElse {
    (NotANumber(value): ParsingError).failureNel
  }

  protected def simpleNumberValidation(value: String): ValidationNel[ParsingError, Int] = Try {
    value.toInt.successNel[ParsingError]
  } getOrElse {
    (NotNatural(value): ParsingError).failureNel
  }

  protected def simpleConfigValidation(accuracy: String, dataSource: String): ValidationNel[ParsingError, Config] =
    (simpleNumberValidation(accuracy) |@| simpleDataTypeValidation(dataSource)) { Config }
}
