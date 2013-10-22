package org.atychyna.marktplaats.csv

import scala.collection.immutable.LinearSeq
import scala.collection.{mutable, LinearSeqOptimized}
import scala.collection.mutable.ListBuffer

/**
 * CsvFile is a collection of lines (body) with optional header.
 *
 * @author Anton Tychyna
 */
class CsvFile(val body: List[IndexedSeq[String]], val header: Option[IndexedSeq[String]] = None) extends LinearSeq[IndexedSeq[String]] with LinearSeqOptimized[IndexedSeq[String], CsvFile] {

  override def tail = new CsvFile(body.tail, header)

  override def head = body.head

  override def isEmpty = body.isEmpty

  override protected[this] def newBuilder = CsvFile.newBuilder
}

object CsvFile {
  def newBuilder: mutable.Builder[IndexedSeq[String], CsvFile] = new ListBuffer[IndexedSeq[String]].mapResult(b => new CsvFile(body = b))
}