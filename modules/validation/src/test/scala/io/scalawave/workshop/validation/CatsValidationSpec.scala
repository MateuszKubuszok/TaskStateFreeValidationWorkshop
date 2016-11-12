package io.scalawave.workshop.validation

import cats.data.Validated
import Validated._
import io.scalawave.workshop.common._
import ActionType.ActionType
import Currency.Currency
import DataSource.DataSource
import org.specs2.mutable.Specification

class CatsValidationSpec extends Specification {

  val expectedDouble = 12.0
  val expectedNatural = 12
  val expectedActionType = ActionType.Convert
  val expectedCurrency = Currency.EUR
  val expectedDataSource = DataSource.DataBase
  val expectedConfig = Config(expectedNatural, expectedDataSource)

  "double validation" should {

    import CatsValidation.parseDouble

    "accept positive number" in {
      // given
      val number = "12"

      // when
      val result = parseDouble(number)

      // then
      result shouldEqual valid[ParsingError, Double](expectedDouble)
    }

    "accept 0" in {
      // given
      val number = "0"

      // when
      val result = parseDouble(number)

      // then
      result shouldEqual valid[ParsingError, Double](0.0)
    }

    "accept number wrapped in whitespaces" in {
      // given
      val number = "\t  0  \t"

      // when
      val result = parseDouble(number)

      // then
      result shouldEqual valid[ParsingError, Double](0.0)
    }

    "accept negative number" in {
      // given
      val number = "-12"

      // when
      val result = parseDouble(number)

      // then
      result shouldEqual valid[ParsingError, Double](-expectedDouble)
    }

    "reject invalid number" in {
      // given
      val number = "-12--"

      // when
      val result = parseDouble(number)

      // then
      result shouldEqual invalidNel[ParsingError, Double](NotANumber(number))
    }
  }

  "natural validation" should {

    import CatsValidation.parseNatural

    "accept positive number" in {
      // given
      val number = "12"

      // when
      val result = parseNatural(number)

      // then
      result shouldEqual valid[ParsingError, Int](expectedNatural)
    }

    "accept 0" in {
      // given
      val number = "0"

      // when
      val result = parseNatural(number)

      // then
      result shouldEqual valid[ParsingError, Int](0)
    }

    "accept number wrapped in whitespaces" in {
      // given
      val number = "\t  0  \t"

      // when
      val result = parseNatural(number)

      // then
      result shouldEqual valid[ParsingError, Int](0)
    }

    "reject negative number" in {
      // given
      val number = "-12"

      // when
      val result = parseNatural(number)

      // then
      result shouldEqual invalidNel[ParsingError, Int](NotNatural(number))
    }

    "reject invalid number" in {
      // given
      val number = "-12--"

      // when
      val result = parseNatural(number)

      // then
      result shouldEqual invalidNel[ParsingError, Int](NotANumber(number))
    }
  }

  "action type validation" should {

    import CatsValidation.parseActionType

    "accept valid action type" in {
      // given
      val actionType = "convert"

      // when
      val result = parseActionType(actionType)

      // then
      result shouldEqual valid[ParsingError, ActionType](expectedActionType)
    }

    "accept valid acton type with different cases" in {
      // given
      val actionType = "Convert"

      // when
      val result = parseActionType(actionType)

      // then
      result shouldEqual valid[ParsingError, ActionType](expectedActionType)
    }

    "accept valid acton type wrapped in whitespaces" in {
      // given
      val actionType = "  Convert\t"

      // when
      val result = parseActionType(actionType)

      // then
      result shouldEqual valid[ParsingError, ActionType](expectedActionType)
    }

    "reject invalid action type" in {
      // given
      val actionType = "invalid"

      // when
      val result = parseActionType(actionType)

      // then
      result shouldEqual invalidNel[ParsingError, ActionType](InvalidActionType(actionType))
    }
  }

  "currency validation" should {

    import CatsValidation.parseCurrency

    "accept valid currency" in {
      // given
      val currency = "eur"

      // when
      val result = parseCurrency(currency)

      // then
      result shouldEqual valid[ParsingError, Currency](expectedCurrency)
    }

    "accept valid currency with different cases" in {
      // given
      val currency = "EUR"

      // when
      val result = parseCurrency(currency)

      // then
      result shouldEqual valid[ParsingError, Currency](expectedCurrency)
    }

    "accept valid currency wrapped in whitespaces" in {
      // given
      val currency = "  EUR\t"

      // when
      val result = parseCurrency(currency)

      // then
      result shouldEqual valid[ParsingError, Currency](expectedCurrency)
    }

    "reject invalid currency" in {
      // given
      val currency = "invalid"

      // when
      val result = parseCurrency(currency)

      // then
      result shouldEqual invalidNel[ParsingError, Currency](InvalidCurrency(currency))
    }
  }

  "data source validation" should {

    import CatsValidation.parseDataSource

    "accept valid data source" in {
      // given
      val dataSource = "database"

      // when
      val result = parseDataSource(dataSource)

      // then
      result shouldEqual valid[ParsingError, DataSource](expectedDataSource)
    }

    "accept valid data source with different cases" in {
      // given
      val dataSource = "DataBase"

      // when
      val result = parseDataSource(dataSource)

      // then
      result shouldEqual valid[ParsingError, DataSource](expectedDataSource)
    }

    "accept valid data source wrapped in whitespaces" in {
      // given
      val dataSource = "  DataBase\t"

      // when
      val result = parseDataSource(dataSource)

      // then
      result shouldEqual valid[ParsingError, DataSource](expectedDataSource)
    }

    "reject invalid data source" in {
      // given
      val dataSource = "invalid"

      // when
      val result = parseDataSource(dataSource)

      // then
      result shouldEqual invalidNel[ParsingError, DataSource](InvalidDataSource(dataSource))
    }
  }

  "config validation" should {

    import CatsValidation.parseConfig

    "accept all valid params" in {
      // given
      val number = "12"
      val dataSource = "database"

      // when
      val result = parseConfig(number, dataSource)

      // then
      result shouldEqual valid[ParsingError, Config](expectedConfig)
    }

    "reject invalid number" in {
      // given
      val number = "-12"
      val dataSource = "DataBase"

      // when
      val result = parseConfig(number, dataSource)

      // then
      result shouldEqual invalidNel[ParsingError, Config](NotNatural(number))
    }

    "reject invalid datasource" in {
      // given
      val number = "12"
      val dataSource = "invalid"

      // when
      val result = parseConfig(number, dataSource)

      // then
      result shouldEqual invalidNel[ParsingError, Config](InvalidDataSource(dataSource))
    }
  }
}
