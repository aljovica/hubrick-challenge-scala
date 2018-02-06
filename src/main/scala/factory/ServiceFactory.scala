package factory

import service._

object ServiceFactory {
  def getCalculationService = new CalculationService(new StatisticsService)
  def getCsvService = new CsvService(new ValidationService)
  def getDataStoreService = new DataStoreService(getCsvService)
}
