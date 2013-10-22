package org.atychyna.marktplaats.web

import org.scalatra._
import scala.io.Source
import org.atychyna.marktplaats.Formatters
import org.atychyna.marktplaats.csv.{CsvFile, DefaultCsvConfig, CsvParser}
import java.io.InputStream

/**
 * @author Anton Tychyna
 */
class DisplayServlet extends ScalatraServlet {
  val parseConfig = new DefaultCsvConfig(true) {

    import Formatters._

    override def formatter(idx: Int) = {
      idx match {
        case 2 => removeWhiteSpaces.andThen(capitalize)
        case 3 => removeWhiteSpaces
        case _ => id
      }
    }
  }
  val csvFile = "/file.csv"
  val csvFileEncoding = "ISO-8859-1"

  get("/") {
    val stream: InputStream = servletContext.getResourceAsStream(csvFile)
    if (stream != null) {
      val csv = CsvParser().parse(Source.fromInputStream(stream, csvFileEncoding), parseConfig)
      PageTemplate(csv)
    } else {
      s"file '$csvFile' not found"
    }
  }
}

object PageTemplate {
  def apply(csv: CsvFile) = {
      <html>
        <head>
        <style>
        {
          """.csv-table {
            font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
            font-size: 18px;
            text-align: left;
            border-collapse: collapse;
            border: 1px solid #69c;
            margin: 20px;
          }
          .csv-table th {
            font-weight: normal;
            font-size: 18px;
            color: #039;
            border-bottom: 1px dashed #69c;
            padding: 12px 17px;
          }
          .csv-table td {
            color: #669;
            padding: 7px 17px;
          }"""
        }
        </style>
        </head>
        <table class="csv-table">
          {if (csv.header.isDefined) {
          <tr>
            {for (h <- csv.header.get) yield <th>{h}</th>}
          </tr>
          }}
          {for (l <- csv) yield
            <tr>
              {for (f <- l) yield <td>{f}</td>}
            </tr>
          }
        </table>
      </html>
  }
}