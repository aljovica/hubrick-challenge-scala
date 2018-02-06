package builder

import utils.NumberUtils.roundToInt

import scala.collection.immutable.Range.{Double, Inclusive, inclusive}
import scala.collection.immutable.Seq

object RangeBuilder {
  def buildRanges(min: Int, max: Int, rangeFactor: Int): Seq[Inclusive] = {
    val size = (max - min) + 1
    val step = size / rangeFactor.doubleValue()

    if (size < rangeFactor) inclusive(min, max).map(range => inclusive(range, range))
    else {
      Double
        .inclusive(min, max, step)
        .map(begin => inclusive(roundToInt(begin), roundToInt(begin + step - 1))).toList
        .filter(_.nonEmpty)
    }
  }
}
