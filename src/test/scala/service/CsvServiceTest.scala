package service

import java.io.IOException

import factory.ServiceFactory
import model.DepartmentCsv
import org.scalatest.{FlatSpec, Matchers}

class CsvServiceTest extends FlatSpec with Matchers {
  private val BASE_PATH = System.getProperty("user.dir") + "/src/test/resources/input"
  private val EMPTY_FILES_BASE_PATH = System.getProperty("user.dir") + "/src/test/resources/emptyfiles"
  private val csvService = ServiceFactory.getCsvService

  "Csv Service" should "should read all employees" in {
    csvService.readEmployees(BASE_PATH).size should equal(99)
  }

  "Csv Service" should "should read all ages" in {
    csvService.readAges(BASE_PATH).size should equal(101)
  }

  "Csv Service" should "should read all departments in sorted order" in {
    val sortedDepartments =
      List("Accounting", "Business Development", "Human Resources", "Information Technology", "Marketing", "Public Relations", "Sales")
      .map(DepartmentCsv)

    csvService.readDepartmentsSorted(BASE_PATH) should equal(sortedDepartments)
  }

  "Csv Service" should "should throw an exception if file does not exist" in {
    val invalidPath = "invalidPath"
    val departmentException = intercept[IOException](csvService.readDepartmentsSorted("invalidPath"))
    val agesException = intercept[IOException](csvService.readAges("invalidPath"))
    val employeeException = intercept[IOException](csvService.readEmployees("invalidPath"))

    departmentException.getMessage should equal(s"Unable to read from file $invalidPath//departments.csv")
    agesException.getMessage should equal(s"Unable to read from file $invalidPath//ages.csv")
    employeeException.getMessage should equal(s"Unable to read from file $invalidPath//employees.csv")
  }

  "Csv Service" should "should throw an exception if file is empty" in {
    val path = EMPTY_FILES_BASE_PATH
    val departmentException = intercept[IOException](csvService.readDepartmentsSorted(path))
    val agesException = intercept[IOException](csvService.readAges(path))
    val employeeException = intercept[IOException](csvService.readEmployees(path))

    departmentException.getMessage should equal(s"Trying to open empty text file $path//departments.csv")
    agesException.getMessage should equal(s"Trying to open empty text file $path//ages.csv")
    employeeException.getMessage should equal(s"Trying to open empty text file $path//employees.csv")
  }
}
