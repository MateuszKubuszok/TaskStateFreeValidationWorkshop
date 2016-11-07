import sbt._
import Settings._

scalaVersion in ThisBuild := scalaVersionUsed

lazy val root = project.root
  .setName("Workshop excercises")
  .setDescription("Task, State, Free, Validation workshop")
  .setInitialCommand("_")
  .configureRoot
  .aggregate(common, warmup, task, state, free, validation)

lazy val common = project.from("common")
  .setName("workshop-common")
  .setDescription("Common utils used in exercises")
  .configureModule

lazy val warmup = project.from("warmup")
  .setName("workshop-warmup")
  .setDescription("Warmup: implicits, typeclasses and monads")
  .setInitialCommand("warmup._")
  .configureModule
  .settings(mainClass in (Compile, run) := Some("io.scalawave.workshop.warmup.Main"))

lazy val task = project.from("task")
  .setName("workshop-task")
  .setDescription("Task monad excerices")
  .setInitialCommand("task._")
  .configureModule
  .settings(mainClass in (Compile, run) := Some("io.scalawave.workshop.task.Main"))

lazy val state = project.from("state")
  .setName("workshop-state")
  .setDescription("State monad excercies")
  .setInitialCommand("state._")
  .configureModule
  .settings(mainClass in (Compile, run) := Some("io.scalawave.workshop.state.Main"))

lazy val free = project.from("free")
  .setName("workshop-free")
  .setDescription("Free monad excercies")
  .setInitialCommand("free._")
  .configureModule
  .settings(mainClass in (Compile, run) := Some("io.scalawave.workshop.free.Main"))

lazy val validation = project.from("validation")
  .setName("workshop-validation")
  .setDescription("Validation monad excercies")
  .setInitialCommand("validation._")
  .configureModule
  .settings(mainClass in (Compile, run) := Some("io.scalawave.workshop.validation.Main"))
