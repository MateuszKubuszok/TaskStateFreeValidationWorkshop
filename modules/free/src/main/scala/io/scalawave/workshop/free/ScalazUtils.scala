package io.scalawave.workshop.free;

import scalaz._

object ScalazUtils {
  sealed abstract class :+:[F[_], G[_]] {
    type λ[A] = Coproduct[F, G, A]
  }

  implicit class ComposeInterpreters[F[_], H[_]](f: F ~> H) {
    def :+:[G[_]](g: G ~> H): (G :+: F)#λ ~> H = new ((G :+: F)#λ ~> H) {
      def apply[A](fa: (G :+: F)#λ[A]) = fa.run.fold(g, f)
    }
  }
}
