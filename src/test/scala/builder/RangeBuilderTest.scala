package builder

import builder.RangeBuilder.buildRanges
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.immutable.Range.inclusive

class RangeBuilderTest extends FlatSpec with Matchers {
  "RangeBuilder" should "calculate correct range for case 1" in {
    val expected = List(inclusive(20, 34), inclusive(35, 49), inclusive(50, 64), inclusive(65, 79))

    expected should equal(buildRanges(20, 79, 4))
  }

  "RangeBuilder" should "calculate correct range for case 2" in {
    val expected = List(inclusive(18, 39), inclusive(40, 60), inclusive(61, 82))

    expected should equal(buildRanges(18, 82, 3))
  }

  "RangeBuilder" should "calculate correct range for case 3" in {
    val expected = List(
      inclusive(19, 24), inclusive(25, 30), inclusive(31, 37), inclusive(38, 43), inclusive(44, 49),
      inclusive(50, 55), inclusive(56, 61), inclusive(62, 68), inclusive(69, 74), inclusive(75, 80)
    )

    expected should equal(buildRanges(19, 80, 10))
  }

  "RangeBuilder" should "calculate 7 ranges" in {
    val expected = inclusive(19, 25).map(range => inclusive(range, range))

    expected should equal(buildRanges(19, 25, 10))
  }

  "RangeBuilder" should "calculate only one range" in {
    val expected = List(inclusive(19, 19))

    expected should equal(buildRanges(19, 19, 10))
  }
}
