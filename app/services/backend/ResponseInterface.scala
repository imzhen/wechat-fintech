package services.backend

import akka.actor.{Actor, ActorLogging, Props}
import services.message.{MessageType, TextMessageResponse, WechatMessage}

/**
  * Created by Elliott on 4/8/17.
  */
class ResponseInterface extends Actor with ActorLogging {
  def receive: Receive = {
    case wechatMessage: WechatMessage if wechatMessage.getType == MessageType.Text =>
      val content = wechatMessage.content
      sender ! TextMessageResponse(wechatMessage, content)
  }
}

object ResponseInterface {
  def props = Props(new ResponseInterface)
  def name = "responseInterface"
}
