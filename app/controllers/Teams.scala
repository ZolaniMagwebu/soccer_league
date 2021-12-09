package controllers

import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.data.validation.Constraints._
import views._
import models._
import play.Logger
import play.api.libs.json.Json
import play.mvc.Controller

import javax.inject.{Inject, Singleton}
@Singleton
class Teams @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  val teamForm: Form[Team] = Form(
    mapping(
      "name" -> nonEmptyText,
    )(Team.apply)(Team.unapply)
  )

  def apiDelete = Action { implicit request: Request[AnyContent] =>
    request.body.asJson.map { json =>
      val name = (json \ "name").validate[String].getOrElse("")
      val team = Team.findByName(name).getOrElse(Team())
      Team.remove(team)
      Redirect(routes.Matches.leagueRanking)
    }.getOrElse {
      BadRequest
    }
  }
  def apiAdd = Action { implicit request: Request[AnyContent] =>
    request.body.asJson.map { json =>
      val name = (json \ "name").validate[String].getOrElse("")
      val team = Team.findByName(name).getOrElse(Team())
      Team.add(team.copy(name = name ))
      Redirect(routes.Matches.leagueRanking)
    }.getOrElse {
      BadRequest
    }
  }

}