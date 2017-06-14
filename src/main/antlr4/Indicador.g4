grammar Indicador;

@header {
package antlr4;
}

asign	: ind '=' expr ;

ind	: ID ;

expr 	: expr ('*' | '/') expr	# MultDiv
	| expr ('+' | '-') expr	# SumaResta
	| ID			# valor
	| NUMERO		# num
	| '(' expr ')'		# parentesis
	;

MUL	: '*' ;
DIV	: '/' ;
SUM	: '+' ;
RES	: '-' ;

ID	: LETRA+ (' 'LETRA+)* ;

LETRA	: ('a'..'z'|'A'..'Z') ;

NUMERO	: [0-9]+ ;

WS	: [ \t\r\n]+ -> skip ;