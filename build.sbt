
cancelable in Global := true

lazy val common = Seq(
  scalaVersion := "2.11.7",
  fork in run := true,
  maxErrors := 10,
  organization := "com.joprice.highlighter",
  version := "0.2-SNAPSHOT",
  libraryDependencies += "com.novocode" % "junit-interface" % "0.10" % Test,
  javacOptions in (Compile, compile) ++= Seq(
    "-source", "1.7", 
    "-target", "1.7"
  )
)

lazy val core = project
  .settings(common:_*)

lazy val awt = project
  .dependsOn(core)
  .settings(common:_*)

lazy val root = project.in(file("."))
  .aggregate(core, awt)
  .settings(
    publish := {},
    publishLocal := {}
  )
