package io.scalawave.workshop.common

import io.scalawave.workshop.common.Currency._

import scala.util.Random

object CurrencyDataBase {

  case object DBError extends Exception

  private val values = Map(
    EUR -> 0.93,
    GBP -> 0.80,
    CAD -> 1.34,
    AUD -> 1.35
  )

  private val dbDelay = 150

  private val errorRatio = 0.20

  def query(currency: Currency): Double = {
    synchronized(wait(dbDelay))
    if (Random.nextDouble < errorRatio) throw DBError
    else values(currency)
  }
}
