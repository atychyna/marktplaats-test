package org.atychyna.marktplaats.csv

import org.atychyna.marktplaats.Formatters._

/**
 * @author Anton Tychyna
 */
trait CsvParseConfig {
  def formatter(idx: Int): String => String

  def withHeader: Boolean
}

class DefaultCsvConfig(val withHeader: Boolean) extends CsvParseConfig {
  def formatter(idx: Int) = id
}

object DefaultCsvConfig extends DefaultCsvConfig(false) {
  def apply(withHeader: Boolean) = new DefaultCsvConfig(withHeader)
}


