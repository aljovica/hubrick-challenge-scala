package service

import model.EmployeeCsv
import model.{Department, Employee}

class DataStoreService(csvService: CsvService) {
  def loadEmployees(dataPath: String): List[Employee] = {
    val employeeNameToAge = csvService.readAges(dataPath).map(ageCsv => ageCsv.name -> ageCsv.age).toMap

    // zipWithIndex returns a tuple(departmentId, index). The syntax for mapping tuples is uggly. It would look like this:
    // map(tuple => tuple._2 + 1 -> tuple._1.name) For that reason pattern matching is used to give them names
    // x -> y is a syntax for creating map entries
    // So what this code does is get the sorted departments, zips it with an index and uses this information to create a map of index -> depName
    val sortIdToDepartmentName = csvService.readDepartmentsSorted(dataPath).zipWithIndex.map {
      case (depCsv, index) => index + 1 -> depCsv.name
    }.toMap

    csvService.readEmployees(dataPath).flatMap(employeeCsv => mapToEmployee(employeeCsv, employeeNameToAge, sortIdToDepartmentName))
  }

  private def mapToEmployee(employeeCsv: EmployeeCsv, employeeNameToAge: Map[String, Int], sortIdToDepartmentName: Map[Int, String]) = {
    val employeeName = employeeCsv.name
    val sortId = employeeCsv.departmentId

    // pattern matching is used to identify 3 different case
    // 1. age exists in ages.csv and department id relates to departments.csv. Create employee object in that case
    // 2. department id does not exist. Message is logged and None is returned
    // 3. age does not exists for this user. Message is logged and None is returned
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