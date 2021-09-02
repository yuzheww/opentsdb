// This file is part of OpenTSDB.
// Copyright (C) 2021  The OpenTSDB Authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
grammar MetricExpression2;

// The following fragments A-Z are helpers for case insensitivity.
fragment A : ('a'|'A') ;
fragment B : ('b'|'B') ;
fragment C : ('c'|'C') ;
fragment D : ('d'|'D') ;
fragment E : ('e'|'E') ;
fragment F : ('f'|'F') ;
fragment G : ('g'|'G') ;
fragment H : ('h'|'H') ;
fragment I : ('i'|'I') ;
fragment J : ('j'|'J') ;
fragment K : ('k'|'K') ;
fragment L : ('l'|'L') ;
fragment M : ('m'|'M') ;
fragment N : ('n'|'N') ;
fragment O : ('o'|'O') ;
fragment P : ('p'|'P') ;
fragment Q : ('q'|'Q') ;
fragment R : ('r'|'R') ;
fragment S : ('s'|'S') ;
fragment T : ('t'|'T') ;
fragment U : ('u'|'U') ;
fragment V : ('v'|'V') ;
fragment W : ('w'|'W') ;
fragment X : ('x'|'X') ;
fragment Y : ('y'|'Y') ;
fragment Z : ('z'|'Z') ;

LPAREN : '(' ;
RPAREN : ')' ;

ADD : '+' ;
SUB : '-' ;
MUL : '*' ;
DIV : '/' ;
MOD : '%' ;
POW : '^' ;

EQ  : '==' ;
NEQ : '!=' ;
LT  : '<' ;
LTE : '<=' ;
GT  : '>' ;
GTE : '>=' ;

TRUE  : T R U E ;
FALSE : F A L S E ;

AND : A N D | '&&' ;
OR  : O R | '||' ;
NOT : N O T | '!' ;

fragment DECIMAL_DIGIT : [0-9] ;
fragment DECIMAL_NONZERO_DIGIT : [1-9] ;
fragment DECIMAL_INTEGER_LITERAL : '0' | DECIMAL_NONZERO_DIGIT DECIMAL_DIGIT* ;
DECIMAL_LITERAL : DECIMAL_INTEGER_LITERAL '.' DECIMAL_DIGIT*
                | '.' DECIMAL_DIGIT+
                | DECIMAL_INTEGER_LITERAL
                ;

REX : [a-zA-Z_0-9]+[.a-zA-Z_\-0-9]* ;

// Skip whitespace not yet matched by any lexer rule above.
WS : [ \t\r\n]+ -> skip ;

// prog is the recommended entry-point rule.
prog: expression EOF;

expression : operand                                         # atom
           | op=(SUB|NOT) expression                         # unary
           | expression op=POW expression                    # pow
           | expression op=(MUL|DIV|MOD) expression          # mul
           | expression op=(ADD|SUB) expression              # add
           | expression op=(EQ|NEQ|LT|LTE|GT|GTE) expression # cmp
           | expression op=AND expression                    # and
           | expression op=OR expression                     # or
           | expression '?' expression ':' expression        # ternary
           ;

operand : LPAREN expression RPAREN
        | numeric_literal
        | boolean_literal
        | metric
        ;

numeric_literal : DECIMAL_LITERAL ;

boolean_literal : TRUE | FALSE ;

metric : REX ;
