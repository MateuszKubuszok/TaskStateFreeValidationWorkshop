package workshop.state

import workshop.common.Currency
import org.specs2.mutable.Specification

class CatsStateSpec extends Specification {

  val dollarExchangeRate = Map(
    Currency.USD -> 1.0,
    Currency.EUR -> 2.0,
    Currency.GBP -> 3.0,
    Currency.AUD -> 4.0,
    Currency.CAD -> 5.0
  )
  val initialState = CurrencyExchangeRates(dollarExchangeRate)

  "getDollarExchangeRate" should {

    "return exchange rate" in {
      // given
      // when
      val result = CatsState.getDollarExchangeRate(Currency.EUR).runA(initialState).value

      // then
      result mustEqual dollarExchangeRate.get(Currency.EUR)
    }
  }

  "setDollarExchangeRate" should {

    "set exchange rate" in {
      // given
      val newValue = 7.0

      // when
      val (newState, oldValue) = CatsState.setDollarExchangeRate(Currency.AUD, newValue).run(initialState).value

      // then
      newState mustEqual initialState.copy(dollarExchangeRate + (Currency.AUD -> newValue))
      oldValue mustEqual dollarExchangeRate.get(Currency.AUD)
    }
  }

  "getExchangeRate" should {

    "return current exchange rate" in {
      // given
      val expected = for {
        x <- dollarExchangeRate.get(Currency.CAD)
        xPrim = 1.0 / x
        y <- dollarExchangeRate.get(Currency.GBP)
      } yield y * xPrim

      // when
      val result = CatsState.getExchangeRate(Currency.CAD, Currency.GBP).runA(initialState).value

      // then
      result mustEqual expected
    }
  }
}
