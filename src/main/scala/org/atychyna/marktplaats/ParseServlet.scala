package org.atychyna.marktplaats

import org.scalatra._

/**
 * @author Anton Tychyna
 */
class ParseServlet extends ScalatraServlet {
  val c = new ParseConfig {

    import Formatters._

    def formatter(idx: Int) = {
      idx match {
        case 2 => removeWhiteSpaces.andThen(capitalize)
        case 3 => removeWhiteSpaces
        case _ => id
      }
    }
  }
  val csv = new CsvFile

  get("/") {
    <html>
      <table>
        <tr>
          {for (h <- csv.header.get) yield <th>{h}</th>}
        </tr>
        {for (l <- csv.lines) yield
          <tr>
            {for (f <- l) yield <td>{f}</td>}
          </tr>}
      </table>
    </html>
  }
}