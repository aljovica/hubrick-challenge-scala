package service


import java.io.IOException
import java.nio.file.Files.write
import java.nio.file.Paths.get

import configuration.Configuration.{AGES_FILE, DEPARTMENTS_FILE, EMPLOYEES_FILE}
import model.{AgeCsv, CsvWritable, DepartmentCsv, EmployeeCsv}

import scala.io.Source.fromFile
import scala.util.{Failure, Success, Try}

class CsvService(validationService: ValidationService) {
  def readDepartmentsSorted(dataPath: String): List[DepartmentCsv] = {
    read(s"$dataPath/$DEPARTMENTS_FILE").map(DepartmentCsv).toList.sortBy(_.name)
  }

  def readAges(dataPath: String): List[AgeCsv] = {
    read(s"$dataPath/$AGES_FILE").flatMap(line => validationService.validateAge(line)).toList
  }

  def readEmployees(dataPath: String): List[EmployeeCsv] = {
    read(s"$dataPath/$EMPLOYEES_FILE").flatMap(line => validationService.validateEmployee(line)).toList
  }

  def writeAsCsv(fileName: String, header: String, elements: List[CsvWritable]): Unit = {
    if (elements.nonEmpty) {
      val headersLine = s"$header\n"
      val text = headersLine.concat(elements.map(_.toCsvLine).mkString("\n"))

      writeTextToFile(fileName, text)
    }
  }

  private def read(fileName: String) = {
    Try {
      fromFile(fileName).getLines()
    } match {
      case Success(lines) =>
        if (lines.isEmpty) throw new IOException(s"Trying to open empty text file $fileName")
        lines
      case Failure(_) => throw new IOException(s"Unable to read from file $fileName")
    }
  }

  private def writeTextToFile(fileName: String, text: String) = {
    Try {
      write(get(fileName), text.getBytes)
    } match {
      case Failure(_) => println(s"Unable to write to file: $fileName")
      case unit => unit
    }
  }
}