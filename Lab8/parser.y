%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define YYDEBUG 1 
int yylex(void);
void yyerror(char *s);
%}

%token INT
%token CHAR
%token NOT
%token AND
%token OR
%token IF
%token ELSE
%token FOR
%token COUT
%token CIN

%token plus
%token minus
%token mul
%token division
%token lessOrEqual
%token moreOrEqual
%token less
%token more
%token equal
%token different
%token eq
%token rightShift
%token leftShift
%token increment



%token leftCurlyBracket
%token rightCurlyBracket
%token leftRoundBracket
%token rightRoundBracket
%token leftBracket
%token rightBracket
%token comma
%token period

%token IDENTIFIER
%token NUMBER_CONST
%token STRING_CONST

%start program


%%

CONST : NUMBER_CONST | STRING_CONST;

program : Declaration_list Cmpstmt;

Declaration_list : Declaration Declaration_list
                | Declaration
                ;

Declaration : Type IDENTIFIER
            | Type IDENTIFIER eq CONST
            | Type Array_declaration
            ;

Type : INT
     | CHAR
     ;

Array_declaration : IDENTIFIER leftRoundBracket CONST rightRoundBracket
                  ;

Cmpstmt : Stmt Cmpstmt
        | Stmt
        ;

Stmt : Assign_stmt
    | IOstmt
    | Struct_stmt
    ;

Assign_stmt : IDENTIFIER eq Expression
            ;

IOstmt : CIN rightShift IDENTIFIER
      | COUT leftShift CONST
      | COUT leftShift IDENTIFIER
      ;

Struct_stmt : For_stmt
           | If_stmt
           ;

If_stmt : IF leftBracket Condition rightBracket leftCurlyBracket Cmpstmt rightCurlyBracket ELSE leftCurlyBracket Cmpstmt rightCurlyBracket
        | IF leftBracket Condition rightBracket leftCurlyBracket Cmpstmt rightCurlyBracket
        ;

For_stmt : FOR leftBracket For_loop rightBracket leftCurlyBracket Cmpstmt rightCurlyBracket
         ;

Condition : Expression relation Expression
          ;

For_loop : INT IDENTIFIER eq CONST period IDENTIFIER For_relation IDENTIFIER period IDENTIFIER increment
         | INT IDENTIFIER eq CONST period IDENTIFIER For_relation CONST period IDENTIFIER increment
         ;

For_relation : less
             | lessOrEqual
             | more
             | moreOrEqual
             ;

Expression : Term Math_operators Term
          | Term
          ;

Math_operators : mul
               | minus
               | plus
               | division
               ;

Term : CONST
     | IDENTIFIER
     ;

relation : less
         | lessOrEqual
         | more
         | moreOrEqual
         | equal
         | different
         ;

%%

void yyerror(char *s)
{
  printf("%s at line above\n", s);
}

extern FILE *yyin;

int main(int argc, char **argv)
{
  if(argc>1) yyin = fopen(argv[1], "r");
  if((argc>2)&&(!strcmp(argv[2],"-d"))) yydebug = 1;
  if(!yyparse()) fprintf(stderr,"\tEverything is okay\n");
}