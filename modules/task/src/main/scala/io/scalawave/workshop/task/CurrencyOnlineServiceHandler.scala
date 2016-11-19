package io.scalawave.workshop.task

import io.scalawave.workshop.common.Currency._
import io.scalawave.workshop.common.CurrencyOnlineService
import io.scalawave.workshop.common.CurrencyOnlineService.ConnectionError

import scalaz.\/
import scalaz.concurrent.Task

object CurrencyOnlineServiceHandler extends (Currency => Double) {

  override def apply(currency: Currency): Double = fetchDataSync(currency)

  def fetchDataAsync(currency: Currency)(callback: Throwable \/ Double => Unit): Unit =
    fetchDataTask(currency).unsafePerformAsync(callback)

  def fetchDataSync(currency: Currency): Double = fetchDataTask(currency).unsafePerformSync

  def fetchDataTask(currency: Currency): Task[Double] = {
    val task = Task(wrapperForDefaultValue(currency))
    task.handleWith {
      case ConnectionError => fetchDataTask(currency)
    }
  }

  private val wrapperForDefaultValue: Currency => Double = {
    case USD                => 1.0
    case currency: Currency => CurrencyOnlineService.query(currency)
  }
}
