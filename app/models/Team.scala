package models


import scala.collection.mutable.ListBuffer


case class Team(name:String =""){

}

object Team {

  val teams:ListBuffer[Team] =
    ListBuffer(Team("Lions"),Team("Snakes"),Team("FC Awesome"),Team("Tarantulas"),Team("Grouches"))

  def findByName(name:String=""):Option[Team] = {
          teams.filter(t => t.name.equalsIgnoreCase(name)).headOption
  }

  def add(team:Team):Option[Team]={
    teams += team
    Some(team)
  }

  def remove(team:Team):Option[Team]={
    teams -= team
    Some(team)
  }

}