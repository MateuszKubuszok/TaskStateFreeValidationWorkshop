import sbt._

import Dependencies._

object Dependencies {

  // scala version
  val scalaVersion = "2.11.8"

  // resolvers
  val resolvers = Seq(
    Resolver sonatypeRepo "public"
  )

  // scalaz
  val scalaz           = "org.scalaz" %% "scalaz-core" % "7.2.6"
  val scalazConcurrent = "org.scalaz" %% "scalaz-concurrent" % "7.2.6"

  // cats
  val cats = "org.typelevel" %% "cats" % "0.7.2"

  // testing
  val spec2Core  = "org.specs2" %% "specs2-core" % "3.8.5.1"
  val spec2JUnit = "org.specs2" %% "specs2-junit" % "3.8.5.1"
}

trait Dependencies {

  val scalaVersionUsed = scalaVersion

  // resolvers
  val commonResolvers = resolvers

  val mainDeps = Seq(scalaz, scalazConcurrent, cats)

  val testDeps = Seq(spec2Core, spec2JUnit)

  implicit class ProjectRoot(project: Project) {

    def root: Project = project in file(".")
  }

  implicit class ProjectFrom(project: Project) {

    private val commonDir = "modules"

    def from(dir: String): Project = project in file(s"$commonDir/$dir")
  }
}
