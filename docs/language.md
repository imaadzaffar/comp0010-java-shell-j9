# Language

A shell can be considered as a language for executing commands. COMP0010 Shell is an interactive shell, that is it parses user's command lines and executes the specified commands in a loop, known also as [REPL]((https://en.wikipedia.org/wiki/Read%E2%80%93eval%E2%80%93print_loop)), that

1. prints a prompt message;
2. parses user's command;
3. interprets user's command, runs the specified applications or built-in commands;
4. prints the output;
5. goes to step 1.

In a shell, applications play a role similar to that of functions in programming languages like Java and Python. A command line application in UNIX can be considered as a black-box with two inputs ([command line arguments](https://en.wikipedia.org/wiki/Command-line_interface#Arguments) and [stdin](https://en.wikipedia.org/wiki/Standard_streams#Standard_input_(stdin))) and three outputs ([stdout](https://en.wikipedia.org/wiki/Standard_streams#Standard_output_(stdout)), [stderr](https://en.wikipedia.org/wiki/Standard_streams#Standard_error_(stderr)) and [exit code](https://en.wikipedia.org/wiki/Exit_status)). Command line arguments is a list of strings; stdin, stdout and stderr are sequences of bytes; exit code is a number. In COMP0010 Shell, exceptions are used instead of stderr and exit codes.

![Applications in UNIX and COMP0010 Shell](apps.svg)

In this document, the syntax of COMP0010 Shell is specified using [BNF](https://en.wikipedia.org/wiki/Backus%E2%80%93Naur_form) notation.

## Command Line Parsing

A command may contain several subcommands. When COMP0010 Shell receives a command line, it

1. parses the command line on the command level. It recognizes three kind of commands: call command, sequence command, and pipe command;
2. evaluates the recognized commands in the proper order.

Step 1 uses the following grammar:

    <command> ::= <pipe> | <seq> | <call>
    <pipe> ::= <call> "|" <call> | <pipe> "|" <call>
    <seq>  ::= <command> ";" <command>
    <call> ::= ( <non-keyword> | <quoted> ) *

A non-keyword character is any character except for newlines, single quotes, double quotes, backquotes, semicolons `;` and vertical bars `|`. The non-terminal `<quoted>` is described below.

## Quoting

[Quoting](https://www.gnu.org/software/bash/manual/html_node/Quoting.html) is used to remove the special meaning of certain characters or words to the shell.

To pass several arguments to an application, we can separate them with spaces:

    echo hello world

In this example, `echo` gets two command line arguments: `hello` and `world`. In order to pass `hello world` as a single argument, we can wrap it by quotes, so that the interpretation of the space character as a separator symbol is disabled:

    echo "hello world"

In this case, `echo` receives `hello world` as a single argument.

COMP0010 Shell supports three kinds of quotes: single quotes ```'```, double quotes ```"``` and backquotes ``` ` ```. The first and the second ones are used to disable interpretation of special characters, the last one is used to make command substitution.

COMP0010 Shell uses the following grammar to parse quoted strings:

    <quoted> ::= <single-quoted> | <double-quoted> | <backquoted>
    <single-quoted> ::= "'" <non-newline and non-single-quote> "'"
    <backquoted> ::= "`" <non-newline and non-backquote> "`"
    <double-quoted> ::= """ ( <backquoted> | <double-quote-content> ) * """

where `<double-quote-content>` can contain any character except for newlines, double quotes and backquotes.

Note that the rule for double quotes is different from single quotes: double quotes do not disable interpretation of backquotes. For example, in the following command:

    echo "this is space: `echo " "`"

the outer `echo` receives one argument rather than two.

Note that compared with e.g. Bash, COMP0010 Shell does not have character escaping.
