package org.atychyna.marktplaats

/**
 * @author Anton Tychyna
 */
object Formatters {
  val removeWhiteSpaces = formatter(_.replaceAll("\\s", ""))
  val id = formatter(identity)
  val capitalize = formatter(_.toUpperCase)

  def formatter(f: String => String) = f
}