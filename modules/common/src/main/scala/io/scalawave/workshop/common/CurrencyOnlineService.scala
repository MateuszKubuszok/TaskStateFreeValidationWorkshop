package io.scalawave.workshop.common

import io.scalawave.workshop.common.Currency._

import scala.util.Random

/** Simulates online service with currency rates.
  *
  * As you can see it is unreliable service as it sometimes has trouble with network connection.
  *
  * What is more, it doesn't handle cases when you decided to ask for USD to USD ratio.
  *
  * It returns USD to X conversion ratio.
  */
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
