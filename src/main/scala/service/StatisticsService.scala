package service

import utils.NumberUtils.roundToDouble

class StatisticsService {
  def calculateAverage(list: List[Double]): Double = {
    if (list.nonEmpty) roundToDouble(list.sum / list.size)
    else 0.0
  }

  def calculateMedian(list: List[Double]): Double = {
    val sortedList = list.sorted

    if (sortedList.size % 2 == 0) {
      val half = sortedList.size / 2
      val left = sortedList(half - 1)
      val right = sortedList(half)
      (left.doubleValue() + right.doubleValue()) / 2
    } else {
      sortedList(sortedList.size / 2).doubleValue()
    }
  }

  def calculatePercentile(list: List[Double], nthPercentile: Int): Double = {
    val sortedList = list.sorted

    sortedList((nthPercentile / 100.0 * sortedList.size).toInt).doubleValue()
  }
}