grammar Grammar;

root
    : functionDefinition
    ;

functionDefinition
    : 'fn' prototype LBRACE expression RBRACE
    ;

prototype
    :   Identifier LPAREN arguments? RPAREN COLON type
    ;

arguments
    :   argument (COMMA argument)*
    ;

argument
    :   Identifier COLON type
    ;

type
    :   Identifier
    ;

expression
    :   primary
    |   unaryOp=(ADD | SUB) left=expression
    |   unaryOp=BANG left=expression
    |   left=expression op=(MUL | DIV | MOD) right=expression
    |   left=expression op=(ADD | SUB) right=expression
    |   left=expression cmpOp=(LE | GE | LT | GT) right=expression
    |   left=expression cmpOp=(EQUAL | NOTEQUAL) right=expression
    |   left=expression boolOp=AND right=expression
    |   left=expression boolOp=OR right=expression
    ;

primary
    :   LPAREN expression RPAREN
    |   intLiteral
    |   boolLiteral
    |   Identifier
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
Identifier
    :   Letter (Letter | Digit)*
    ;

IntLiteral
    :   '0'
    |   NonZeroDigit Digit*
    ;

BoolLiteral
    :   'true'
    |   'false'
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
