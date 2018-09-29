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

bloque : sentencia {System.out.println("se cargo una sentencia");}
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


sentencia   : declaracion ',' {System.out.println("se cargo una declaracion");}
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
		    | ID  {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego el identificador"+ID);}
		    |error_lista_de_variables 
	            ;

error_lista_de_variables : lista_de_variables  ID {lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta el ; que separa las variables");}
		         ;



clousure : VOID ID '(' ')' '{' bloque_sentencias '}' RETURN  {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego un clousure VOID");}
         | FUN  ID '(' ')' '{' bloque_sentencias '}' RETURN '(' bloque_sentencias ')' {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego un clousure FUN");}
         | FUN  ID '(' ')' '{' bloque_sentencias '}' RETURN '(' ID ')' {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego un clousure FUN");}
         | error_clousure
          ;


error_clousure : VOID ID  ')' '{' bloque_sentencias '}' RETURN {lexico.getLexico().agregarError("error en la creacion del clousure void"+ " (aca va el numero de la linea)"+"falta el primer parentesis");}
               | VOID ID '('  '{' bloque_sentencias '}' RETURN {lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta el segundo parentesis");}
               | VOID ID '(' ')'  bloque_sentencias '}' RETURN {lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta la primer llave");}
               | VOID ID '(' ')' '{' bloque_sentencias  RETURN {lexico.getLexico().agregarError("error en la creacion del clousure void"+ "(aca va el numero de la linea)"+"falta la segunda llave");}
               | VOID ID '(' ')'  bloque_sentencias  RETURN {lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"faltan ambas llaves");}
               | VOID ID  '{' bloque_sentencias '}' RETURN {lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta ambos parentesis");}
               | VOID ID '(' ')' '{' bloque_sentencias '}' {lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta return");}
               | VOID  '(' ')' '{' bloque_sentencias '}' RETURN {lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta identificador");}
               | ID '(' ')' '{' bloque_sentencias '}' RETURN  {lexico.getLexico().agregarError("error en la creacion del clousure "+  "(aca va el numero de la linea)"+"falta tipofuncion");}
               | FUN ID  ')' '{' bloque_sentencias '}' RETURN {lexico.getLexico().agregarError("error en la creacion del clousure fun"+ " (aca va el numero de la linea)"+"falta el primer parentesis");}
               | FUN ID '('  '{' bloque_sentencias '}' RETURN {lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta el segundo parentesis");}
               | FUN ID '(' ')'  bloque_sentencias '}' RETURN {lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta la primer llave");}
               | FUN ID '(' ')' '{' bloque_sentencias  RETURN {lexico.getLexico().agregarError("error en la creacion del clousure fun"+ "(aca va el numero de la linea)"+"falta la segunda llave");}
               | FUN ID '(' ')'  bloque_sentencias  RETURN {lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"faltan ambas llaves");}
               | FUN ID  '{' bloque_sentencias '}' RETURN {lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta ambos parentesis");}
               | FUN ID '(' ')' '{' bloque_sentencias '}' {lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta return");}
               | FUN  '(' ')' '{' bloque_sentencias '}' RETURN {lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta identificador");}
               | FUN  ID '(' ')' '{' bloque_sentencias '}' RETURN '('  ')'{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"faltan las sentencias del return");} 
;
    
{/*
tipofuncion : FUN 
            | VOID 
            ;
*/}

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






cte : CTE_D {System.out.println("leida DOUBLE");}
      | CTE_USLINTEGER{System.out.println("Leida CTE");}
	;


seleccion : IF {System.out.println("cargue un if");}'(' condicion ')'{System.out.println("cargue una condicion");} bloque {System.out.println("cargue un BLOQUE1");}ELSE bloque {System.out.println("cargue un BLOQUE ELSE");}END_IF {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una seleccion");}
	  {/*| error_seleccion
	   ;


error_seleccion : 
                 IF '('condicion')' {System.out.println("LOGRE ENTRAR ACA PERO PUTO");} error_bloque ELSE bloque END_IF {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el BLOQUE de la condicion ");}
                 
                IF  condicion ')' bloque_sentencias ELSE bloque_sentencias END_IF {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el '(' de la condicion ");}
		| IF '(' condicion   bloque_sentencias ELSE bloque_sentencias END_IF {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el ')' de la condicion ");}
		| IF  condicion  bloque_sentencias ELSE bloque_sentencias END_IF    {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"faltan los parentesis de la condicion");}
               */} ;

asignacion : ID {System.out.println("lei id");} ASIGNACION {System.out.println("lei asig");} expresion{System.out.println("lei exp");} {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una asignacion");System.out.println("realice una asignacion");}
           | error_asignacion
           ;


error_asignacion : ID expresion { System.out.println("Error"); lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el := de la asignacion ");}
                 | ASIGNACION expresion { System.out.println("Error"); lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el ID de la asignacion ");}
                 ;




expresion : expresion '+' termino {System.out.println("se hizo una suma ");}{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una suma");} 
	  | expresion '-' termino {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una resta");}
	  | termino {System.out.println("TERMINO a EXPR");}
          ;

termino : termino '*' factor {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una multiplicacion");} 
	| termino '/' factor {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una division");}
	| factor { System.out.println("FACTOR a TERMINO"); }
        ;

factor : cte { System.out.println("CTE a FACTOR"); }
	| factor_negado
	| ID {System.out.println("cargue un identificador");}
        {/*| error { System.out.println("Error"); lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el token ");}*/}
	;

factor_negado : '-' CTE_D
              ;


condicion :  {System.out.println("llegue aca pero no lee exp");}expresion {System.out.println("encontre un puta expresion");}comparador expresion 
           ;

comparador : '<'{System.out.println("cargue un menor");}
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
   if(token != null ){ 
    int val =token.getTipo();
    yyval = new ParserVal(token);
    return val;
}
   else return 0;
}

public void yyerror(String s){
    System.out.println("Parser: " + s);
}


