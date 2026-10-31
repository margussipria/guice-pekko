name := "guice-pekko"
description := "Library for injecting Apache Pekko actors with Google Guice"

homepage := Some(url("https://github.com/margussipria/guice-pekko"))
licenses := Seq("Apache License, Version 2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0.txt"))

organization := "eu.sipria.inject.pekko"

version := "1.0.0"

scalaVersion := "2.12.21"
crossScalaVersions := Seq("2.12.21", "2.13.18", "3.3.9")

scalacOptions := Seq(
  "-deprecation",
  "-language:postfixOps"
)

val PekkoVersion = "1.7.1"
val GuiceVersion = "6.0.0"
val ScalaTestVersion = "3.2.20"

libraryDependencies ++= Seq(
  "net.codingwell" %% "scala-guice" % "6.0.0",
  "com.google.inject" % "guice" % GuiceVersion,
  "com.google.inject.extensions" % "guice-assistedinject" % GuiceVersion,

  "org.apache.pekko" %% "pekko-actor" % PekkoVersion,
  "org.apache.pekko" %% "pekko-testkit" % PekkoVersion % Test,

  "org.scalatest" %% "scalatest" % ScalaTestVersion % Test
)

developers := List(
  Developer("margussipria", "Margus Sipria", "margus+guice-pekko@sipria.fi", url("https://github.com/margussipria"))
)

scmInfo := Some(ScmInfo(
  url("https://github.com/margussipria/guice-pekko"),
  "scm:git:https://github.com/margussipria/guice-pekko.git",
  Some("scm:git:git@github.com:margussipria/guice-pekko.git")
))
