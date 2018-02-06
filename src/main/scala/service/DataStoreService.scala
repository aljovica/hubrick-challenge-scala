package service

import model.EmployeeCsv
import model.{Department, Employee}

class DataStoreService(csvService: CsvService) {
  def loadEmployees(dataPath: String): List[Employee] = {
    val employeeNameToAge = csvService.readAges(dataPath).map(ageCsv => ageCsv.name -> ageCsv.age).toMap

    val sortIdToDepartmentName = csvService.readDepartmentsSorted(dataPath).zipWithIndex.map {
      case (depCsv, index) => index + 1 -> depCsv.name
    }.toMap

    csvService.readEmployees(dataPath).flatMap(employeeCsv => mapToEmployee(employeeCsv, employeeNameToAge, sortIdToDepartmentName))
  }

  private def mapToEmployee(employeeCsv: EmployeeCsv, employeeNameToAge: Map[String, Int], sortIdToDepartmentName: Map[Int, String]) = {
    val employeeName = employeeCsv.name
    val sortId = employeeCsv.departmentId

    (employeeNameToAge.get(employeeName), sortIdToDepartmentName.get(sortId)) match {
      case (Some(age), Some(departmentName)) =>
        Some(Employee(Department(sortId, departmentName), employeeName, employeeCsv.gender, employeeCsv.salary, age))
      case (_, None) =>
        println(s"Employee $employeeName will not be loaded because department with ID $sortId does not exist")
        None
      case (None, _) =>
        println(s"Employee $employeeName will not be loaded, because an entry in ages.csv can not be found")
        None
    }
  }
}