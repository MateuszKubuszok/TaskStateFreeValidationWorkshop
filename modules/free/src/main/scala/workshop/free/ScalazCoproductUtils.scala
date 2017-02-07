package workshop.free

import scalaz._

/**
 * This class is something we had to implement manually, because so far Scalaz doesn't provide equivalent of Cats':
 *
 * interpreter1 or interpreter 2
 *
 * Such a shame considering how useful piece of code it is.
 */
object ScalazCoproductUtils {
  sealed abstract class :+:[F[_], G[_]] {
    type λ[A] = Coproduct[F, G, A]
  }

  implicit class ComposeInterpreters[F[_], H[_]](f: F ~> H) {
    def :+:[G[_]](g: G ~> H): (G :+: F)#λ ~> H = new ((G :+: F)#λ ~> H) {
      def apply[A](fa: (G :+: F)#λ[A]) = fa.run.fold(g, f)
    }
  }
}
