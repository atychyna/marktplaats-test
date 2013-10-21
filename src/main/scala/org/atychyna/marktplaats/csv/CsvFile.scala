package org.atychyna.marktplaats.csv

/**
 * @author Anton Tychyna
 */
class CsvFile(val body: IndexedSeq[IndexedSeq[String]], val header: Option[IndexedSeq[String]] = None) extends IndexedSeq[IndexedSeq[String]] {

  import scala._

  def length = if (header.isDefined) body.length + 1 else body.length

  def apply(idx: Int) = {
    if (header.isDefined) {
      if (idx == 0) header.get else body(idx - 1)
    } else {
      body(idx)
    }
  }
}