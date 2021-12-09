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
import play.api.i18n.{I18nSupport, Lang, Langs}
import play.Logger
import play.api.libs.json.Json
import play.mvc.Controller

import javax.inject.{Inject, Singleton}

@Singleton
class Matches @Inject()(val controllerComponents: ControllerComponents) extends BaseController with I18nSupport {
  val matchForm: Form[Match] = Form(
    mapping(
      "firstTeam" -> mapping("name" ->nonEmptyText)(Team.apply)(Team.unapply),
      "secondTeam" -> mapping("name" ->nonEmptyText)(Team.apply)(Team.unapply),
      "firstTeamScore" -> number,
      "secondTeamScore" -> number,
            "draw" -> boolean
    )(Match.apply)(Match.unapply)
  )

  def leagueRanking = Action{ implicit request: Request[AnyContent] =>
    val rank = Log.rankTeams()
    Ok(views.html.log.list(rank))
  }
  def addMatchResult = Action{implicit request: Request[AnyContent] =>
    Ok(views.html.index(matchForm))

  }
 def submit = Action { implicit request =>
    matchForm.bindFromRequest.fold(
      errors => {
        BadRequest(html.index(errors))
      },
      game => {
        val firstTeam= game.firstTeam
        val secondTeam = game.secondTeam
        val firstTeamScore = game.firstTeamScore
        val secondTeamScore = game.secondTeamScore
        val draw = firstTeamScore != secondTeamScore
        Match.findMatchWinner(firstTeam)(secondTeam)(firstTeamScore)(secondTeamScore)(draw)
        Redirect(routes.Matches.leagueRanking)
      }
    )
  }

  def apiAdd = Action { implicit request: Request[AnyContent] =>
    request.body.asJson.map { json =>
      val firstTeamJson = (json \ "firstTeam").validate[String].getOrElse("")
      val secondTeamJson = (json \ "secondTeam").validate[String] getOrElse ("")
      val firstTeamScore = (json \ "firstTeamScore").validate[String].getOrElse("0").toInt
      val secondTeamScore = (json \ "secondTeamScore").validate[String].getOrElse("0").toInt
      val draw = firstTeamScore != secondTeamScore
      val firstTeam = Team.findByName(firstTeamJson).getOrElse(Team())
      val secondTeam = Team.findByName(secondTeamJson).getOrElse(Team())
      Match.findMatchWinner(firstTeam)(secondTeam)(firstTeamScore)(secondTeamScore)(draw)
      Ok(Json.obj("success" -> true))
    }.getOrElse {
      BadRequest
    }
  }

}