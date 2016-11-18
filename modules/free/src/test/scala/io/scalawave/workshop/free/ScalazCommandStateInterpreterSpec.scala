package io.scalawave.workshop.free

import io.scalawave.workshop.common._
import io.scalawave.workshop.free.ScalazCommand.Ops
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

import scala.collection.mutable

class ScalazCommandStateInterpreterSpec extends Specification with ScalazInterpreterTestParams {

  "ScalazCommandIdInterpreter" should {

    "ask for next action till it's correct" in new Fixture {
      // given
      input ++= Seq("wrong", "invalid", "quit")
      val program = for {
        a1 <- ops.getNextAction("test")
      } yield a1

      // when
      val result = program.foldMap(interpreter).run(initialConfig)._2

      // then
      input.size should_== 0
      output.toList should_== List(
        "test",
        "Errors:",
        s" - ${InvalidActionType("wrong")}",
        "Try again\n",
        "test",
        "Errors:",
        s" - ${InvalidActionType("invalid")}",
        "Try again\n",
        "test"
      )
      result should_== ActionType.Quit
    }

    "ask for new config till it's correct" in new Fixture {
      // given
      input ++= Seq("wrong", "invalid", "5", "internet")
      val program = for {
        a1 <- ops.configure("test")
      } yield a1

      // when
      val (config, _) = program.foldMap(interpreter).run(initialConfig)

      // then
      input.size should_== 0
      output.toList should_== List(
        "test",
        "Errors:",
        s" - ${NotNatural("wrong")}",
        s" - ${InvalidDataSource("invalid")}",
        "Try again\n",
        "test"
      )
      config should_== Config(5, DataSource.Internet)
    }

    "print exit message" in new Fixture {
      // given
      val program = for {
        _ <- ops.quit
      } yield ()

      // when
      program.foldMap(interpreter).run(initialConfig)

      // then
      output.toList should_== List("Exiting program")
    }
  }

  trait Fixture extends Scope {
    val ops = new Ops[ScalazCommand]
    val input = mutable.Queue[String]()
    val output = mutable.MutableList[String]()
    val initialConfig = Config(2, DataSource.DataBase)
    val interpreter = new ScalazCommandStateInterpreter(
      readLineSimulation(input),
      writeLineSimulation(output),
      simpleActionTypeValidation,
      simpleConfigValidation
    )
  }
}
