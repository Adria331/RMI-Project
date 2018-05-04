
%{
#include <stdio.h>
#include <stdlib.h>

#include "symtab.h"
#include "semantic.h"
#include "ast.h"

int yylex();
int yyerror();
//#define YYDEBUG 1

%}

%union {
   int number;
   char *name;
   struct Arbre *arbre;
}

/* Terminals */
%token SEMI LPAREN RPAREN EOLN ASSIGN VAR
%token<number> NUMBER
%token FUN COMMA BEGINF ENDF
%token<name> ID

/* Precedencia i associativitat */
%left MINUS PLUS   // Baixa
%left TIMES DIV
%right UNITMINUS // Alta

/* No terminals amb tipus */

%type<arbre> expr expr_list

%%

programa : expr_list {}
         ;

expr_list : {}
          | expr_list EOLN {}
          | expr_list expr EOLN { }
          | error EOLN { yyerrok; }
          ;

expr      : NUMBER { $$ = crear_fulla($1);}
	    | ID ASSIGN NUMBER { afegir_variable($1, $3); }	
            | expr PLUS expr {  }
            | expr MINUS expr {  }
            | expr TIMES expr {  }
            | expr DIV expr {  }
            | LPAREN expr RPAREN {  }
            | ID { int res = avaluar_variable($1); printf("%i",res); }
	    ;

%%

extern FILE* yyin;

int main(int argc, char *argv[]) {
  //yydebug = 1;
  yyparse();
  return 0;
}
