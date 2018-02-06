package service

import factory.ServiceFactory
import org.scalatest.{FlatSpec, Matchers}

class DataStoreServiceTest extends FlatSpec with Matchers {
  private val BASE_PATH = System.getProperty("user.dir") + "/src/test/resources/data-store-tests/"
  private val dataStoreService = ServiceFactory.getDataStoreService

  "Data Store Service" should "not load employee with not existing department id" in {
    val employees = dataStoreService.loadEmployees(s"$BASE_PATH/case-1")
    employees.size should equal(1)
    employees.head.name should equal("Opal Ballard")
  }

  "Data Store Service" should "load only load employee without age" in {
    val employees = dataStoreService.loadEmployees(s"$BASE_PATH/case-2")
    employees.size should equal(1)
    employees.head.name should equal("Otis Bell")
  }
}
