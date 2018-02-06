import java.lang.System.{exit, out}

import configuration.Configuration._
import factory.ServiceFactory

import scala.util.{Failure, Success, Try}

object HubrickStatisticsApplication extends App {
  if (args.length != 1) {
    out.println("Application excepts only one argument.")
    exit(-1)
  }

  val dataPath = args(0)

  val dataStoreService = ServiceFactory.getDataStoreService
  val csvService = ServiceFactory.getCsvService
  val calculationService = ServiceFactory.getCalculationService

  Try {
    dataStoreService.loadEmployees(dataPath)
  } match {
    case Success(employees) =>
      csvService.writeAsCsv(AVERAGE_INCOME_BY_RANGE_FILE, AVERAGE_INCOME_BY_RANGE_HEADER, calculationService.averageIncomeByAgeRange(employees))
      csvService.writeAsCsv(EMPLOYEE_AGE_BY_DEPARTMENT_FILE, EMPLOYEE_AGE_BY_DEPARTMENT_HEADER, calculationService.medianAgeByDepartment(employees))
      csvService.writeAsCsv(INCOME_BY_DEPARTMENT_FILE, INCOME_BY_DEPARTMENT_HEADER, calculationService.medianIncomeByDepartment(employees))
      csvService.writeAsCsv(INCOME_95_BY_DEPARTMENT_FILE, INCOME_95_BY_DEPARTMENT_HEADER, calculationService.percentile95ByDepartment(employees))
    case Failure(throwable) => println(s"${throwable.getLocalizedMessage}. Will terminate the application.")
  }
}
