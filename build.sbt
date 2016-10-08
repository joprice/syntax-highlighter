
//cancelable in Global := true

scalaVersion in ThisBuild := "2.11.8"

lazy val common = Seq(
  fork in run := true,
  maxErrors := 10,
  organization := "com.joprice.highlighter",
  version := "0.2-SNAPSHOT",
  libraryDependencies ++= Seq(
    "com.novocode" % "junit-interface" % "0.10" % Test,
    "org.scalatest" %% "scalatest" % "2.2.6" % Test
  ),
  javacOptions in (Compile, compile) ++= Seq(
    "-source", "1.7", 
    "-target", "1.7"
  )
)

lazy val core = project
  .settings(common:_*)

lazy val root = project.in(file("."))
  .aggregate(core)
  .settings(
    publish := {},
    publishLocal := {}
  )
