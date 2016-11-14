package io.scalawave.workshop.common

sealed trait ParsingError

final case class NotANumber(value: String) extends ParsingError {

  override def toString: String = s"$value is not a number"
}

final case class NotNatural(value: String) extends ParsingError {

  override def toString: String = s"$value is not a natural number"
}

final case class InvalidActionType(value: String) extends ParsingError {

  override def toString: String = s"$value is not a valid action type (${ActionType.values.mkString(", ")})"
}

final case class InvalidCurrency(value: String) extends ParsingError {

  override def toString: String = s"$value is not a valid currency (${Currency.values.mkString(", ")})"
}

final case class InvalidDataSource(value: String) extends ParsingError {

  override def toString: String = s"$value is not a valid data source (${DataSource.values.mkString(", ")})"
}
