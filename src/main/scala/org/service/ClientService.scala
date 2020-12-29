package org.service

import java.util.UUID

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import org.db.data.Client
import org.domain.ClientRequest
import org.mongodb.scala.Completed
import org.mongodb.scala.result.DeleteResult
import org.client.repositories.EmployeeRepo

import scala.concurrent.{ExecutionContextExecutor, Future}

class ClientService {

  implicit val actorSystem: ActorSystem = ActorSystem()
  implicit val ec: ExecutionContextExecutor = actorSystem.dispatcher
  implicit val mat: ActorMaterializer = ActorMaterializer()

  def saveEmployeeData: ClientRequest => Future[Completed] = (employeeRequest: ClientRequest) => {
    val employeeDoc:Client = employeeMapperWithNewID(employeeRequest)

    EmployeeRepo.insertData(employeeDoc)
  }

  def findAll: Source[Client, NotUsed] = {
    Source.fromFuture(EmployeeRepo.findAll())
      .mapConcat {
        identity
      }
  }

  def update(employeeRequest:ClientRequest, id: String): Future[Client] = {
    val employeeDoc:Client = employeeMapperWithNewID(employeeRequest)
    EmployeeRepo.update(emp = employeeDoc, id)
  }

  def delete(id: String): Future[DeleteResult] ={
    EmployeeRepo.delete(id)
  }
  private def employeeMapperWithNewID(employee: ClientRequest) = {
    Client(name = employee.name, inboundFeedUrl = employee.inboundFeedUrl,
      _id = UUID.randomUUID.toString)
  }
}
