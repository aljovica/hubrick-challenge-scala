package utils

import java.math.RoundingMode.HALF_UP
import java.text.DecimalFormat

object NumberUtils {
  def roundToDouble(number: Double): Double = {
    val formatter = new DecimalFormat("#0.00")
    formatter.setRoundingMode(HALF_UP)
    formatter.format(number).toDouble
  }

  def roundToInt(number: Double): Integer = {
    val formatter = new DecimalFormat("#0")
    formatter.setRoundingMode(HALF_UP)
    formatter.format(number).toInt
  }
}