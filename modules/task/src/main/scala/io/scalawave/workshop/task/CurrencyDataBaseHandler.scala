package io.scalawave.workshop.task

import io.scalawave.workshop.common.Currency.Currency
import io.scalawave.workshop.common.CurrencyDataBase
import io.scalawave.workshop.common.CurrencyDataBase.DBError

import scalaz.\/
import scalaz.concurrent.Task

object CurrencyDataBaseHandler extends (Currency => Double) {

  override def apply(currency: Currency): Double = fetchDataSync(currency)

  def fetchDataAsync(currency: Currency)(callback: Throwable \/ Double => Unit): Unit =
    fetchDataTask(currency).unsafePerformAsync(callback)

  def fetchDataSync(currency: Currency): Double = fetchDataTask(currency).unsafePerformSync

  def fetchDataTask(currency: Currency): Task[Double] = {
    val task = Task(CurrencyDataBase.query(currency))
    task.handleWith {
      case DBError => fetchDataTask(currency)
    }
  }
}
