package org.client.repositories

import org.databaseconfiguration.config.DbConfig
import org.databaseconfiguration.data.Client
import org.mongodb.scala.{Completed, MongoCollection}
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.FindOneAndUpdateOptions
import org.mongodb.scala.model.Updates.{combine, set}
import org.mongodb.scala.result.DeleteResult
import org.utils.JsonUtils

import scala.concurrent.Future

object EmployeeRepo extends JsonUtils {
  private val employeeDoc: MongoCollection[Client] = DbConfig.employees

  def createCollection(): Unit = {
    DbConfig.database.createCollection("employee").subscribe(
      (result: Completed) => println(s"$result"),
      (e: Throwable) => println(e.getLocalizedMessage),
      () => println("complete"))
  }

  def insertData(emp: Client): Future[Completed] = {
    employeeDoc.insertOne(emp).toFuture()
  }

  def findAll(): Future[Seq[Client]] = {
    employeeDoc.find().toFuture()
  }

  def update(emp: Client, id: String):Future[Client] = {

    employeeDoc
      .findOneAndUpdate(equal("_id", id),
        setBsonValue(emp),
        FindOneAndUpdateOptions().upsert(true)).toFuture()
  }

  def delete(id: String): Future[DeleteResult] = {
    employeeDoc.deleteOne(equal("_id", id)).toFuture()
  }

  private def setBsonValue(emp:Client): Bson = {
    combine(
      set("name", emp.name),
      set("inboundFeedUrl",emp.inboundFeedUrl)
    )
  }
}
