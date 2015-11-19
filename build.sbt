
cancelable in Global := true

lazy val common = Seq(
  fork in run := true,
  maxErrors := 10,
  organization := "com.joprice.highlighter",
  publishMavenStyle := true,
  crossPaths := false,
  autoScalaLibrary := false,
  version := "0.1-SNAPSHOT",
  libraryDependencies += "com.novocode" % "junit-interface" % "0.10" % Test
)

lazy val core = project
  .settings(common:_*)

lazy val awt = project
  .dependsOn(core)
  .settings(common:_*)


