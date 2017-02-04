package workshop.task

import workshop.common.Currency._
import workshop.common.CurrencyOnlineService
import workshop.common.CurrencyOnlineService.ConnectionError

import scalaz.\/
import scalaz.concurrent.Task

/** Task exercise
 *
 * Goal of this exercise is implementation of few utility functions for safely fetching data.
 *
 * When we look into [[CurrencyOnlineService.query]] we'll see that it is unreliable and tends to throw exceptions.
 *
 * We will try to handle that exception and try to query service until it succeeds.
 */
object CurrencyOnlineServiceHandler extends (Currency => Double) {

  override def apply(currency: Currency): Double = fetchDataSync(currency)

  /** Fetches data and runs callback when computation finishes.
   *
   * Hint: we basically want to asynchronously perform unsafe (with possible side effects) calculation on a task.
   *
   * @param currency which USD exchange ratio we would like to obtain
   * @param callback function to call on completion
   */
  def fetchDataAsync(currency: Currency)(callback: Throwable \/ Double => Unit): Unit = ???

  /** Fetches data and waits for the result.
   *
   * While we can call [[wrapperForDefaultValue]] it doesn't solve our problem as it sometimes throws, and we would like
   * to retry utill it succeeds.
   *
   * Hint: we want to wait synchronously perform
   *
   * @param currency which USD exchange ratio we would like to obtain
   * @return USD to currency exchange ratio
   */
  def fetchDataSync(currency: Currency): Double = ???

  /** Creates Task which will attempt to fetch data until it succeeds.
   *
   * @param currency which USD exchange ratio we would like to obtain
   * @return Task fetching USD to currency exchange ratio
   */
  def fetchDataTask(currency: Currency): Task[Double] = ???

  private val wrapperForDefaultValue: Currency => Double = {
    case USD                => 1.0
    case currency: Currency => CurrencyOnlineService.query(currency)
  }
}
