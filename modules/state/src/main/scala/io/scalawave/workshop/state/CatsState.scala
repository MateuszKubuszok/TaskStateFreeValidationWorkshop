package io.scalawave.workshop.state

import cats.data.State
import io.scalawave.workshop.state.Currency.Currency

object CatsState {

  def getExchangeRate(from: Currency, to: Currency): State[CurrencyExchangeRates, Option[Double]] =
    State[CurrencyExchangeRates, Option[Double]] { state =>
      state -> state.getRate(from, to)
    }

  def getDollarExchangeRate(to: Currency): State[CurrencyExchangeRates, Option[Double]] =
    State[CurrencyExchangeRates, Option[Double]] { state =>
      state -> state.dollarExchangeRates.get(to)
    }

  def setDollarExchangeRate(to: Currency, rate: Double): State[CurrencyExchangeRates, Option[Double]] =
    State[CurrencyExchangeRates, Option[Double]] { oldState =>
      val oldValue = oldState.dollarExchangeRates.get(to)
      val newState = oldState.copy(oldState.dollarExchangeRates + (to -> rate))
      newState -> oldValue
    }
}
