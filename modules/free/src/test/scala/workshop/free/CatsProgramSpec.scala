package workshop.free

import cats.data.Xor
import cats.{ Id, ~> }
import workshop.common.{ Currency, ActionType }
import workshop.free.CatsProgram.Program
import org.specs2.mutable.Specification

import scala.collection.mutable

class CatsProgramSpec extends Specification {

  /*
  object Operations extends Enumeration {
    val ConfigChanged, Quit = Value
  }

  class TestInterpreter(
      input:      mutable.Queue[String],
      output:     mutable.MutableList[String],
      operations: mutable.MutableList[Operations.Value]
  ) extends (CatsProgram.Program ~> Id) {

    import CatsCommand._
    import CatsCalculation._

    override def apply[A](fa: Program[A]): Id[A] = fa.run match {
      case Xor.Left(GetNextAction(question)) =>
        output += question
        ActionType.withName(input.dequeue)
      case Xor.Left(Configure(question)) =>
        output += question
        operations += Operations.ConfigChanged
        ()
      case Xor.Left(Quit) =>
        operations += Operations.Quit
        ()
      case Xor.Right(GetCurrency(question)) =>
        output += question
        Currency.withName(input.dequeue)
      case Xor.Right(GetAmount(question)) =>
        output += question
        input.dequeue.toDouble
      case Xor.Right(Convert(from, to, amount)) =>
        output += s"$from -> $to"
        amount * 2
      case Xor.Right(DisplayValue(value: Double)) =>
        output += value.toString
        ()
    }
  }

  "Cats program" should {

    val program = CatsProgram.program

    "perform initial config then quit" in {
      // given
      val input = mutable.MutableList("quit").toQueue
      val output = mutable.MutableList[String]()
      val operations = mutable.MutableList[Operations.Value]()
      val runner = new TestInterpreter(input, output, operations)

      // when
      program.foldMap(runner)

      // then
      input.toList should_== List()
      output.toList should_== List(
        "Set initial configuration (amount, data source)", // initialize
        "What do you want to do next" // quit
      )
      operations.toList should_== List(Operations.ConfigChanged, Operations.Quit)
    }

    "perform configuration, convert, reconfigure, convert then quit" in {
      // given
      val input = mutable.MutableList(
        "convert",
        "eur", "gbp", "123.45",
        "settings",
        "convert",
        "aud", "cad", "67.89",
        "quit"
      ).toQueue
      val output = mutable.MutableList[String]()
      val operations = mutable.MutableList[Operations.Value]()
      val runner = new TestInterpreter(input, output, operations)

      // when
      program.foldMap(runner)

      // then
      input.toList should_== List()
      output.toList should_== List(
        "Set initial configuration (amount, data source)", // initialize
        "What do you want to do next", // convert
        "From what currency you want to convert", // eur
        "Into what currency you want to convert", // gbp
        "How much do you want to convert", // 123.45
        "eur -> gbp",
        "246.9",
        "What do you want to do next", // settings
        "Change configuration (amount, data source)",
        "What do you want to do next", // convert
        "From what currency you want to convert", // aud
        "Into what currency you want to convert", // cad
        "How much do you want to convert", // 67.89
        "aud -> cad",
        "135.78",
        "What do you want to do next" // quit
      )
      operations.toList should_== List(Operations.ConfigChanged, Operations.ConfigChanged, Operations.Quit)
    }
  }
  */
}
