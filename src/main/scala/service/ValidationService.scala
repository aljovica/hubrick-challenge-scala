package service

import model.{AgeCsv, CsvModel, EmployeeCsv}

import scala.util.Try

class ValidationService {
  def validateAge(line: String): Option[AgeCsv] = {
    validate[AgeCsv](convertAgeLine, line)
  }

  def validateEmployee(line: String): Option[EmployeeCsv] = {
    validate[EmployeeCsv](convertEmployeeLine, line)
  }

  private def validate[CSVModel <: CsvModel](convertLineFunction: String => Option[CSVModel], line: String) = {
    Try(convertLineFunction(line)).toOption.flatMap {
      case None =>
        println(s"""Not able to parse line: "$line"""")
        None
      case result => result
    }
  }

  private def convertAgeLine(line: String) = {
    val elements = line.split(",")

    if (elements.size != 2) {
      println(s"""Csv line in ages.csv expected with 2 elements. Line: "$line"""")
      None
    } else {
      val name = elements(0)
      val age = elements(1).toInt

      Some(AgeCsv(name, age))
    }
  }

  private def convertEmployeeLine(line: String) = {
    val elements = line.split(",")

    if (elements.size != 4) {
      println(s"""Csv line in employees.csv expected with 4 elements. Line: "$line"""")
      None
    } else {
      val departmentId = elements(0).toInt
      val name = elements(1)
      val gender = elements(2)
      val salary = elements(3).toDouble

      Some(EmployeeCsv(departmentId, name, gender, salary))
    }
  }
}