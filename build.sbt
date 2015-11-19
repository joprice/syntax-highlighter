

lazy val core = project

lazy val awt = project.dependsOn(core)

fork in run := true

