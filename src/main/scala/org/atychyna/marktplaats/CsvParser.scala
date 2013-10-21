package org.atychyna.marktplaats

import scala.io.Source

/**
 * @author Anton Tychyna
 */
trait CsvParser {
  def parse(s: Source, c: ParseConfig = DefaultConfig): CsvFile
}

object CsvParser {
  def apply = ???
}

trait ParseConfig {
  def formatter(idx: Int): String => String
}

object DefaultConfig extends ParseConfig {
  def formatter(idx: Int) = Formatters.id
}

