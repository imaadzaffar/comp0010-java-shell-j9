![Open in Codespaces](https://classroom.github.com/assets/open-in-codespaces-abfff4d4e15f9e1bd8274d9a39a0befe03a0632bb0f153d0ec72ff541cedbe34.svg)
# COMP0010 Shell

COMP0010 Shell is a [shell](https://en.wikipedia.org/wiki/Shell_(computing)) created for educational purposes. 
Similarly to other shells, it provides a [REPL](https://en.wikipedia.org/wiki/Read%E2%80%93eval%E2%80%93print_loop), an interactive environment that allows users to execute commands.

## Language

COMP0010 Shell has a simple language for specifying commands that resembles [Bash](https://en.wikipedia.org/wiki/Bash_(Unix_shell)). This language allows, for example, calling applications and connecting the output of one application to the input of another application through a [pipeline](https://en.wikipedia.org/wiki/Pipeline_(Unix)). We used ANTLR4 to do all the lexing/parsing of command line inputs.

Learn more about the [language](docs/language.md).

Learn more about the [commands](docs/commands.md).

## Applications

COMP0010 Shell also provides its own implementations of widely-used UNIX applications for file system and text manipulation: [echo](https://en.wikipedia.org/wiki/Echo_(command)), [ls](https://en.wikipedia.org/wiki/Ls), [cat](https://en.wikipedia.org/wiki/Cat_(Unix)), etc.

Learn more about the [applications](docs/applications.md).

## Instructions

To execute/test COMP0010 Shell, there are multiple terminal commands you can use.

Learn more about the [instructions](docs/instructions.md).
