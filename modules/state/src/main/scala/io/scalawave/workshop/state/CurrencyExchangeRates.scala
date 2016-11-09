package io.scalawave.workshop.state

import io.scalawave.workshop.state.Currency.Currency

object Currency extends Enumeration {
  type Currency = Value
  val USD = Value("usd")
  val EUR = Value("eur")
  val GBP = Value("gbp")
  val CAD = Value("cad")
  val AUD = Value("aud")
}

case class CurrencyExchangeRates(dollarExchangeRates: Map[Currency, Double] = Map.empty) {

  def getRate(from: Currency, to: Currency): Option[Double] = for {
    dollarToX <- dollarExchangeRates.get(from)
    xToDollar = 1.0 / dollarToX
    dollarToY <- dollarExchangeRates.get(to)
  } yield xToDollar * dollarToY
}
