package io.scalawave.workshop.state

import cats.data.State
import io.scalawave.workshop.common.Currency
import Currency.Currency

/** Direct state manipulation - Cats
  *
  * Here we only want to show some of the most generic State functions - ones will allows to extract some data from
  * passed state, perform calculations and return potentially modified state as well as possible result of those
  * calculations.
  */
object CatsState {

  /** Preserves state and returns calculated exchange rate.
    *
    * @param from conversion from
    * @param to conversion to
    * @return state -> calculated exchange rate
    */
  def getExchangeRate(from: Currency, to: Currency): State[CurrencyExchangeRates, Option[Double]] =
    State[CurrencyExchangeRates, Option[Double]] { state =>
      state -> state.getRate(from, to)
    }

  /** Preserves state and returns USD exchange rate.
    *
    * @param to conversion to
    * @return state -> exchange rate
    */
  def getDollarExchangeRate(to: Currency): State[CurrencyExchangeRates, Option[Double]] =
    State[CurrencyExchangeRates, Option[Double]] { state =>
      state -> state.dollarExchangeRates.get(to)
    }

  /** Updates state with new USD exchange rate and returns old rate.
    *
    * @param to conversion to
    * @param rate exchange rate
    * @return new state -> old exchange rate
    */
  def setDollarExchangeRate(to: Currency, rate: Double): State[CurrencyExchangeRates, Option[Double]] =
    State[CurrencyExchangeRates, Option[Double]] { oldState =>
      val oldValue = oldState.dollarExchangeRates.get(to)
      val newState = oldState.copy(oldState.dollarExchangeRates + (to -> rate))
      newState -> oldValue
    }
}
