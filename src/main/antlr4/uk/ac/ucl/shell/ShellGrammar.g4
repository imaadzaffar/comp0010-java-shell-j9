grammar ShellGrammar;

/*
 * Parser Rules
 */

shell : sequence? EOF;
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

SINGLEQUOTED : '\'' ~['\n]+ '\'';
DOUBLEQUOTED : '"' (~["`\n]+ | BACKQUOTED)* '"';
BACKQUOTED :'`' ~[`\n]+ '`';
UNQUOTED : ~[ \t\n'`";><|]+;
WS : (' ' | '\t') -> channel(HIDDEN);