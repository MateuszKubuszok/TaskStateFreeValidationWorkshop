package io.scalawave.workshop.common

object ActionType extends Enumeration {
  type ActionType = Value
  val Convert = Value("convert")
  val ChangeSettings = Value("settings")
  val Quit = Value("quit")
}
