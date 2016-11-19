package io.scalawave.workshop.task

import java.util.concurrent.Executors

import io.scalawave.workshop.common.Currency

import scalaz.concurrent.Task
import scalaz.{ \/-, -\/ }

/**
  * Task demo
  *
  * This program's purpose is to show the difference between sync and async run, as well as some of the consequences
  * of using default ExecutionService vs using a custom one.
  */
object Main {

  def main(args: Array[String]): Unit = {
    println("Starting querying online sources asynchronously")
    Currency.values foreach { value =>
      CurrencyOnlineServiceHandler.fetchDataTask(value).unsafePerformAsync {
        case -\/(error)  => System.err.println(s"Unexpected error: $error")
        case \/-(result) => println(s"[Internet async] USD to ${value.toString.toUpperCase} rate: $result")
      }
    }

    println("Starting querying database synchronously")
    Currency.values foreach { value =>
      CurrencyDataBaseHandler.fetchDataTask(value).unsafePerformSyncAttempt match {
        case -\/(error)  => System.err.println(s"Unexpected error: $error")
        case \/-(result) => println(s"[DataBase sync ] USD to ${value.toString.toUpperCase} rate: $result")
      }
    }

    println("Starting querying database asynchronously with custom ExecutionService")
    Currency.values foreach { value =>
      val customExecutionService = Executors.newSingleThreadExecutor()
      Task.fork(CurrencyDataBaseHandler.fetchDataTask(value))(customExecutionService).unsafePerformAsync {
        case -\/(error) =>
          System.err.println(s"Unexpected error: $error")
          customExecutionService.shutdown()
        case \/-(result) =>
          println(s"[Database async] USD to ${value.toString.toUpperCase} rate: $result")
          customExecutionService.shutdown()
      }
    }
  }
}
