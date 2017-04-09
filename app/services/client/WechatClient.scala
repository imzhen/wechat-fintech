package services.client

import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import akka.pattern.ask
import org.apache.commons.codec.digest.DigestUtils
import play.api.mvc.RequestHeader
import services.aes.WXBizMsgCrypt
import services.backend.ResponseInterface
import services.message.{Message, MessageResponse, WechatMessage}

import scala.concurrent.ExecutionContext.Implicits.global
import akka.util.Timeout
import services.utils.Utilities

import scala.concurrent.Future
import scala.concurrent.duration._

/**
  * Created by Elliott on 4/7/17.
  */

@Singleton
class WechatClient @Inject()(system: ActorSystem) extends Crypto with WechatSettings {

  val responseInterface = system.actorOf(ResponseInterface.props, ResponseInterface.name)

  def validate(timestamp: String, nonce: String) = {
    val authList = List[String](timestamp, nonce, token).sortWith(_ < _).mkString
    DigestUtils.sha1Hex(authList)
  }

  def getResponse(message: Message): Future[String] =
    (responseInterface ? message).mapTo[MessageResponse].map{result => result.stringify}

}

trait Crypto extends WechatSettings {

  def decryptMsg(msg: String, request: RequestHeader): Future[Message] = {

    val timestamp = request.getQueryString("timestamp").getOrElse("empty")
    val nonce = request.getQueryString("nonce").getOrElse("empty")
    val encryptType = request.getQueryString("encrypt_type").getOrElse("empty")
    val msg_signature = request.getQueryString("msg_signature").getOrElse("empty")

    encryptType match {
      case "aes" => decryptAESMsg(msg_signature, timestamp, nonce, msg)
      case _ => decryptPlainMsg(msg)
    }
  }

  def decryptAESMsg(signature: String, timestamp: String, nonce: String, msg: String): Future[Message] = {
    decryptPlainMsg(msg)
  }

  def decryptPlainMsg(msg: String): Future[Message] = Future(WechatMessage(msg))

  def encryptMsg(msg: String): Future[String] = {
    val wx = new WXBizMsgCrypt(token, encodingAESKey, appId)
    Future(wx.encryptMsg(msg, Utilities.currentTimeSeconds.toString, Utilities.randomNextInt(0, 9999).toString))
  }

}

trait WechatSettings {
  val token: String = "Vt9auKvYFbm59gy72S73J7aQq4p1Ap3t"
  val encodingAESKey = "4YDkPhhlQgHiz31UFoUpw9TmllBRYSE6CwzIR1pErxe"
  val appId = "wx20469600c0722274"

  implicit val timeout: Timeout = Timeout(5.seconds)
}

