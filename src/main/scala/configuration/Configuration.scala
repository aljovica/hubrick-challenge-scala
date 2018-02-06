package configuration

object Configuration {
  val PERCENTILE_FOR_CALCULATION = 95
  val RANGE_FACTOR = 10

  private val REPORTS_PATH = System.getProperty("user.dir") + "/reports"

  val EMPLOYEES_FILE = "/employees.csv"
  val AGES_FILE = "/ages.csv"
  val DEPARTMENTS_FILE = "/departments.csv"

  val AVERAGE_INCOME_BY_RANGE_FILE = REPORTS_PATH + "/income-average-by-age-range.csv"
  val AVERAGE_INCOME_BY_RANGE_HEADER = "Age Range;Average Income"
  val EMPLOYEE_AGE_BY_DEPARTMENT_FILE = REPORTS_PATH + "/employee-age-by-department.csv"
  val EMPLOYEE_AGE_BY_DEPARTMENT_HEADER = "Department;Median Age"
  val INCOME_BY_DEPARTMENT_FILE = REPORTS_PATH + "/income-by-department.csv"
  val INCOME_BY_DEPARTMENT_HEADER = "Department;Median Income"
  val INCOME_95_BY_DEPARTMENT_FILE = REPORTS_PATH + "/income-95-by-department.csv"
  val INCOME_95_BY_DEPARTMENT_HEADER = "Department;95 Percentile Income"
}
