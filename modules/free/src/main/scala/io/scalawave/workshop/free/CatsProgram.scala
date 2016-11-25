package io.scalawave.workshop.free

import cats.data.Coproduct
import cats.free.Free
import io.scalawave.workshop.common.ActionType

/**
 * Here I took the liberty of implementing the whole program using our DSL.
 *
 * Interesting how similar are Cats and Scalaz versions :)
 *
 * If not for imports (and DSL classes' names) one might have trouble finding any difference.
 */
object CatsProgram {

  type Program[A] = Coproduct[CatsCommand, CatsCalculation, A]
  /*
  val commands = new CatsCommand.Ops[Program]
  val calculations = new CatsCalculation.Ops[Program]

  val convert = for {
    from <- calculations.getCurrency("From what currency you want to convert")
    to <- calculations.getCurrency("Into what currency you want to convert")
    amount <- calculations.getAmount("How much do you want to convert")
    result <- calculations.convert(from, to, amount)
    _ <- calculations.displayValue(result)
  } yield ()

  def mainLoop: Free[Program, Unit] =
    commands.getNextAction("What do you want to do next") flatMap {
      case ActionType.Convert =>
        convert flatMap { _ => mainLoop }
      case ActionType.ChangeSettings =>
        commands.configure("Change configuration (amount, data source)") flatMap { _ => mainLoop }
      case ActionType.Quit =>
        commands.quit
    }

  val program = for {
    _ <- commands.configure("Set initial configuration (amount, data source)")
    _ <- mainLoop
  } yield ()
  */
}
