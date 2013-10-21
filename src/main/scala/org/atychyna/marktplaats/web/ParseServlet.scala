package org.atychyna.marktplaats.web

import org.scalatra._
import scala.io.Source
import org.atychyna.marktplaats.Formatters
import org.atychyna.marktplaats.csv.{CsvParseConfig, CsvParser}

/**
 * @author Anton Tychyna
 */
class ParseServlet extends ScalatraServlet {
  val c = new CsvParseConfig {

    import Formatters._

    def formatter(idx: Int) = {
      idx match {
        case 2 => removeWhiteSpaces.andThen(capitalize)
        case 3 => removeWhiteSpaces
        case _ => id
      }
    }

    val withHeader = true
  }
  val csv = CsvParser().parse(Source.fromInputStream(getClass.getResourceAsStream("/Workbook2.csv"), "ISO-8859-1"), c)

  get("/") {
    <html>
      <table>
        <tr>
          {for (h <- csv.header.get) yield <th>{h}</th>}
        </tr>
        {for (l <- csv.body) yield
          <tr>
            {for (f <- l) yield <td>{f}</td>}
          </tr>}
      </table>
    </html>
  }
}