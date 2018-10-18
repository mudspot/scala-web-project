package controllers

import java.text.SimpleDateFormat

import controllers.Assets.Asset
import javax.inject._
import model.{SunInfo, WeatherInfo}
import org.joda.time.DateTime
import play.api.libs.ws.WSClient
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

class Application @Inject() (components: ControllerComponents, assets: Assets, ws: WSClient)
    extends AbstractController(components) {
  def index = Action.async {
    val responseF = ws.url("http://api.sunrise-sunset.org/json?"+ "lat=-33.8830&lng=151.2167&formatted=0").get()
    val weatherResponseF = ws.url("http://api.openweathermap.org/data/2.5/" +
      s"weather?lat=-33.8830&lon=151.2167&units=metric&APPID=a607461f4d50942e286a86e98cf35e73").get()

    for {
      response <- responseF
      weatherResponse <- weatherResponseF
    } yield {
      val sunInfoOpt = response.json.validate[SunInfo].asOpt
      val temperatureOpt = weatherResponse.json.validate[WeatherInfo].asOpt

      (sunInfoOpt, temperatureOpt) match {
        case (Some(sunInfo), Some(temperature)) => Ok(views.html.index(sunInfo, temperature))
        case _ => throw new RuntimeException("SunInfo or WeatherInfo not found")
      }
    }
  }

  def versioned(path: String, file: Asset) = assets.versioned(path, file)
}