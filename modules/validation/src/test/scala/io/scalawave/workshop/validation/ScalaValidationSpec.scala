package io.scalawave.workshop.validation

import io.scalawave.workshop.validation.DataSource.DataSource
import org.specs2.mutable.Specification

import scalaz._
import Scalaz._

class ScalaValidationSpec extends Specification {

  val expectedAccuracy = 12
  val expectedDataSource = DataSource.DataBase
  val expectedConfig = Config(expectedAccuracy, expectedDataSource)

  "accuracy validation" should {

    import ScalazValidation.parseAccuracy

    "accept positive number" in {
      // given
      val accuracy = "12"

      // when
      val result = parseAccuracy(accuracy)

      // then
      result shouldEqual expectedAccuracy.success[ConfigError]
    }

    "accept 0" in {
      // given
      val accuracy = "0"

      // when
      val result = parseAccuracy(accuracy)

      // then
      result shouldEqual 0.success[ConfigError]
    }

    "reject negative number" in {
      // given
      val accuracy = "-12"

      // when
      val result = parseAccuracy(accuracy)

      // then
      result shouldEqual NotNatural(accuracy).failureNel[Int]
    }

    "reject invalid number" in {
      // given
      val accuracy = "-12--"

      // when
      val result = parseAccuracy(accuracy)

      // then
      result shouldEqual NotANumber(accuracy).failureNel[Int]
    }
  }

  "data source validation" should {

    import ScalazValidation.parseDataSource

    "accept valid datasource" in {
      // given
      val dataSource = "database"

      // when
      val result = parseDataSource(dataSource)

      // then
      result shouldEqual expectedDataSource.success[ConfigError]
    }

    "accept valid datasource with different cases" in {
      // given
      val dataSource = "DataBase"

      // when
      val result = parseDataSource(dataSource)

      // then
      result shouldEqual expectedDataSource.success[ConfigError]
    }

    "reject invalid datasource" in {
      // given
      val dataSource = "invalid"

      // when
      val result = parseDataSource(dataSource)

      // then
      result shouldEqual InvalidDataSource(dataSource).failureNel[DataSource]
    }
  }

  "config validation" should {

    import ScalazValidation.parse

    "accept all valid params" in {
      // given
      val accuracy = "12"
      val dataSource = "database"

      // when
      val result = parse(accuracy, dataSource)

      // then
      result shouldEqual expectedConfig.success[ConfigError]
    }

    "reject invalid accuracy" in {
      // given
      val accuracy = "-12"
      val dataSource = "DataBase"

      // when
      val result = parse(accuracy, dataSource)

      // then
      result shouldEqual NotNatural(accuracy).failureNel[Config]
    }

    "reject invalid datasource" in {
      // given
      val accuracy = "12"
      val dataSource = "invalid"

      // when
      val result = parse(accuracy, dataSource)

      // then
      result shouldEqual InvalidDataSource(dataSource).failureNel[Config]
    }
  }
}
