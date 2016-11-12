package io.scalawave.workshop.common

object Currency extends Enumeration {
  type Currency = Value
  val USD = Value("usd")
  val EUR = Value("eur")
  val GBP = Value("gbp")
  val CAD = Value("cad")
  val AUD = Value("aud")
}
