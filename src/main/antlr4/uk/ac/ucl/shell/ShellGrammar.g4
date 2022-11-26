grammar ShellGrammar;
options { contextSuperClass=ShellRuleContext; }

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

DOUBLEQUOTED : '"' (~["`\n]* BACKQUOTED? ~["`\n]*) '"';
SINGLEQUOTED : '\'' ~['\n]* '\'';
BACKQUOTED :'`' ~[`\n]* '`';
UNQUOTED : ~[ \t\n'`";><|]+;
WS : (' ' | '\t') -> channel(HIDDEN);
