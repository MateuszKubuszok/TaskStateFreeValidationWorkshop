package workshop.validation

import workshop.common._
import ActionType.ActionType
import Currency.Currency
import DataSource.DataSource
import org.specs2.mutable.Specification

import scalaz._
import Scalaz._

class ScalaValidationSpec extends Specification {

  val expectedDouble = 12.0
  val expectedNatural = 12
  val expectedActionType = ActionType.Convert
  val expectedCurrency = Currency.EUR
  val expectedDataSource = DataSource.DataBase
  val expectedConfig = Config(expectedNatural, expectedDataSource)

  "double validation" should {

    import ScalazValidation.parseDouble

    "accept positive number" in {
      // given
      val number = "12"

      // when
      val result = parseDouble(number)

      // then
      result shouldEqual expectedDouble.success[ParsingError]
    }

    "accept 0" in {
      // given
      val number = "0"

      // when
      val result = parseDouble(number)

      // then
      result shouldEqual 0.0.success[ParsingError]
    }

    "accept number wrapped in whitespaces" in {
      // given
      val number = "\t  0  \t"

      // when
      val result = parseDouble(number)

      // then
      result shouldEqual 0.0.success[ParsingError]
    }

    "accept negative number" in {
      // given
      val number = "-12"

      // when
      val result = parseDouble(number)

      // then
      result shouldEqual (-expectedDouble).success[ParsingError]
    }

    "reject invalid number" in {
      // given
      val number = "-12--"

      // when
      val result = parseDouble(number)

      // then
      result shouldEqual NotANumber(number).failureNel[Int]
    }
  }

  "natural validation" should {

    import ScalazValidation.parseNatural

    "accept positive number" in {
      // given
      val number = "12"

      // when
      val result = parseNatural(number)

      // then
      result shouldEqual expectedNatural.success[ParsingError]
    }

    "accept 0" in {
      // given
      val number = "0"

      // when
      val result = parseNatural(number)

      // then
      result shouldEqual 0.success[ParsingError]
    }

    "accept number wrapped in whitespaces" in {
      // given
      val number = "\t  0  \t"

      // when
      val result = parseNatural(number)

      // then
      result shouldEqual 0.success[ParsingError]
    }

    "reject negative number" in {
      // given
      val number = "-12"

      // when
      val result = parseNatural(number)

      // then
      result shouldEqual NotNatural(number).failureNel[Int]
    }

    "reject invalid number" in {
      // given
      val number = "-12--"

      // when
      val result = parseNatural(number)

      // then
      result shouldEqual NotANumber(number).failureNel[Int]
    }
  }

  "action type validation" should {

    import ScalazValidation.parseActionType

    "accept valid action type" in {
      // given
      val actionType = "convert"

      // when
      val result = parseActionType(actionType)

      // then
      result shouldEqual expectedActionType.success[ParsingError]
    }

    "accept valid acton type with different cases" in {
      // given
      val actionType = "Convert"

      // when
      val result = parseActionType(actionType)

      // then
      result shouldEqual expectedActionType.success[ParsingError]
    }

    "accept valid acton type wrapped in whitespaces" in {
      // given
      val actionType = "  Convert\t"

      // when
      val result = parseActionType(actionType)

      // then
      result shouldEqual expectedActionType.success[ParsingError]
    }

    "reject invalid action type" in {
      // given
      val actionType = "invalid"

      // when
      val result = parseActionType(actionType)

      // then
      result shouldEqual InvalidActionType(actionType).failureNel[ActionType]
    }
  }

  "currency validation" should {

    import ScalazValidation.parseCurrency

    "accept valid currency" in {
      // given
      val currency = "eur"

      // when
      val result = parseCurrency(currency)

      // then
      result shouldEqual expectedCurrency.success[ParsingError]
    }

    "accept valid currency with different cases" in {
      // given
      val currency = "EUR"

      // when
      val result = parseCurrency(currency)

      // then
      result shouldEqual expectedCurrency.success[ParsingError]
    }

    "accept valid currency wrapped in whitespaces" in {
      // given
      val currency = "  EUR\t"

      // when
      val result = parseCurrency(currency)

      // then
      result shouldEqual expectedCurrency.success[ParsingError]
    }

    "reject invalid currency" in {
      // given
      val currency = "invalid"

      // when
      val result = parseCurrency(currency)

      // then
      result shouldEqual InvalidCurrency(currency).failureNel[Currency]
    }
  }

  "data source validation" should {

    import ScalazValidation.parseDataSource

    "accept valid data source" in {
      // given
      val dataSource = "database"

      // when
      val result = parseDataSource(dataSource)

      // then
      result shouldEqual expectedDataSource.success[ParsingError]
    }

    "accept valid data source with different cases" in {
      // given
      val dataSource = "DataBase"

      // when
      val result = parseDataSource(dataSource)

      // then
      result shouldEqual expectedDataSource.success[ParsingError]
    }

    "accept valid data source wrapped in whitespaces" in {
      // given
      val dataSource = "  DataBase\t"

      // when
      val result = parseDataSource(dataSource)

      // then
      result shouldEqual expectedDataSource.success[ParsingError]
    }

    "reject invalid data source" in {
      // given
      val dataSource = "invalid"

      // when
      val result = parseDataSource(dataSource)

      // then
      result shouldEqual InvalidDataSource(dataSource).failureNel[DataSource]
    }
  }

  "config validation" should {

    import ScalazValidation.parseConfig

    "accept all valid params" in {
      // given
      val number = "12"
      val dataSource = "database"

      // when
      val result = parseConfig(number, dataSource)

      // then
      result shouldEqual expectedConfig.success[ParsingError]
    }

    "reject invalid number" in {
      // given
      val number = "-12"
      val dataSource = "DataBase"

      // when
      val result = parseConfig(number, dataSource)

      // then
      result shouldEqual NotNatural(number).failureNel[Config]
    }

    "reject invalid datasource" in {
      // given
      val number = "12"
      val dataSource = "invalid"

      // when
      val result = parseConfig(number, dataSource)

      // then
      result shouldEqual InvalidDataSource(dataSource).failureNel[Config]
    }
  }
}
