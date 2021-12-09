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
        Log.upDateWinningTeamPoint(firstTeam)
      }else if(firstTeamScore < secondTeamScore){
          Log.upDateWinningTeamPoint(secondTeam)
        }else
          Log.rankTeams()
    }else{
      Log.upDrawTeamPoint(firstTeam)
      Log.upDrawTeamPoint(secondTeam)
    }
  }

}