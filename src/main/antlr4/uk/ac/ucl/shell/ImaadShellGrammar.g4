grammar ImaadShellGrammar;

/*
 * Parser Rules
 */

shell: command (';' command)*;
command : pipe | call;
pipe : call ('|' call)+;

call : WHITESPACE* ( redirection WHITESPACE )* argument ( WHITESPACE atom )* WHITESPACE*;
atom : redirection | argument;
argument : ( quoted | nonKeyword )+;
redirection  : '<' WHITESPACE argument #inputRedirection
             | '>' WHITESPACE argument #outputRedirection
             ;

quoted : singleQuoted | doubleQuotedAux | backQuoted;

singleQuoted : SINGLE_QUOTED;

doubleQuotedAux : doubleQuotedFinal | doubleQuotedSub;
doubleQuotedFinal : DOUBLE_QUOTED;
doubleQuotedSub : '"' (nonKeyword)* WHITESPACE* backQuoted WHITESPACE* (nonKeyword)* '"';

backQuoted : '`' subCommand '`';
subCommand : argument (WHITESPACE argument)*;

nonKeyword : NON_KEYWORD;

/*
 * Lexer Rules
 */

DOUBLE_QUOTED : '"' ~[\n'`]+ '"';
SINGLE_QUOTED : '\'' ~[\n']+ '\'';
//BACKQUOTED : '`' ~[\n`]+ '`';

NON_KEYWORD : ~[ \t\n'`";><|]+;
WHITESPACE : [ \t\n] -> channel(HIDDEN);