package io.scalawave.workshop.validation

import io.scalawave.workshop.validation.DataSource.DataSource

object DataSource extends Enumeration {
  type DataSource = Value
  val DataBase = Value("database")
  val Internet = Value("internet")
}

case class Config(accuracy: Int, dataSource: DataSource)

sealed trait ConfigError
case class NotANumber(value: String) extends ConfigError
case class NotNatural(value: String) extends ConfigError
case class InvalidDataSource(dataSource: String) extends ConfigError
