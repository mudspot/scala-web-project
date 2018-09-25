name := """scala-web-project"""
version := "1.0-SNAPSHOT"
scalaVersion := "2.12.6"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
pipelineStages := Seq(digest)

libraryDependencies ++= Seq(
  jdbc,
  ehcache,
  ws,
  guice,
  "com.typesafe.play" %% "play-functional" % "2.7.0-M1"
)

//resolvers += "sonatype-snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"