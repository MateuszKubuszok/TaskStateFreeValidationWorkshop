# Nice Scalaz (and Cats) monads you want to use: State, Free, Task, as well as Validation

Workshops will require SBT, some nice editor IDE and Git.

## Slides

are placed under `slides/index.html`. I encourage you to open them and follow them on your computer as it will be easier for you to find some useful information during the exercises.

## Exercises and solutions

### Validation exercise

Files are in `validation` module inside `io.scalawave.workshop.validation` package.

You can validate your solutions by running:

    sbt validation/test

Feel free to use both hints from the code as well as cheatsheet from the slides.

Once all missing implementations are in place you can also try running main function with:

    sbt validation/run

If you had problem with the exercise you can preview solutions by merging solution branch:

    git merge validation-exercise-solution

### Task exercise

Files are in `task` module inside `io.scalawave.workshop.task` package.

You can validate your solutions by running:

    sbt task/test

Feel free to use both hints from the code as well as cheatsheet from the slides.

Once all missing implementations are in place you can also try running main function with:

    sbt task/run

If you had problem with the exercise you can preview solutions by merging solution branch:

    git merge task-exercise-solution

### State exercise

Files are in `state` module inside `io.scalawave.workshop.state` package.

You can validate your solutions by running:

    sbt state/test

Feel free to use both hints from the code as well as cheatsheet from the slides.

Once all missing implementations are in place you can also try running main function with:

    sbt state/run

If you had problem with the exercise you can preview solutions by merging solution branch:

    git merge state-exercise-solution

### Free exercises

Files are in `free` module inside `io.scalawave.workshop.free` package.

You can validate your solutions by running:

    sbt free/test

Feel free to use both hints from the code as well as cheatsheet from the slides.

Once all missing implementations (after Free exercise 2) are in place you can also try running main function with:

    sbt state/run

if you uncomment some interesting pieces of code.

If you had problem with the exercise you can preview solutions by merging solution branch:

    git merge free-exercise-1-solution

and

    git merge free-exercise-2-solution

### Final demo

It assumes that all exercises were solved and some interesting classes were uncommented (that will be explained on the workshops). It's just for showing how all of the ideas we learn about nicely fit together.
