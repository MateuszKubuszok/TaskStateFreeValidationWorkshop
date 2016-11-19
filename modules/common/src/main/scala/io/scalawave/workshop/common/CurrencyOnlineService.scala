package io.scalawave.workshop.common

import io.scalawave.workshop.common.Currency._

import scala.util.Random

object CurrencyOnlineService {

  case object ConnectionError extends Exception

  private val values = Map(
    EUR -> 0.95,
    GBP -> 0.82,
    CAD -> 1.36,
    AUD -> 1.37
  )

  private val connectionDelay = 750

  private val errorRatio = 0.50

  def query(currency: Currency): Double = {
    synchronized(wait(connectionDelay))
    if (Random.nextDouble < errorRatio) throw ConnectionError
    else values(currency)
  }
}
