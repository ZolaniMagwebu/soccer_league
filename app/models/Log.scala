package models


import play.Logger
import play.api.libs.json.{Json, Writes}

import scala.collection.mutable.ListBuffer

case class Log(forTeam:Team =Team(),
               point:Int = 0) extends Ordered[Log] {

 override def compare(that: Log):Int = this.point compare that.point

}

object Log {

 val leagueRanking:ListBuffer[Log] = {
   Team.teams.map{c => Log(c)}
 }

  def upDateWinningTeamPoint(team:Team=Team()):ListBuffer[Log] = {
    val findLog:Option[Log] = leagueRanking.filter(x=>x.forTeam.name.equalsIgnoreCase(team.name)).headOption
    val teamRank:Option[Log] = leagueRanking.filter(x=>x.forTeam.name.equalsIgnoreCase(team.name)).map(t => Log(t.forTeam,t.point+3)).headOption
      leagueRanking -= findLog.getOrElse(Log())
    leagueRanking += teamRank.getOrElse(Log())

  }

  def upDrawTeamPoint(team:Team=Team()):ListBuffer[Log] = {
    val findLog:Option[Log] = leagueRanking.filter(x=>x.forTeam == team).headOption
    val teamRank:Option[Log] = leagueRanking.filter(x=>x.forTeam == team).map(t => Log(t.forTeam,t.point+1)).headOption
    leagueRanking -= findLog.getOrElse(Log())
    leagueRanking += teamRank.getOrElse(Log())
  }

  def rankTeams():ListBuffer[Log] ={
    leagueRanking.sorted.reverse
  }

  implicit val logWrites = new Writes[Log] {
    def writes(log: Log) = Json.obj(
      "forTeam" -> log.forTeam.name,
      "point" -> log.point
    )
  }

}