package controllers

import java.text.SimpleDateFormat

import controllers.Assets.Asset
import javax.inject._
import model.SunInfo
import org.joda.time.DateTime
import play.api.libs.ws.WSClient
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

class Application @Inject() (components: ControllerComponents, assets: Assets, ws: WSClient)
    extends AbstractController(components) {
  def index = Action.async {
    val timeString = new SimpleDateFormat().format(new DateTime().toDate)

    val responseF = ws.url("http://api.sunrise-sunset.org/json?"+ "lat=-33.8830&lng=151.2167&formatted=0").get()
    responseF map { response =>
      val sunInfoOpt = response.json.validate[SunInfo].asOpt

      sunInfoOpt match {
        case Some(sunInfo) => Ok(views.html.index(sunInfo))
        case None => throw new RuntimeException("SunInfo not found ")
      }
    }
  }

  def versioned(path: String, file: Asset) = assets.versioned(path, file)
}