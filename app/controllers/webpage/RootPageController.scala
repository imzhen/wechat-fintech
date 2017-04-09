package controllers.webpage

import javax.inject.Inject

import play.api.mvc.{Action, Controller}

/**
  * Created by Elliott on 4/7/17.
  */
class RootPageController @Inject() extends Controller {
  def get = Action {
    Ok("Welcome Home!")
  }
}
