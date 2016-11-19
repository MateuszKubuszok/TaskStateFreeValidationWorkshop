package io.scalawave.workshop.state

import io.scalawave.workshop.common.Currency
import Currency.Currency

/**
 * Demo exchange rate provider
 *
 * This class is used by the demo to provide some basic conversion features. It is demo only though - in proper program
 * we would use an **actual** data source.
 */
case class CurrencyExchangeRates(dollarExchangeRates: Map[Currency, Double] = Map.empty) {

  def getRate(from: Currency, to: Currency): Option[Double] = for {
    dollarToX <- dollarExchangeRates.get(from)
    xToDollar = 1.0 / dollarToX
    dollarToY <- dollarExchangeRates.get(to)
  } yield xToDollar * dollarToY
}
