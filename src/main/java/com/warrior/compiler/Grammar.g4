grammar Grammar;

module
    :   (globalDeclaration | functionDefinition)+
    ;

globalDeclaration
    :   'let' Identifier (':' type)? '=' expression ';'
    ;

functionDefinition
    :   'fn' (prototype | extensionPrototype) block
    ;

prototype
    :   Identifier '(' typedArguments? ')' '->' type
    ;

extensionPrototype
    :   extendedType=type '.' fnName=Identifier '(' self=Identifier (',' typedArguments)? ')' '->' returnType=type
    ;

typedArguments
    :   typedArgument (',' typedArgument)*
    ;

typedArgument
    :   Identifier ':' type
    ;

type
    :   simpleType
    |   tupleType
    |   arrayType
    ;

simpleType
    :   Identifier
    ;

tupleType
    :   '(' (type (',' type)*)? ')'
    ;

arrayType
    :   '[' type ';' intLiteral ']'
    ;

statement
    :   block
    |   exprStatement
    |   assign
    |   setTupleElement
    |   setArrayElement
    |   assignDeclaration
    |   destructiveDeclaration
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

setTupleElement
    :   tuple=expression '.' index=intLiteral '=' value=expression ';'
    ;

setArrayElement
    :   array=expression '[' index=expression ']' '=' value=expression ';'
    ;

assignDeclaration
    :   'let' Identifier (':' type)? '=' expression ';'
    ;

destructiveDeclaration
    :   'let' '(' Identifier (',' Identifier)* ')' '=' expression ';'
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
    |   object=expression '.' Identifier '(' arguments? ')'
    |   tuple=expression '.' tupleIndex=intLiteral
    |   array=expression '[' arrayIndex=expression ']'
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
    |   tupleLiteral
    |   seqArrayLiteral
    |   repeatArrayLiteral
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

tupleLiteral
    :   '(' (expression (',' expression)*)? ')'
    ;

seqArrayLiteral
    :   '[' (expression (',' expression)*)? ']'
    ;

repeatArrayLiteral
    :   '[' expression ';' intLiteral ']'
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
