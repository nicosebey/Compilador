%{
 package ParserP; 
import AnalizadorLexico.ArchController;
import AnalizadorLexico.Token;
%}

%token S_MAYOR_IGUAL
%token S_MENOR_IGUAL
%token S_DISTINTO
%token CTE_D
%token CTE_USLINTEGER
%token COMENTARIO
%token CADENA
%token ID
%token IF
%token THEN
%token ELSE
%token END_IF
%token DO
%token FUN
%token DOUBLE
%token USLINTEGER
%token RETURN 
%token PRINT
%token ASIGNACION
%token CASE
%token VOID
%%




programa:   bloque
           ;

bloque : sentencia 
       | '{'bloque_sentencias'}'
       | error_bloque
       ;
bloque_sentencias  :   sentencia
                    |  bloque_sentencias sentencia
                    ;


error_bloque : error bloque_sentencias '}' {lexico.getLexico().agregarError("en la linea "+" (aca va el numero de la linea)"+" Error sintactico: falta '{' ");}
	     | error bloque_sentencias error  {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+" Error sintactico: falta '{'y '}' ");}
	     | '{' bloque_sentencias error {lexico.getLexico().agregarError( "en la linea "+"(aca va el numero de la linea)"+" Error sintactico: falta '}' ");}
             ;


sentencia   : declaracion ',' {}
            | ejecucion   ',' {}
	    | error_sentencia_d 
            ;



error_sentencia_d : declaracion error { lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"Error sintactico: falta la coma");}
		   | ejecucion error {	lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"Error sintactico: falta la coma");}
			;




declaracion  :  tipo lista_de_variables  
	     | clousure 
            ;


lista_de_variables : lista_de_variables ';' ID 
		    | ID {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego el identificador"+ID);}
		    |error_lista_de_variables 
	            ;

error_lista_de_variables : lista_de_variables  ID {lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta el ; que separa las variables");}
		         ;



clousure : tipofuncion ID '(' ')' '{' bloque_sentencias '}' RETURN  {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego un clousure");}
         | error_clousure
          ;


error_clousure : tipofuncion ID  ')' '{' bloque_sentencias '}' RETURN {lexico.getLexico().agregarError(" (aca va el numero de la linea)"+"falta el primer parentesis");}
               | tipofuncion ID '('  '{' bloque_sentencias '}' RETURN {lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta el segundo parentesis");}
               | tipofuncion ID '(' ')'  bloque_sentencias '}' RETURN {lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta la primer llave");}
               | tipofuncion ID '(' ')' '{' bloque_sentencias  RETURN {lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta la segunda llave");}
               | tipofuncion ID '(' ')'  bloque_sentencias  RETURN {lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"faltan ambas llaves");}
               | tipofuncion ID  '{' bloque_sentencias '}' RETURN {lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta ambos parentesis");}
               ;


tipofuncion : FUN 
            | VOID 
            ;


tipo :  USLINTEGER
	| DOUBLE
        ;



ejecucion : control 
	  | seleccion 
	  | print 
	  | asignacion 	
	  ;		

print : PRINT '(' CADENA ')'  {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se hizo un print");}
	|error_print
	;



error_print : PRINT CADENA ')' {lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta el '(' ");}
	     | PRINT '(' CADENA {lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta el ')'");} 
	     | PRINT CADENA {lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta los '(' ')' ");}
	     ;


control : CASE '(' ID ')' '{' lista_acciones '}' 
	| error_control
	 ;

error_control : CASE  ID ')' '{' lista_acciones '}' {lexico.getLexico().agregarError("en la linea "+" (aca va el numero de la linea)"+"falta algun parentesis o llave");}
  	      |	CASE '(' ID  '{' lista_acciones '}' {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+" falta algun parentesis o llave");}
	      |	CASE '(' ID ')'  lista_acciones '}' {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta algun parentesis o llave ");}
	      |	CASE '(' ID ')' '{' lista_acciones  {lexico.getLexico().agregarError("en la linea "+"(aca va el numero de la linea)"+"falta algun parentesis o llave");}
	      |	CASE  ID  '{' lista_acciones '}'    {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta algun parentesis o llave ");}
	      |	CASE  ID   lista_acciones  	    {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta algun parentesis o llave");}      
	      |	CASE  '('ID ')'  lista_acciones     {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta algun parentesis o llave ");}
	      ;

lista_acciones : lista_acciones accion 
		| accion {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una accion");}
		;

accion : cte ':' DO bloque
	| error_accion
	 ;


error_accion : cte error DO bloque {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el ':' ");}
             ;






cte : CTE_D
      | CTE_USLINTEGER
	;


seleccion : IF '(' condicion ')' bloque_sentencias ELSE bloque_sentencias END_IF {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una seleccion");}
	  | error_seleccion
	   ;


error_seleccion : IF error condicion ')' bloque_sentencias ELSE bloque_sentencias END_IF {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el '(' de la condicion ");}
		| IF '(' condicion   bloque_sentencias ELSE bloque_sentencias END_IF {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el ')' de la condicion ");}
		| IF  condicion  bloque_sentencias ELSE bloque_sentencias END_IF    {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"faltan los parentesis de la condicion");}
                ;

asignacion : ID ':=' expresion {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una asignacion");}
           | error_asignacion
           ;


error_asignacion : ID  {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el := de la asignacion ");}
                 ;




expresion : expresion '+' termino {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una suma");} 
	  | expresion '-' termino {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una resta");}
	  | termino 
          
                ;

termino : termino '*' factor {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una multiplicacion");} 
	| termino '/' factor {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una division");}
	| factor 
       
              ;

factor : cte
	| factor_negado
	| ID 
	;

factor_negado : '-' CTE_D
              ;


condicion :  expresion comparador expresion 
           ;

comparador : '<'
	   |  '>'
	   |   '=' 
           |  S_MAYOR_IGUAL
	   |  S_MENOR_IGUAL
	   |  S_DISTINTO 
            ;

%%





private ArchController lexico;
public Parser(ArchController lexico)
{
  this.lexico= lexico;
} 

public int yylex(){
    Token token = this.lexico.getToken();
    
    int val =token.getTipo();
    yyval = new ParserVal(token);
    return val;
}

public void yyerror(String s){
    System.out.println("Parser: " + s);
}


