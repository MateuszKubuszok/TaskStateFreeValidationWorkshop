package workshop.common

object DataSource extends Enumeration {
  type DataSource = Value
  val DataBase = Value("database")
  val Internet = Value("internet")
}
