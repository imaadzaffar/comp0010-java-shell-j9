# Commands

## Call Command

A call command executes an application with specified inputs. For example,

    grep "Interesting String" < text1.txt > result.txt

finds all lines of the file `text1.txt` that contain the string `Interesting String` as a substring and saves them in the file `result.txt`.

COMP0010 Shell uses the following grammar to parse call commands:

    <call> ::= [ <whitespace> ] [ <redirection> <whitespace> ]* <argument> [ <whitespace> <atom> ]* [ <whitespace> ]
    <atom> ::= <redirection> | <argument>
    <argument> ::= ( <quoted> | <unquoted> )+
    <redirection> ::= "<" [ <whitespace> ] <argument>
                    | ">" [ <whitespace> ] <argument>

In this definition, `<whitespace>` is one or several tabs or spaces; the `<unquoted>` part of an `<argument>` can include any characters except for whitespace characters, quotes, newlines, semicolons `;`, vertical bar `|`, less than `<` and greater than `>`.

A call command is evaluated in the following order:

1. command substitution is performed (see command substitution);
2. the command is split into arguments corresponding to the `<argument>` non-terminal. Note that one backquoted argument can produce several arguments after command substitution. The quotes (`'`, `"` and ``` ` ```) that form the `<quoted>` non-terminals are removed;
3. the filenames are expanded (see globbing);
4. the application name is resolved (the first `<argument>` without a redirection operator is the application to be called);
5.	the specified application is executed.

Before executing an application, COMP0010 Shell interprets the [redirections](https://www.gnu.org/software/bash/manual/html_node/Redirections.html) commands in the following way:

1. opens the file following the `<` symbol for input redirection;
2. opens the file following the `>` symbol for output redirection;
3. if several files are specified for input or output redirection (e.g. `> a.txt > b.txt`), throws an exception;
4. if the file specified for input redirection does not exist, throws an exception;
5. if the file specified for output redirection does not exist, creates it.

After that, COMP0010 Shell runs the specified application, supplying given command line arguments and redirection streams.

## Sequence Command

Executes a sequence of commands separated by semicolons. For example,

    cd articles; cat text1.txt

changes the current directory to `articles`, then displays the content of the file `text1.txt`.

The syntax of this command is the following:

    <seq> ::= <command> ";" <command>

It runs the first command; after the first command terminates, runs the second command. If an exception is thrown during the execution of the first command, the execution if the whole command must be terminated.

## Pipeline Command

The output of each command in a [pipeline](https://www.gnu.org/software/bash/manual/html_node/Pipelines.html) is connected via a pipe to the input of the next command. For example,

    cat articles/text1.txt | grep "Interesting String"

finds the line of the file `articles/text1.txt` that contain `Interesting String` as a substring. In this command, the output of `cat` is passed as the input to `grep` via a pipe.

Pipiline is expressed using a left-associative operator `|` that binds a set of call commands into a chain:

    <pipe> ::= <call> "|" <call> |
               <pipe> "|" <call>

The operator `|` connects stdout of the left subcommand to stdin of the right subcommand.

## Globbing

Globbing, also known as [filename expansion](https://www.gnu.org/software/bash/manual/html_node/Filename-Expansion.html), allows using patterns to capture one or several filenames. For example,

    cat articles/*

concatenates all files in the directory `articles`.

The symbol `*` (asterisk) in an unquoted part of an argument is interpreted as globbing.

For each argument `ARG` that contains unquoted `*` (asterisk), COMP0010 Shell performs the following:

1. collects all paths to existing files and directories such that these paths can be obtained by replacing all the unquoted asterisk symbols in `ARG` by some (possibly empty) sequences of non-slash characters.
2. if there are no such paths, leaves `ARG` unchanges.
3. if there are such paths, replaces `ARG` with a list of these path separated by spaces.

Globbing is performed after argument splitting, but it produces several command line arguments if several matching paths are found.

## Command Substitution

[Command substitution](https://www.gnu.org/software/bash/manual/html_node/Command-Substitution.html) allows the output of a command to replace the command itself. For example,

    wc -l `find -name '*.java'`

finds all files whose names end with `.java`, and counts the number of lines in these files.

A part of a call command surrounded by backquotes ``` ` ``` is interpreted as command substitution iff the backquotes are not inside single quotes (see the non-terminal `<backquoted>`).

For each part `SUBCMD` of the call command `CALL` surrounded by backquotes:

1. `SUBCMD` is evaluated as a separate shell command yielding the output `OUT`.
2. `SUBCMD`, together with the backquotes, is substituted in `CALL` with `OUT`. After substitution, the symbols in `OUT` are interpreted the following way:
    - whitespace characters are used for argument splitting. Since our shell does not support multi-line commands, newlines in `OUT` are replaced with spaces;
    - other characters (including quotes) are not interpreted during the next parsing step as special characters.
3. the modified `CALL` is evaluated. Note that there cannot be nested/recursive command substitutions.

Command substitution is performed after command-level parsing but before argument splitting.
