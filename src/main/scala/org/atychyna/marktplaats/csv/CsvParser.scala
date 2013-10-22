package org.atychyna.marktplaats.csv

import scala.io.Source
import scala.util.parsing.combinator.RegexParsers

/**
 * @author Anton Tychyna
 */
trait CsvParser {
  def parse(s: Source, c: CsvParseConfig = DefaultCsvConfig): CsvFile
}

object CsvParser {

  //based on http://stackoverflow.com/questions/5063022/use-scala-parser-combinator-to-parse-csv-files
  //with several improvements/simplifications (no double-quotes handling, better empty files handling)
  object CSV extends RegexParsers {
    override val skipWhitespace = false

    def COMMA = ","

    def QUOTE = "\""

    def CRLF = "\r\n" | "\n"

    def TXT = "[^\",\r\n]".r

    def SPACES = "[ \t]+".r

    def file: Parser[List[IndexedSeq[String]]] = repsep(record, CRLF) <~ (CRLF ?)

    def record: Parser[IndexedSeq[String]] = repsep(field, COMMA) ^? {
      case s if !s.head.isEmpty || !s.tail.isEmpty => s.toIndexedSeq
    }

    def field: Parser[String] = quoted | unquoted

    def quoted: Parser[String] = {
      ((SPACES ?) ~> QUOTE ~> ((TXT | COMMA | CRLF) *) <~ QUOTE <~ (SPACES ?)) ^^ {
        case ls => ls.mkString("")
      }
    }

    def unquoted: Parser[String] = (TXT *) ^^ {
      case ls => ls.mkString("")
    }

    def parse(s: Source) = parseAll(file, s.mkString) match {
      case Success(res, _) => res
      case _ => List[IndexedSeq[String]]()
    }
  }

  def apply() = new CsvParser {
    def parse(source: Source, c: CsvParseConfig): CsvFile = {
      val csv = CSV.parse(source)
      val header = if (c.withHeader && !csv.isEmpty) Some(csv.head) else None
      val body = if (c.withHeader) csv.tail else csv
      new CsvFile(body = body.map(fields => fields.zipWithIndex.map({
        case (s, i) => c.formatter(i)(s)
      })), header = header)
    }
  }
}