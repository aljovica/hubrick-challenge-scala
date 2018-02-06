package service

import factory.ServiceFactory
import org.scalatest.{FlatSpec, Matchers}
import utils.CsvUtils.{readAgeRangeIncome, readDepartmentsOutput}

class CalculationServiceTest extends FlatSpec with Matchers {
  private val BASE_PATH = System.getProperty("user.dir") + "/src/test/resources/input"
  private val OUTPUT_BASE_PATH = System.getProperty("user.dir") + "/src/test/resources/output"
  private val INCOME_DEPARTMENTS_PATH = OUTPUT_BASE_PATH + "/income-by-department.csv"
  private val PERCENTILE_DEPARTMENTS_PATH = OUTPUT_BASE_PATH + "/income-95-by-department.csv"
  private val AVERAGE_INCOME_AGE_PATH = OUTPUT_BASE_PATH + "/income-average-by-age-range.csv"
  private val MEDIAN_AGE_DEPARTMENTS_PATH = OUTPUT_BASE_PATH + "/employee-age-by-department.csv"

  private val calculationService = ServiceFactory.getCalculationService
  private val dataStoreService = ServiceFactory.getDataStoreService
  private val allEmployees = dataStoreService.loadEmployees(BASE_PATH)

  "App" should "calculate median income by department" in {
    val expected = readDepartmentsOutput(INCOME_DEPARTMENTS_PATH).toSet
    val actual = calculationService.medianIncomeByDepartment(allEmployees).toSet

    expected should equal(actual)
  }

  "App" should "calculate median age by department" in {
    val expected = readDepartmentsOutput(MEDIAN_AGE_DEPARTMENTS_PATH).toSet
    val actual = calculationService.medianAgeByDepartment(allEmployees).toSet

    expected should equal(actual)
  }

  "App" should "calculate 95th percentile per department" in {
    val expected = readDepartmentsOutput(PERCENTILE_DEPARTMENTS_PATH).toSet
    val actual = calculationService.percentile95ByDepartment(allEmployees).toSet

    expected should equal(actual)
  }

  "App" should "calculate average income by age range" in {
    val expected = readAgeRangeIncome(AVERAGE_INCOME_AGE_PATH).toSet
    val actual = calculationService.averageIncomeByAgeRange(allEmployees).toSet

    expected should equal(actual)
  }
}