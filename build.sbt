name := "wechatfintech"
version := "1.0-SNAPSHOT"
scalaVersion := "2.11.8"
organization := "imzhen"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, sbtdocker.DockerPlugin)

libraryDependencies ++= Seq(
  jdbc, cache, ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test,
  "org.scala-lang.modules" %% "scala-xml" % "1.0.6"
)

imageNames in docker := Seq(ImageName(s"${organization.value}/${name.value}:${version.value}"))

dockerfile in docker := {
  val executeName = s"${name.value}-${version.value}"
  val entryFile = "docker-entrypoint.bash"

  new Dockerfile {
    from("gcr.io/google_appengine/openjdk")
    copy(target.value / "universal" / s"$executeName.tgz", s"/$executeName.tgz")
    run("tar", "--touch", "-xvf", s"$executeName.tgz")
    workDir(s"/$executeName")
    run("chmod", "755", s"/$entryFile")
    entryPoint(s"/$entryFile")
    cmd(s"bin/${name.value}", "-Dconfig.file=conf/prod.conf", "-Dhttp.port=8080")
  }
}

addCommandAlias("buildAll", ";universal:packageZipTarball;docker")
