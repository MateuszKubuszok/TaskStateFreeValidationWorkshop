package io.scalawave.workshop.task

import io.scalawave.workshop.common.Currency._
import io.scalawave.workshop.common.CurrencyOnlineService
import io.scalawave.workshop.common.CurrencyDataBase.DBError

import scalaz.\/
import scalaz.concurrent.Task

object CurrencyOnlineServiceHandler extends (Currency => Double) {

  override def apply(currency: Currency): Double = fetchDataSync(currency)

  def fetchDataAsync(currency: Currency)(callback: Throwable \/ Double => Unit): Unit =
    fetchDataTask(currency).unsafePerformAsync(callback)

  def fetchDataSync(currency: Currency): Double = fetchDataTask(currency).unsafePerformSync

  def fetchDataTask(currency: Currency): Task[Double] = {
    val task = Task(CurrencyOnlineService.query(currency))
    task.handleWith {
      case DBError => fetchDataTask(currency)
    }
  }
}
