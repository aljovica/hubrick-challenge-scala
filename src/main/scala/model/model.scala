package object model {
  trait CsvModel
  trait CsvWritable {
    def toCsvLine: String
  }

  case class AgeIncomeRangeCsv(range: Range, income: Double) extends CsvWritable {
    def toCsvLine = s"[${range.start} ${range.end}],$income"
  }

  case class DepartmentOutputCsv(departmentName: String, value: String) extends CsvWritable {
    override def toCsvLine: String = s"$departmentName,$value"
  }

  case class AgeCsv(name: String, age: Int) extends CsvModel
  case class EmployeeCsv(departmentId: Int, name: String, gender: String, salary: Double) extends CsvModel
  case class DepartmentCsv(name: String) extends CsvModel

  case class Department(id: Int, name: String)
  case class Employee(department: Department, name: String, gender: String, salary: Double, age: Integer)
}