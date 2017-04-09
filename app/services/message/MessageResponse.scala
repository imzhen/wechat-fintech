package services.message

import scala.xml.{Node, PCData, Elem}

import services.utils.Utilities

/**
  * Created by Elliott on 4/8/17.
  */

abstract class MessageResponse(msg: Message) {
  def toNode: Node = <xml>{nodeSeq}</xml>

  def stringify: String = XMLHelper.stringify(toNode)

  def nodeSeq: Vector[Elem] = Vector(
    <ToUserName>{PCData(msg.fromUserName)}</ToUserName>,
    <FromUserName>{PCData(msg.toUserName)}</FromUserName>,
    <CreateTime>{Utilities.currentTimeSeconds.toString}</CreateTime>,
    <MsgType>{PCData(msg.msgType)}</MsgType>
  )
}

case class TextMessageResponse(msg: Message, content: String) extends MessageResponse(msg) {

  override def nodeSeq = {
    val v = <Content>{PCData(content)}</Content>
    super.nodeSeq :+ v
  }

}
