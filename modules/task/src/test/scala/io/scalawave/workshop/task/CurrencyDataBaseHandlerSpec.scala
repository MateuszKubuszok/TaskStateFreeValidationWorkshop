package io.scalawave.workshop.task

import io.scalawave.workshop.common.Currency
import org.specs2.mutable.Specification

import scala.concurrent.{ Await, Promise }
import scala.concurrent.duration._
import scalaz.{ \/-, \/ }

class CurrencyDataBaseHandlerSpec extends Specification {

  "CurrencyDataBaseHandler" should {

    "be able to run unsafe sync call" in {
      // given
      val currency = Currency.EUR

      // when
      val result = CurrencyDataBaseHandler.fetchDataSync(currency)

      // then
      result should_== 0.93
    }

    "be able to run unsafe async call" in {
      // given
      val currency = Currency.EUR
      val resultP = Promise[Throwable \/ Double]()

      // when
      CurrencyDataBaseHandler.fetchDataAsync(currency) { result => resultP.success(result) }

      // then
      Await.result(resultP.future, 10 seconds) should_== \/-(0.93)
    }

    "be able to return plain task" in {
      // given
      val currency = Currency.EUR

      // when
      val result = CurrencyDataBaseHandler.fetchDataTask(currency).unsafePerformSync

      // then
      result should_== 0.93
    }
  }
}
