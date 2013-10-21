package org.atychyna.marktplaats


/**
 * @author Anton Tychyna
 */
class CsvFile extends IndexedSeq[IndexedSeq[String]] {
  def lines: IndexedSeq[IndexedSeq[String]] = IndexedSeq(IndexedSeq("Johnson, John", "Voorstraat 32", "3122gg", "020 384 9381", "10000", "01/01/1987"))

  def header: Option[IndexedSeq[String]] = Some(IndexedSeq("Name", "Address", "Postcode", "Phone", "Credit Limit", "Birthday"))

  def length = lines.length + (if (header.isDefined) 1 else 0)

  def apply(idx: Int) = {
    if (header.isDefined) {
      if (idx == 0) header.get else lines(idx - 1)
    } else {
      lines(idx)
    }
  }
}