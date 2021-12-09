addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.11")

addSbtPlugin("org.foundweekends.giter8" % "sbt-giter8-scaffold" % "0.13.1")


addSbtPlugin("com.typesafe.sbt" % "sbt-twirl" % "1.5.1")


lazy val root = (project in file("."))
  .enablePlugins(SbtPlugin)