package io.scalawave.workshop.common

import io.scalawave.workshop.common.Currency._

object CurrencyDataBase {

  private val values = Map(
    EUR -> 0.93,
    GBP -> 0.80,
    CAD -> 1.34,
    AUD -> 1.35
  )

  private val dbDelay = 150

  def query(currency: Currency): Double = {
    synchronized(wait(dbDelay))
    values(currency)
  }
}
