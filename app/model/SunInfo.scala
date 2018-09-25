package model

import play.api.libs.json.{JsPath, Reads}
import play.api.libs.functional.syntax._

case class SunInfo(sunrise: String, sunset: String)

object SunInfo {
  implicit val sunInfoReads: Reads[SunInfo] = (
    (JsPath \ "results" \ "sunrise").read[String] and (JsPath \ "results" \ "sunset").read[String]
    )(SunInfo.apply _)
}