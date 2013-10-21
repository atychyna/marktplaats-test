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

  object CSV extends RegexParsers {
    override val skipWhitespace = false

    def COMMA = ","

    def QUOTE = "\""

    def CRLF = "\r\n" | "\n"

    def TXT = "[^\",\r\n]".r

    def SPACES = "[ \t]+".r

    def file: Parser[IndexedSeq[IndexedSeq[String]]] = repsep(record, CRLF) <~ (CRLF ?) ^^ {
      case s => s.toIndexedSeq
    }

    def record: Parser[IndexedSeq[String]] = repsep(field, COMMA) ^^ {
      case s => s.toIndexedSeq
    }

    def field: Parser[String] = escaped | nonescaped

    def escaped: Parser[String] = {
      ((SPACES ?) ~> QUOTE ~> ((TXT | COMMA | CRLF) *) <~ QUOTE <~ (SPACES ?)) ^^ {
        case ls => ls.mkString("")
      }
    }

    def nonescaped: Parser[String] = (TXT *) ^^ {
      case ls => ls.mkString("")
    }

    def parse(s: Source) = parseAll(file, s.mkString) match {
      case Success(res, _) => res
      case _ => IndexedSeq[IndexedSeq[String]]()
    }
  }

  def apply() = new CsvParser {
    def parse(source: Source, c: CsvParseConfig): CsvFile = {
      val csv = CSV.parse(source)
      val header = if (c.withHeader && !csv.isEmpty) Some(csv(0)) else None
      val body = if (c.withHeader) csv.drop(1) else csv
      new CsvFile(body = body.map(fields => fields.view.zipWithIndex.map({
        case (s, i) => c.formatter(i)(s)
      }).toIndexedSeq), header = header)
    }
  }
}