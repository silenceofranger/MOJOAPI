package org.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.db.data.Client
import org.domain.ClientRequest
import spray.json.{DefaultJsonProtocol, JsString, JsValue, JsonFormat, RootJsonFormat}
trait JsonUtils extends SprayJsonSupport with DefaultJsonProtocol {

  implicit object dateFormatter extends JsonFormat[LocalDate] {
    override def write(obj: LocalDate): JsValue = {
      JsString(obj.toString)
    }

    override def read(json: JsValue): LocalDate = {
      LocalDate.parse(json.toString(), DateTimeFormatter.ISO_DATE)
    }
  }
  implicit val employeeJsonFormatter: RootJsonFormat[Client] = DefaultJsonProtocol.jsonFormat3(Client)
  implicit val employeeRequestFormat: RootJsonFormat[ClientRequest] = jsonFormat2(ClientRequest)
}
