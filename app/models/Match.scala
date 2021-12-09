package models


import play.Logger

import scala.collection.mutable.ListBuffer

case class Match(forFirstTeam:Team=Team(),forSecondTeam:Team=Team(),
                 firstTeamScore:Int = 0,secondTeamScore:Int = 0,
                 draw:Boolean=true){

lazy val firstTeam = Team.findByName(forFirstTeam.name).getOrElse(Team())
  lazy val secondTeam= Team.findByName(forSecondTeam.name).getOrElse(Team())

}

object Match{

  def findMatchWinner(firstTeam:Team)
                     (secondTeam:Team)
                     (firstTeamScore:Int = 0)
                     (secondTeamScore:Int = 0)
                     (draw:Boolean=false):ListBuffer[Log] ={
    if(draw){
      if( firstTeamScore > secondTeamScore){
        Logger.debug("firstTeam won")
        Logger.debug(s"firstTeam name  ${firstTeam}")
        Log.upDateWinningTeamPoint(firstTeam)
      }else if(firstTeamScore < secondTeamScore){
          Logger.debug("secondTeam won")
          Logger.debug(s"secondTeam name ${secondTeam}")
          Log.upDateWinningTeamPoint(secondTeam)
        }else
          Log.rankTeams()
    }else{
      Logger.debug(" draw")
      Log.upDrawTeamPoint(firstTeam)
      Log.upDrawTeamPoint(secondTeam)
    }
  }

}