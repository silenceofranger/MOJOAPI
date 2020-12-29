package org.client.actor

import akka.actor.{Actor, ActorLogging}
import org.domain.ClientRequest
import org.service.ClientService


class EmployeeActor extends Actor with ActorLogging {
  private val employeeService: ClientService = new ClientService()

  override def receive: Receive = {

    case SAVE(employee: ClientRequest) =>
      log.info(s"received message Save with employee $employee")

      sender ! employeeService.saveEmployeeData(employee)

    case SEARCH_ALL =>
      log.info(s"received message find all")
      sender() ! employeeService.findAll

    case UPDATE(emp,id) =>
      log.info(s"received message find all")
      sender() ! employeeService.update(emp,id)

    case DELETE(id)=>
      log.info(s"delete message received for the id: $id")
      sender() ! employeeService.delete(id)

    case _ =>
      log.debug("Unhandled message!")
  }
}

sealed trait EmployeeActorMessage

case class SAVE(emp: ClientRequest) extends EmployeeActorMessage

case object SEARCH_ALL extends EmployeeActorMessage

case class UPDATE(emp: ClientRequest, id: String) extends EmployeeActorMessage

case class DELETE(id: String) extends EmployeeActorMessage
