package services

import model._
import play.api.libs.ws.WSClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class WeatherService(ws: WSClient) {
  def getWeather(lng: Double, lat: Double): Future[Option[WeatherInfo]] = {
    val weatherResponseF = ws.url("http://api.openweathermap.org/data/2.5/" +
      s"weather?lat=$lat&lon=$lng&units=metric&APPID=${WeatherService.OPENWP_APPID}").get

    val result = weatherResponseF map { response => response.json.validate[WeatherInfo].asOpt }
    result.recover {
      case _ => None
    }
  }
}

class SunInfoService(ws: WSClient) {
  def getSunInfo(lng: Double, lat: Double): Future[Option[SunInfo]] = {

  }
}

object WeatherService {
  val OPENWP_APPID = "a607461f4d50942e286a86e98cf35e73"
  def apply(ws: WSClient): WeatherService = new WeatherService(ws)
}

object SunInfoService {
  def apply(ws: WSClient): SunInfoService = new SunInfoService(ws)
}