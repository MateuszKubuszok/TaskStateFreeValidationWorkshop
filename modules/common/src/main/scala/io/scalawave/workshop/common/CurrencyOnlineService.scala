package io.scalawave.workshop.common

import io.scalawave.workshop.common.Currency._

object CurrencyOnlineService {

  private val values = Map(
    EUR -> 0.95,
    GBP -> 0.82,
    CAD -> 1.36,
    AUD -> 1.37
  )

  private val connectionDelay = 1000

  def query(currency: Currency): Double = {
    synchronized(wait(connectionDelay))
    // add random failures
    values(currency)
  }
}
