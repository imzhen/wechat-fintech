package services.message

/**
  * Created by Elliott on 4/7/17.
  */

import scala.xml.{Node, XML}

abstract class Message(node: Node) {
  var toUserName = XMLHelper.text(node, "ToUserName")
  var fromUserName = XMLHelper.text(node, "FromUserName")
  val createTime = XMLHelper.text(node, "CreateTime")
  val msgType = XMLHelper.text(node, "MsgType")
}

case class WechatMessage(node: Node) extends Message(node) {

  def msgId = XMLHelper.text(node, "MsgId").toLong

  def content = XMLHelper.text(node, "Content")

  def mediaId = XMLHelper.text(node, "MediaId")

  def picUrl = XMLHelper.text(node, "PicUrl")

  def format = XMLHelper.text(node, "Format")

  def thumbMediaId = XMLHelper.text(node, "ThumbMediaId")

  def recognition = XMLHelper.text(node, "Recognition")

  def locationX = XMLHelper.text(node, "Location_X")

  def locationY = XMLHelper.text(node, "Location_Y")

  def scale = XMLHelper.text(node, "Scale").toInt

  def label = XMLHelper.text(node, "Label")

  def title = XMLHelper.text(node, "Title")

  def description = XMLHelper.text(node, "Description")

  def url = XMLHelper.text(node, "Url")


  def getContent = msgType

  import MessageType._

  def getType = msgType match {
    case "text" => Text
    case "voice" => Voice
  }

  def switchUser() = {
    val temp = toUserName
    toUserName = fromUserName
    fromUserName = temp
  }
}

object WechatMessage {
  def apply(s: String): WechatMessage = new WechatMessage(XML.loadString(s))
}