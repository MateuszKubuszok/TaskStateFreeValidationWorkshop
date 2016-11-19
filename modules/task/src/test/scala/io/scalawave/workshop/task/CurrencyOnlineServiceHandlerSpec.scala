package io.scalawave.workshop.task

import io.scalawave.workshop.common.Currency
import org.specs2.mutable.Specification

import scala.collection.mutable
import scalaz.{ \/-, \/ }

class CurrencyOnlineServiceHandlerSpec extends Specification {

  "CurrencyOnlineServiceHandler" should {

    "be able to run unsafe sync call" in {
      // given
      val currency = Currency.EUR

      // when
      val result = CurrencyOnlineServiceHandler.fetchDataSync(currency)

      // then
      result should_== 0.95
    }

    "be able to run unsafe async call" in {
      // given
      val currency = Currency.EUR
      val result = mutable.Set[Throwable \/ Double]()
      val testTimeout = 2000

      // when
      CurrencyOnlineServiceHandler.fetchDataAsync(currency) { result += _ }

      synchronized(wait(testTimeout))

      // then
      result.toSet should_== Set(\/-(0.95))
    }

    "be able to return plain task" in {
      // given
      val currency = Currency.EUR

      // when
      val result = CurrencyOnlineServiceHandler.fetchDataTask(currency).unsafePerformSync

      // then
      result should_== 0.95
    }
  }
}
