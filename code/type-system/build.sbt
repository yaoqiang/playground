import Dependencies._

ThisBuild / scalaVersion     := "2.13.5"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "type-system",
    libraryDependencies ++= Seq(
      compilerPlugin(
        "org.typelevel" %% "kind-projector" % "0.11.3"
          cross CrossVersion.full
      ),
      "org.typelevel" %% "cats-core"       % "2.6.1",
      "org.typelevel" %% "cats-effect"     % "3.1.1",
      "org.typelevel" %% "cats-mtl"        % "1.2.1",
      "co.fs2"        %% "fs2-core"        % "3.0.3",
      "dev.optics"    %% "monocle-core"    % "3.0.0-RC2",
      "dev.optics"    %% "monocle-macro"   % "3.0.0-RC2",
      "io.estatico"   %% "newtype"         % "0.4.4",
      "eu.timepit"    %% "refined"         % "0.9.25",
      "eu.timepit"    %% "refined-cats"    % "0.9.25",
      "tf.tofu" %% "derevo-cats"           % "0.12.5",
      "tf.tofu" %% "derevo-cats-tagless"   % "0.12.5",
      "tf.tofu" %% "derevo-circe-magnolia" % "0.12.5",
      "tf.tofu" %% "tofu-core-higher-kind" % "0.10.2"
    ),
    scalacOptions ++= Seq("-Ymacro-annotations", "-Wconf:cat=unused:info")
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
