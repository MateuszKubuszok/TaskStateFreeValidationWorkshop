package io.scalawave.workshop.state

import io.scalawave.workshop.common.Currency
import Currency.Currency

import scalaz._

/**
 * Direct state manipulation - Scalaz
 *
 * Here we only want to show some of the most generic State functions - ones will allows to extract some data from
 * passed state, perform calculations and return potentially modified state as well as possible result of those
 * calculations.
 */
object ScalazState {

  /**
   * Preserves state and returns calculated exchange rate.
   *
   * Hint: we don't need to modify state here, but we need to extract some value from it.
   *
   * @param from conversion from
   * @param to conversion to
   * @return state -> calculated exchange rate
   */
  def getExchangeRate(from: Currency, to: Currency): State[CurrencyExchangeRates, Option[Double]] =
    State.gets[CurrencyExchangeRates, Option[Double]] { state => state.getRate(from, to) }

  /**
   * Preserves state and returns USD exchange rate.
   *
   * Hint: we don't need to modify state here, but we need to extract some value from it.
   *
   * @param to conversion to
   * @return state -> exchange rate
   */
  def getDollarExchangeRate(to: Currency): State[CurrencyExchangeRates, Option[Double]] =
    State.gets[CurrencyExchangeRates, Option[Double]] { state => state.dollarExchangeRates.get(to) }

  /**
   * Updates state with new USD exchange rate and returns old rate.
   *
   * Hint: we want to both update state and retrive some value - there are several good ways to do it ;)
   *
   * @param to conversion to
   * @param rate exchange rate
   * @return new state -> old exchange rate
   */
  def setDollarExchangeRate(to: Currency, rate: Double): State[CurrencyExchangeRates, Option[Double]] = for {
    oldValue <- getDollarExchangeRate(to)
    oldState <- State.get[CurrencyExchangeRates]
    _ <- State.put[CurrencyExchangeRates] {oldState.copy(oldState.dollarExchangeRates + (to -> rate)) }
  } yield oldValue
}
