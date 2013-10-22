package org.atychyna.marktplaats.csv

import org.atychyna.marktplaats.Formatters._

/**
 * @author Anton Tychyna
 */
trait CsvParseConfig {
  /**
   * [[org.atychyna.marktplaats.csv.CsvParser C s v P a r s e r]] will format field using function
   * provided by this method.
   *
   * @param idx field's index (zero based)
   * @return formatting function
   */
  def formatter(idx: Int): String => String

  def withHeader: Boolean
}

class DefaultCsvConfig(val withHeader: Boolean) extends CsvParseConfig {
  def formatter(idx: Int) = id
}

object DefaultCsvConfig extends DefaultCsvConfig(false) {
  def apply(withHeader: Boolean) = new DefaultCsvConfig(withHeader)
}


