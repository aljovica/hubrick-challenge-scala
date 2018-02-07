package object model {
  trait CsvWritable {
    def toCsvLine: String
  }

  case class AgeIncomeRangeCsv(range: Range, income: Double) extends CsvWritable {
    def toCsvLine = s"[${range.start} ${range.end}],$income"
  }

  case class DepartmentOutputCsv(departmentName: String, value: String) extends CsvWritable {
    override def toCsvLine: String = s"$departmentName,$value"
  }

  case class AgeCsv(name: String, age: Int)
  case class EmployeeCsv(departmentId: Int, name: String, gender: String, salary: Double)
  case class DepartmentCsv(name: String)

  case class Department(id: Int, name: String)
  case class Employee(department: Department, name: String, gender: String, salary: Double, age: Integer)
}