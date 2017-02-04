package workshop.task

import workshop.common.Currency
import org.specs2.mutable.Specification

import scala.concurrent.{ Await, Promise }
import scala.concurrent.duration._
import scalaz.{\/, \/-}

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
      val resultP = Promise[Throwable \/ Double]()

      // when
      CurrencyOnlineServiceHandler.fetchDataAsync(currency) { result => resultP.success(result) }

      // then
      Await.result(resultP.future, 10 seconds) should_== \/-(0.95)
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
