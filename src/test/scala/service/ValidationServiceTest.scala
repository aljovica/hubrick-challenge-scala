package service

import org.scalatest.{FlatSpec, Matchers}

class ValidationServiceTest extends FlatSpec with Matchers {
  def validationService = new ValidationService

  "Validation Service" should "should import valid age line" in {
    val line = "Julius Glover,44"

    validationService.validateAge(line) should matchPattern { case Some(_) => }
  }

  "Validation Service" should "not import age line with empty age" in {
    val line = "Julius Glover,"

    validationService.validateAge(line) should matchPattern { case None => }
  }

  "Validation Service" should "not import age line with age as string" in {
    val line = "Julius Glover,4O"

    validationService.validateAge(line) should matchPattern { case None => }
  }

  "Validation Service" should "not import age line with three elements" in {
    val line = "Julius Glover,34,56"

    validationService.validateAge(line) should matchPattern { case None => }
  }

  "Validation Service" should "not import age line with different delimiter" in {
    val line = "Julius Glover|34"

    validationService.validateAge(line) should matchPattern { case None => }
  }

  "Validation Service" should "import valid employee line" in {
    val line = "1,Judith Ford,f,2820.00"

    validationService.validateEmployee(line) should matchPattern { case Some(_) => }
  }

  "Validation Service" should "import employee with missing gender" in {
    val line = "1,Judith Ford,,2820.00"

    validationService.validateEmployee(line) should matchPattern { case Some(_) => }
  }

  "Validation Service" should "import employee with unknown gender" in {
    val line = "1,Judith Ford,d,2820.00"

    validationService.validateEmployee(line) should matchPattern { case Some(_) => }
  }

  "Validation Service" should "not import employee with less then 4 elements" in {
    val line = "1,Judith Ford,2820.00"

    validationService.validateEmployee(line) should matchPattern { case None => }
  }

  "Validation Service" should "not import employee with more then 4 elements" in {
    val line = "1,Judith Ford,d,2820.00,45"

    validationService.validateEmployee(line) should matchPattern { case None => }
  }

  "Validation Service" should "not import employee with different delimiter" in {
    val line =  "1|Judith Ford|d|2820.00|45"

    validationService.validateEmployee(line) should matchPattern { case None => }
  }
}
