package service


import builder.RangeBuilder.buildRanges
import configuration.Configuration.{PERCENTILE_FOR_CALCULATION, RANGE_FACTOR}
import model.{AgeIncomeRangeCsv, Department, DepartmentOutputCsv, Employee}

import scala.collection.immutable.List.empty
import scala.util.{Success, Try}

class CalculationService(statisticsService: StatisticsService) {
  def medianIncomeByDepartment(allEmployees: List[Employee]): List[DepartmentOutputCsv] = {
    getDepartmentToValuesMap(allEmployees, _.salary)
      .mapValues(salaries => statisticsService.calculateMedian(salaries))
      .map(mapToCsv).toList
  }

  def medianAgeByDepartment(allEmployees: List[Employee]): List[DepartmentOutputCsv] = {
    getDepartmentToValuesMap(allEmployees, _.age.doubleValue)
      .mapValues(ages => statisticsService.calculateMedian(ages))
      .map(mapToCsv).toList
  }

  def percentile95ByDepartment(allEmployees: List[Employee]): List[DepartmentOutputCsv] = {
    getDepartmentToValuesMap(allEmployees, _.salary)
      .mapValues(salaries => statisticsService.calculatePercentile(salaries, PERCENTILE_FOR_CALCULATION))
      .map(mapToCsv).toList
  }

  def averageIncomeByAgeRange(allEmployees: List[Employee]): List[AgeIncomeRangeCsv] = {
    val ages = allEmployees.map(_.age)
    val minAge = Try(ages.min)
    val maxAge = Try(ages.max)

    // (x, y) is a syntax for a tupple. '_ =>' means match anything and dont care about the match result.
    (minAge, maxAge) match {
      case (Success(min), Success(max)) => buildRanges(min, max, RANGE_FACTOR).map(range => mapToCsv(range, allEmployees)).toList
      case _ => empty
    }
  }

  private def getDepartmentToValuesMap(employees: List[Employee], mappingFunction: Employee => Double) = {
    employees.groupBy(_.department).mapValues(values => values.map(mappingFunction))
  }

  private def mapToCsv(range: Range, employees: List[Employee]) = {
    val filteredSalaries = employees.filter(employee => range.toList.contains(employee.age)).map(_.salary)
    AgeIncomeRangeCsv(range, statisticsService.calculateAverage(filteredSalaries))
  }

  private def mapToCsv(tuple: (Department, Double)) = {
    tuple match {
      case (department, value) => DepartmentOutputCsv(department.name, value.toString)
    }
  }
}