import org.atychyna.marktplaats.web.DisplayServlet
import org.scalatra.LifeCycle
import javax.servlet.ServletContext

/**
 * @author Anton Tychyna
 */
class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context mount(new DisplayServlet, "/*")
  }
}
