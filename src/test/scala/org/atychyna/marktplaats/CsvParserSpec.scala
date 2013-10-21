package org.atychyna.marktplaats

import org.specs2.mutable.Specification
import scala.io.Source
import org.atychyna.marktplaats.csv.{DefaultCsvConfig, CsvParser}

/**
 * @author Anton Tychyna
 */
class CsvParserSpec extends Specification {
  "CsvParser" should {
    "parse simple string" in {
      val csv = CsvParser().parse(Source.fromString("test,anton,tychyna"))
      csv must have length 1
      csv must beEqualTo(IndexedSeq(IndexedSeq("test", "anton", "tychyna")))
    }

    "parse quoted fields" in {
      val csv = CsvParser().parse(Source.fromString( """"test, quote",anton,tychyna"""))
      csv must have length 1
      csv must beEqualTo(IndexedSeq(IndexedSeq("test, quote", "anton", "tychyna")))
    }

    "parse csv file with header" in {
      val file = Seq("header1,header2,header3", "value1,value2,value3")
      val csv = CsvParser().parse(Source.fromString(file.mkString("\n")), DefaultCsvConfig(true))
      csv must have length 2
      csv.header must beSome[IndexedSeq[String]](IndexedSeq("header1", "header2", "header3"))
      csv must beEqualTo(IndexedSeq(IndexedSeq("header1", "header2", "header3"), IndexedSeq("value1", "value2", "value3")))
    }

    "parse ISO-8859-1 encoded file" in {
      val csv = CsvParser().parse(Source.fromInputStream(getClass.getResourceAsStream("/test.csv"), "ISO-8859-1"))
      csv must have length 8
      // do some random checks
      csv(1) must contain("Johnson, John")
      csv(2) must contain("03/12/1965")
      csv(3) must contain("0313-398475")
      csv(7) must contain("Børkestraße 32")
    }
  }
}
