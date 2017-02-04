import sbt._
import Settings._

scalaVersion in ThisBuild := scalaVersionUsed

lazy val root = project.root
  .setName("Workshop exercises")
  .setDescription("Task, State, Free, Validation workshop")
  .setInitialCommand("_")
  .configureRoot
  .aggregate(common, validation, state, free, task, `summary-cats`, `summary-scalaz`)

lazy val common = project.from("common")
  .setName("workshop-common")
  .setDescription("Common utils used in exercises")
  .configureModule

lazy val validation = project.from("validation")
  .setName("workshop-validation")
  .setDescription("Validation monad exercises")
  .setInitialCommand("validation._")
  .configureModule
  .dependsOn(common)
  .settings(mainClass in (Compile, run) := Some("workshop.validation.Main"))

lazy val state = project.from("state")
  .setName("workshop-state")
  .setDescription("State monad exercises")
  .setInitialCommand("state._")
  .configureModule
  .dependsOn(common)
  .settings(mainClass in (Compile, run) := Some("workshop.state.Main"))

lazy val free = project.from("free")
  .setName("workshop-free")
  .setDescription("Free monad exercises")
  .setInitialCommand("free._")
  .configureModule
  .dependsOn(common)
  .settings(mainClass in (Compile, run) := Some("workshop.free.Main"))

lazy val task = project.from("task")
  .setName("workshop-task")
  .setDescription("Task monad exercises")
  .setInitialCommand("task._")
  .configureModule
  .dependsOn(common)
  .settings(mainClass in (Compile, run) := Some("workshop.task.Main"))

lazy val `summary-cats` = project.from("summary-cats")
  .setName("workshop-summary-cats")
  .setDescription("Demo of a complete Cats program")
  .setInitialCommand("summary._")
  .configureModule
  .dependsOn(common, validation, state, free, task)
  .settings(mainClass in (Compile, run) := Some("workshop.summary.CatsMain"))

lazy val `summary-scalaz` = project.from("summary-scalaz")
  .setName("workshop-summary-scalaz")
  .setDescription("Demo of a complete Scalaz program")
  .setInitialCommand("summary._")
  .configureModule
  .dependsOn(common, validation, state, free, task)
  .settings(mainClass in (Compile, run) := Some("workshop.summary.ScalazMain"))
