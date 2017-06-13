grammar Indicador;

@header {
package antlr4;
}

asign	: ind '=' expr;

ind	: ID;

expr 	: expr (SUM | RES) expr
	| expr (MUL | DIV) expr
	| val
	| constant
	| '(' expr ')';

val	: ID;

SUM	: '+';

RES	: '-';

MUL	: '*';

DIV	: '/';

ID	: LETRA+ (' 'LETRA+)*;

LETRA	: ('a'..'z'|'A'..'Z');

constant: NUMERO;

NUMERO	: [0-9]+;

WS	: [ \t\r\n]+ -> skip;