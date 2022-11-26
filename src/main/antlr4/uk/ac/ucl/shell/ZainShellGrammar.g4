grammar ZainShellGrammar;

/*
 * Parser Rules
 */

command : commandAux (';' commandAux)*;

commandAux : variableAssignment | call | pipe ;

pipe : (call '|' call) ('|' call)*;

variableAssignment : VAR_ASSIGN (substitution | nonSpecialNoSpace | singleQuotedCommand | doubleQuotedCommand) (WHITESPACE variableAssignment)*;

call : callAux #C
    | callAux leftRedirection  #CL
    | callAux rightRedirection #CR
    | callAux appendRedirection #CA
    | leftRedirection callAux #LC
    | rightRedirection callAux #RC
    | appendRedirection callAux #AC
    | leftRedirection rightRedirection callAux #LRC
    | leftRedirection appendRedirection callAux #LAC
    | leftRedirection callAux rightRedirection #LCR
    | leftRedirection callAux appendRedirection #LCA
    | callAux leftRedirection rightRedirection #CLR
    | callAux leftRedirection appendRedirection #CLA
    | callAux rightRedirection leftRedirection #CRL
    | callAux appendRedirection leftRedirection #CAL
    | rightRedirection callAux leftRedirection #RCL
    | rightRedirection leftRedirection callAux #RLC
    | appendRedirection callAux leftRedirection #ACL
    | appendRedirection leftRedirection callAux #ALC
    ;

leftRedirection : LEFTREDIRECTION;

rightRedirection : RIGHTREDIRECTION;

appendRedirection : APPENDREDIRECTION;

callAux : (doubleQuotedCommand | singleQuotedCommand | substitution | variableDollar | (nonSpecial | nonSpecialNoSpace))*;

variableDollar : VAR_CALL;

doubleQuotedCommand : doubleQuoted | doubleQuotedSub;

singleQuotedCommand : singleQuoted | singleQuotedSub;

doubleQuotedSub : '"' (nonSpecial | nonSpecialNoSpace)* (substitution)+ (nonSpecial | nonSpecialNoSpace)* '"';

singleQuotedSub : '\'' (nonSpecial | nonSpecialNoSpace)* (substitution)+ (nonSpecial | nonSpecialNoSpace)* '\'';

substitution : BACKQUOTE substitutionContent (';' substitutionContent)* BACKQUOTE;

substitutionContent : (singleQuoted | doubleQuoted | variableDollar | (nonSpecial | nonSpecialNoSpace))*;

singleQuoted : SINGLEQUOTED;

doubleQuoted : DOUBLEQUOTED;

nonSpecial : NONSPECIAL;

nonSpecialNoSpace : NONSPECIALNOSPACE;


/*
 * Lexer Rules
 */

LEFTREDIRECTION : [ \n\t\r]* '<' [ \n\t\r]* (('a'..'z' | 'A'..'Z' | '0'..'9') ('a'..'z' | 'A'..'Z' | '0'..'9' | '-' | '_' | [ \n\t\r])*'/')* ('a'..'z' | 'A'..'Z' | '0'..'9') ('a'..'z' | 'A'..'Z' | '0'..'9' | '-' | '_' | [ \n\t\r])* '.'('a'..'z' | 'A'..'Z')+;
RIGHTREDIRECTION : [ \n\t\r]* '>' [ \n\t\r]* (('a'..'z' | 'A'..'Z' | '0'..'9') ('a'..'z' | 'A'..'Z' | '0'..'9' | '-' | '_' | [ \n\t\r])*'/')* ('a'..'z' | 'A'..'Z' | '0'..'9') ('a'..'z' | 'A'..'Z' | '0'..'9' | '-' | '_' | [ \n\t\r])* '.'('a'..'z' | 'A'..'Z')+;
APPENDREDIRECTION : [ \n\t\r]* '>>' [ \n\t\r]* (('a'..'z' | 'A'..'Z' | '0'..'9') ('a'..'z' | 'A'..'Z' | '0'..'9' | '-' | '_' | [ \n\t\r])*'/')* ('a'..'z' | 'A'..'Z' | '0'..'9') ('a'..'z' | 'A'..'Z' | '0'..'9' | '-' | '_' | [ \n\t\r])* '.'('a'..'z' | 'A'..'Z')+;

VAR_ASSIGN : [ \n\t\r]* ('a'..'z' | 'A'..'Z') ('a'..'z' | 'A'..'Z' | '0'..'9' | '-' )* '=';
VAR_CALL : '$'('a'..'z' | 'A'..'Z') ('a'..'z' | 'A'..'Z' | '0'..'9' | '-' )*;
NONSPECIAL : ~['";`|<>$=]* [ \n\t\r]+ ~['";`|<>$]*;
BACKQUOTE : '`';
DOUBLEQUOTED : '"'~["`]*'"';
SINGLEQUOTED : '\''~['`]*'\'';
NONSPECIALNOSPACE : ~['";`|<>$= \n\t\r]+;
WHITESPACE : [ \n\t\r]+;
