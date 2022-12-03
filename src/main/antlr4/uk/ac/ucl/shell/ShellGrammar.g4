grammar ShellGrammar;

/*
 * Parser Rules
 */

shell : sequence?;
sequence : command (';' command)*;
command : pipe | call;
pipe : call ('|' call)+;

call : WS* (redirection WS*)* argument (WS* atom)* WS*;

atom : redirection | argument;
argument : (quoted | UNQUOTED)+;
redirection : '<' WS* argument | '>' WS* argument;
quoted : SINGLEQUOTED | DOUBLEQUOTED | BACKQUOTED;

/*
 * Lexer Rules
 */

DOUBLEQUOTED : UNQUOTED* '"' (~["`\n]* BACKQUOTED? ~["`\n]*) '"' UNQUOTED*;
SINGLEQUOTED : UNQUOTED* '\'' ~['\n]* '\'' UNQUOTED*;
BACKQUOTED : UNQUOTED* '`' ~[`\n]* '`' UNQUOTED*;
UNQUOTED : ~[ \t\n'`";><|]+;
WS : (' ' | '\t') -> channel(HIDDEN);
