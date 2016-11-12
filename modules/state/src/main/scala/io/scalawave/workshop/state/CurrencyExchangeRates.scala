package io.scalawave.workshop.state

import io.scalawave.workshop.common.Currency
import Currency.Currency

case class CurrencyExchangeRates(dollarExchangeRates: Map[Currency, Double] = Map.empty) {

  def getRate(from: Currency, to: Currency): Option[Double] = for {
    dollarToX <- dollarExchangeRates.get(from)
    xToDollar = 1.0 / dollarToX
    dollarToY <- dollarExchangeRates.get(to)
  } yield xToDollar * dollarToY
}
