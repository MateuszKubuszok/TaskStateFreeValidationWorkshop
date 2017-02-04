package workshop

package object common {

  // helper definitions which allows one to use Free -> State interpreter easier.

  type ScalazConfigState[A] = scalaz.State[Config, A]
  type CatsConfigState[A] = cats.data.State[Config, A]
}
