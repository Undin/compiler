grammar Grammar;

module
    :   functionDefinition+
    ;

functionDefinition
    :   'fn' prototype block
    ;

prototype
    :   Identifier '(' typedArguments? ')' '->' type
    ;

typedArguments
    :   typedArgument (',' typedArgument)*
    ;

typedArgument
    :   Identifier ':' type
    ;

type
    :   Identifier
    ;

statement
    :   block
    |   exprStatement
    |   assign
    |   assignDeclaration
    |   ifStatement
    |   ifElseStatement
    |   whileStatement
    |   returnStatement
    |   print
    |   read
    ;

block
    :   '{' statement* '}'
    ;

exprStatement
    :   expression ';'
    ;

assign
    :   Identifier '=' expression ';'
    ;

assignDeclaration
    :   Identifier ':' type ('=' expression)? ';'
    ;

ifStatement
    :   'if' '(' expression ')' block
    ;

ifElseStatement
    :   'if' '(' expression ')' thenBlock=block 'else' elseBlock=block
    ;

whileStatement
	:	'while' '(' expression ')' block
	;

returnStatement
    :   'return' expression ';'
    ;

print
    :   name=('print' | 'println') '(' expression ')' ';'
    ;

read
    :   'read' '(' Identifier ')' ';'
    ;

expression
    :   primary
    |   unaryOp=('+' | '-') left=expression
    |   unaryOp='!' left=expression
    |   left=expression op=('*' | '/' | '%') right=expression
    |   left=expression op=('+' | '-') right=expression
    |   left=expression cmpOp=('<=' | '>=' | '<' | '>') right=expression
    |   left=expression equalOp=('==' | '!=') right=expression
    |   left=expression boolOp='&&' right=expression
    |   left=expression boolOp='||' right=expression
    ;

primary
    :   '(' expression ')'
    |   intLiteral
    |   boolLiteral
    |   functionCall
    |   variable
    ;

functionCall
    :   Identifier '(' arguments? ')'
    ;

variable
    :   Identifier
    ;

arguments
    :   expression (',' expression)*
    ;

intLiteral
    :   IntLiteral
    ;

boolLiteral
    :   BoolLiteral
    ;

//
//  Lexer
//

IntLiteral
    :   '0'
    |   NonZeroDigit Digit*
    ;

BoolLiteral
    :   'true'
    |   'false'
    ;

Identifier
    :   Letter (Letter | Digit)*
    ;

fragment
Letter
    :   [a-zA-Z_]
    ;

fragment
Digit
    :   [0-9]
    ;

fragment
NonZeroDigit
    :   [1-9]
    ;

//
//  Separators
//
LPAREN          : '(';
RPAREN          : ')';
LBRACE          : '{';
RBRACE          : '}';
LBRACK          : '[';
RBRACK          : ']';
COLON           : ':';
SEMICOLON       : ';';
COMMA           : ',';
DOT             : '.';
ARROW           : '->';

//
//  Operators
//
GT              : '>';
LT              : '<';
BANG            : '!';
EQUAL           : '==';
LE              : '<=';
GE              : '>=';
NOTEQUAL        : '!=';
AND             : '&&';
OR              : '||';
ADD             : '+';
SUB             : '-';
MUL             : '*';
DIV             : '/';
MOD             : '%';
ASSIGN          : '=';

//
//  Whitespace and comments
//
WS
    :  [ \t\r\n]+ -> skip
    ;

COMMENT
    :   '/*' .*? '*/' -> skip
    ;

LINE_COMMENT
    :   '//' ~[\r\n]* -> skip
    ;
