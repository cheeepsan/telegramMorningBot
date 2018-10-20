name := "slavyaneBot"

version := "0.1"

scalaVersion := "2.12.7"

// Core with minimal dependencies, enough to spawn your first bot.
libraryDependencies += "com.bot4s" %% "telegram-core" % "4.0.0-RC1"

// Extra goodies: Webhooks, support for games, bindings for actors.
libraryDependencies += "com.bot4s" %% "telegram-akka" % "4.0.0-RC1"
libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.20.0"
libraryDependencies += "com.h2database" % "h2" % "1.4.192"
libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.2.3",
  "org.slf4j" % "slf4j-nop" % "1.6.4"
)

enablePlugins(JavaAppPackaging)

mainClass in Compile := Some("bot.Main")
