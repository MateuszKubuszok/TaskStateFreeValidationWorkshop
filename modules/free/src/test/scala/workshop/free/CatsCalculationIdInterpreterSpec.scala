package workshop.free

import workshop.common._
import workshop.free.CatsCalculation.Ops
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

import scala.collection.mutable

class CatsCalculationIdInterpreterSpec extends Specification with CatsInterpreterTestParams {

  "CatsCalculationIdInterpreter" should {

    "ask for currency till it's correct" in new Fixture {
      // given
      input ++= Seq("wrong", "invalid", "gbp")
      val program = for {
        c1 <- ops.getCurrency("test")
      } yield c1

      // when
      val result = program.foldMap(interpreter)

      // then
      input.size should_== 0
      output.toList should_== List(
        "test",
        "Errors:",
        s" - ${InvalidCurrency("wrong")}",
        "Try again\n",
        "test",
        "Errors:",
        s" - ${InvalidCurrency("invalid")}",
        "Try again\n",
        "test"
      )
      result should_== Currency.GBP
    }

    "ask for currency twice" in new Fixture {
      // given
      input ++= Seq("eur", "gbp")
      val program = for {
        c1 <- ops.getCurrency("test1")
        c2 <- ops.getCurrency("test2")
      } yield (c1, c2)

      // when
      val result = program.foldMap(interpreter)

      // then
      input.size should_== 0
      output.toList should_== List("test1", "test2")
      result should_== ((Currency.EUR, Currency.GBP))
    }

    "ask for amount till it's correct" in new Fixture {
      // given
      input ++= Seq("wrong", "invalid", "123")
      val program = for {
        c1 <- ops.getAmount("test")
      } yield c1

      // when
      val result = program.foldMap(interpreter)

      // then
      input.size should_== 0
      output.toList should_== List(
        "test",
        "Errors:",
        s" - ${NotANumber("wrong")}",
        "Try again\n",
        "test",
        "Errors:",
        s" - ${NotANumber("invalid")}",
        "Try again\n",
        "test"
      )
      result should_== 123.0
    }

    "convert values" in new Fixture {
      // given
      val program = for {
        value <- ops.convert(Currency.EUR, Currency.CAD, 123.0)
      } yield value

      // when
      val result = program.foldMap(interpreter)

      // then
      result should_== 123.0
    }

    "display value" in new Fixture {
      // given
      val program = for {
        _ <- ops.displayValue(456.0)
      } yield ()

      // when
      program.foldMap(interpreter)

      // then
      output.toList should_== List("Result: 456.00")
    }
  }

  trait Fixture extends Scope {
    val ops = new Ops
    val input = mutable.Queue[String]()
    val output = mutable.MutableList[String]()
    val configStore = ConfigStore(Config(2, DataSource.DataBase))
    val interpreter = new CatsCalculationIdInterpreter(
      readLineSimulation(input),
      writeLineSimulation(output),
      simpleCurrencyValidation,
      simpleDoubleValidation,
      constantConversionRates,
      configStore
    )
  }
}
