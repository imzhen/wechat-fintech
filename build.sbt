name := "WechatFintech"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  jdbc, cache, ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test,
  "org.scala-lang.modules" %% "scala-xml" % "1.0.6"
)


