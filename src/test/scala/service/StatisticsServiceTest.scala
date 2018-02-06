package service

import org.scalatest.{FlatSpec, Matchers}

class StatisticsServiceTest extends FlatSpec with Matchers {
  val statisticsService = new StatisticsService

  "Statistics Service" should "should calculate correct median for odd list" in {
    statisticsService.calculateMedian(List(9, 1, 5, 3, 10, 7, 11, 20, 21)) should equal(9)
  }

  "Statistics Service" should "should calculate correct median for even list" in {
    statisticsService.calculateMedian(List(21, 3, 8, 5, 1, 9, 11, 20)) should equal(8.5)
  }

  "Statistics Service" should "should calculate correct median for list with one element" in {
    statisticsService.calculateMedian(List(4)) should equal(4)
  }

  "Statistics Service" should "should calculate correct median for list with two elements" in {
    statisticsService.calculateMedian(List(4, 6)) should equal(5)
  }

  "Statistics Service" should "should calculate correct median for list with three elements" in {
    statisticsService.calculateMedian(List(4, 6, 5)) should equal(5)
  }

  "Statistics Service" should "should calculate correct percentile" in {
    statisticsService.calculatePercentile(List(9, 1, 5, 3, 10, 7, 11, 20, 21, 45, 23, 24, 32, 1, 24, 8), 60) should equal(20)
    statisticsService.calculatePercentile(List(9, 1, 5, 3, 10, 7, 11, 20, 21, 45, 23, 24, 32, 1, 24, 8), 95) should equal(45)
  }

  "Statistics Service" should "should calculate correct percentile for list with only two elements" in {
    statisticsService.calculatePercentile(List(1, 9), 10) should equal(1)
    statisticsService.calculatePercentile(List(9, 1), 60) should equal(9)
  }

  "Statistics Service" should "should calculate correct percentile for list with only one element" in {
    statisticsService.calculatePercentile(List(9), 60) should equal(9)
  }

  "Statistics Service" should "should calculate correct average" in {
    statisticsService.calculateAverage(List(9, 7, 6, 5, 4)) should equal(6.2)
  }

  "Statistics Service" should "should calculate correct average for empty list" in {
    statisticsService.calculateAverage(List()) should equal(0.0)
  }
}