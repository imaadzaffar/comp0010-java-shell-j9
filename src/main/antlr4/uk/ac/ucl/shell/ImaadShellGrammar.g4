grammar ImaadShellGrammar;

/*
 * Parser Rules
 */

command : pipe | seq | call;
pipe : call '|' call | pipe '|' call;
seq  : command ';' command;
call : ( nonKeyword | quoted ) *;

call : [ whitespace ] [ redirection whitespace ]* argument [ whitespace atom ]* [ whitespace ];
atom : redirection | argument;
argument : ( quoted | unquoted )+;
redirection : '<' [ whitespace ] argument
    | '>' [ whitespace ] argument;

quoted : singleQuoted | doubleQuoted | backquoted;
singleQuoted : '\'' nonNewline and nonSingleQuote '\'';
backquoted : '`' nonNewline and nonBackquote '`';
doubleQuoted : '"' ( backquoted | doubleQuoteContent ) * '"';

/*
 * Lexer Rules
 */

SINGLEQUOTED : '\'' ~['\n]+ '\'';
DOUBLEQUOTED : '"' (~["`\n]+ | BACKQUOTED)* '"';
BACKQUOTED :'`' ~[`\n]+ '`';
UNQUOTED : ~[ \t\n'`";><|]+;
WS : (' ' | '\t') -> channel(HIDDEN);