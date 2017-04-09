package controllers.wechat

import javax.inject.{Inject, Singleton}

import play.api.mvc.{Action, Controller}
import services.client.WechatClient

import scala.concurrent.ExecutionContext.Implicits.global


/**
  * Created by Elliott on 4/6/17.
  */
@Singleton
class AuthController @Inject()(wechatClient: WechatClient) extends Controller {

  def get(signature: String, echostr: String, timestamp: String, nonce: String) = Action { msg =>
    wechatClient.validate(timestamp, nonce) match {
      case `signature` => Ok(echostr)
      case _ => BadRequest
    }
  }


  def post = Action.async(parse.tolerantText) { request =>

    for {
      message <- wechatClient.decryptMsg(request.body, request)
      result <- wechatClient.getResponse(message)
//      encryptedResult <- wechatClient.encryptMsg(result)
    } yield {
      Ok(result).as("application/xml")
    }

  }
}

