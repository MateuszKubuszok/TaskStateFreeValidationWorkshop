package io.scalawave.workshop

package object common {

  type ScalazConfigState[A] = scalaz.State[Config, A]
  type CatsConfigState[A] = cats.data.State[Config, A]
}
