package io.scalawave.workshop.state

import io.scalawave.workshop.common.Currency
import Currency.Currency

import scala.util.Try

/** State monad demo
  *
  * You can use this main function for checking how state function work in action.
  *
  * Here you can see, how State objects can be combined (chained) together using for-comprehension.
  *
  * Feel free to play around with those, as understanding of State will become useful when we learn next powerful
  * abstraction - Free.
  */
object Main {

  def main(args: Array[String]): Unit = {
    println("Initialize state with scalaz")
    val firstScalaz = scalazTest.exec(CurrencyExchangeRates())
    println(s"Initial state resolved to: $firstScalaz")
    println()
    println("Modify state with scalaz")
    val secondScalaz = scalazTest.exec(firstScalaz)
    println(s"Modified state: $secondScalaz")
    println()
    println("Initialize state with cats")
    val firstCats = catsTest.runS(CurrencyExchangeRates()).value
    println(s"Initial state resolved to: $firstCats")
    println()
    println("Modify state with cats")
    val secondCats = catsTest.runS(firstCats).value
    println(s"Modified state: $secondCats")
  }

  def scalazTest: scalaz.State[CurrencyExchangeRates, Unit] = for {
    _ <- scalazSingleTest(Currency.EUR)
    _ <- scalazSingleTest(Currency.AUD)
    _ <- scalazSingleTest(Currency.CAD)
    eurToAud <- ScalazState.getExchangeRate(Currency.EUR, Currency.AUD)
    _ = println(s"EUR to AUD is: $eurToAud")
    audToCad <- ScalazState.getExchangeRate(Currency.AUD, Currency.CAD)
    _ = println(s"AUD to CAD is: $audToCad")
  } yield ()

  def scalazSingleTest(currency: Currency): scalaz.State[CurrencyExchangeRates, Unit] = {
    println(s"Define exchange for $currency")
    val rate = Try(scala.io.StdIn.readLine.toDouble).getOrElse(1.0)
    for {
      oldValue <- ScalazState.setDollarExchangeRate(currency, rate)
      _ = println(s"Exchange rate changed from $oldValue to $rate")
    } yield ()
  }

  def catsTest: cats.data.State[CurrencyExchangeRates, Unit] = for {
    _ <- catsSingleTest(Currency.EUR)
    _ <- catsSingleTest(Currency.AUD)
    _ <- catsSingleTest(Currency.CAD)
    eurToAud <- CatsState.getExchangeRate(Currency.EUR, Currency.AUD)
    _ = println(s"EUR to AUD is: $eurToAud")
    audToCad <- CatsState.getExchangeRate(Currency.AUD, Currency.CAD)
    _ = println(s"AUD to CAD is: $audToCad")
  } yield ()

  def catsSingleTest(currency: Currency): cats.data.State[CurrencyExchangeRates, Unit] = {
    println(s"Define exchange for $currency")
    val rate = Try(scala.io.StdIn.readLine.toDouble).getOrElse(1.0)
    for {
      oldValue <- CatsState.setDollarExchangeRate(currency, rate)
      _ = println(s"Exchange rate changed from $oldValue to $rate")
    } yield ()
  }
}
