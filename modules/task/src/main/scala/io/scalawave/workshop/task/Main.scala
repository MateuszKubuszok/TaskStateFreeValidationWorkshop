package io.scalawave.workshop.task

import io.scalawave.workshop.common.Currency

import scalaz.{ \/-, -\/ }

object Main {

  def main(args: Array[String]): Unit = {
    Currency.values foreach { value =>
      CurrencyOnlineServiceHandler.fetchDataTask(value).unsafePerformAsync {
        case -\/(error) =>
          System.err.println(s"Unexpected error: $error")
          error.printStackTrace()
        case \/-(result) => println(s"[Internet] USD to ${value.toString.toUpperCase} rate: $result")
      }
    }
    Currency.values foreach { value =>
      CurrencyDataBaseHandler.fetchDataTask(value).unsafePerformSyncAttempt match {
        case -\/(error) =>
          System.err.println(s"Unexpected error: $error")
          error.printStackTrace()
        case \/-(result) => println(s"[DataBase] USD to ${value.toString.toUpperCase} rate: $result")
      }
    }
    synchronized(wait(10000))
  }
}
