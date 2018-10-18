package model

import play.api.libs.json.{JsPath, Reads}
import play.api.libs.functional.syntax._

case class SunInfo(sunrise: String, sunset: String)

object SunInfo {
  implicit val sunInfoReads: Reads[SunInfo] = (
    (JsPath \ "results" \ "sunrise").read[String] and (JsPath \ "results" \ "sunset").read[String]
    )(SunInfo.apply _)
}

case class WeatherInfo(temperature: Double, pressure: Int)

object WeatherInfo {
  implicit val weatherInfoReads: Reads[WeatherInfo] = (
    (JsPath \ "main" \ "temp").read[Double] and (JsPath \ "main" \ "pressure").read[Int]
    )(WeatherInfo.apply _)

  def apply(temperature: Double, pressure: Int): WeatherInfo = new WeatherInfo(temperature, pressure)
}