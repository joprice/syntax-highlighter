
cancelable in Global := true

lazy val common = Seq(
  fork in run := true,
  maxErrors := 10,
  organization := "com.joprice.highlighter",
  //publishMavenStyle := true,
  //crossPaths := false,
  //autoScalaLibrary := false,
  version := "0.1-SNAPSHOT",
  libraryDependencies += "com.novocode" % "junit-interface" % "0.10" % Test,
  javacOptions in (Compile, compile) ++= Seq(
    "-source", "1.7", 
    "-target", "1.7"
  )
  //javacOptions in doc := Seq("source", "1.7")
)

lazy val core = project
  .settings(common:_*)

lazy val awt = project
  .dependsOn(core)
  .settings(common:_*)


