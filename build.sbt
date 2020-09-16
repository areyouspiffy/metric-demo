val circeVersion = "0.12.0"
val Http4sVersion = "0.21.7"
val LogbackVersion = "1.2.3"
val Specs2Version = "4.9.3"

lazy val root = (project in file("."))
  .settings(
    organization := "com.brianmowen",
    name := "metric_demo",
    version := "0.0.1",
    scalaVersion := "2.13.1",
    Global / cancelable := false,
    libraryDependencies ++= Seq(
      "com.github.pureconfig" %% "pureconfig"               % "0.12.3",
      "com.beachape"          %% "enumeratum"               % "1.5.13",
      "io.circe"              %% "circe-core"               % circeVersion,
      "io.circe"              %% "circe-generic"            % circeVersion,
      "io.circe"              %% "circe-parser"             % circeVersion,
      "io.circe"              %% "circe-literal"            % circeVersion,
      "org.http4s"            %% "http4s-blaze-server"      % Http4sVersion,
      "org.http4s"            %% "http4s-blaze-client"      % Http4sVersion,
      "org.http4s"            %% "http4s-circe"             % Http4sVersion,
      "org.http4s"            %% "http4s-client"            % Http4sVersion,
      "org.http4s"            %% "http4s-dsl"               % Http4sVersion,
      "org.http4s"            %% "http4s-async-http-client" % Http4sVersion,
      "org.http4s"            %% "http4s-twirl"             % Http4sVersion,
      "org.tpolecat"          %% "natchez-log"              % "0.0.12",
      "org.specs2"            %% "specs2-core"              % Specs2Version % "test",
      "ch.qos.logback"        % "logback-classic"           % LogbackVersion,
      "org.scalatest"         %% "scalatest"                % "3.1.1" % "test",
      "io.chrisdavenport"     %% "log4cats-slf4j"           % "1.1.1"
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.10.3"),
    addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1"),
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-language:postfixOps",
      "-Xlint",
      "-Xlog-free-terms",
      "-Xlog-free-types",
      "-language:implicitConversions",
      "-language:higherKinds",
      "-language:existentials",
      "-language:postfixOps",
      "-Ywarn-dead-code",
      "-Ywarn-numeric-widen",
      "-unchecked",
      "-encoding",
      "UTF-8",
      "-language:experimental.macros",
      "-Xfatal-warnings",
      "-Ywarn-numeric-widen",
      "-Ywarn-value-discard"
    )
  )
