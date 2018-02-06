package utils

import model.{AgeIncomeRangeCsv, DepartmentOutputCsv}

import scala.io.Source.fromFile

object CsvUtils {
  def readAgeRangeIncome(path: String): Iterator[AgeIncomeRangeCsv] = {
    fromFile(path).getLines().map(parseIncomeByAgeRange)
  }

  def readDepartmentsOutput(path: String): Iterator[DepartmentOutputCsv] = {
    fromFile(path).getLines().map(line => createIncomeDepartment(line))
  }

  private def createIncomeDepartment(line: String) = {
    val elements = line.split(",")
    val departmentName = elements(0)
    val income = elements(1)

    DepartmentOutputCsv(departmentName, income)
  }

  private def parseIncomeByAgeRange(line: String) = {
    val firstSplit = line.split("],")
    val secondSplit = firstSplit(0).replace("[", "").split(" ")
    val begin = Integer.valueOf(secondSplit(0))
    val end = Integer.valueOf(secondSplit(1))
    val income = firstSplit(1).toDouble

    AgeIncomeRangeCsv(Range.inclusive(begin, end), income)
  }
}
