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




programa:   bloque {System.out.println("reconocio prog");}
           ;

bloque : sentencia 
       | '{'bloque_sentencias'}'
       
       ;
bloque_sentencias  :   sentencia
                    |  bloque_sentencias sentencia
                    ;



sentencia   : declaracion ',' {}
            | ejecucion   ',' {}
	   
            ;





declaracion  :  tipo lista_de_variables  
	     | clousure 
            ;


lista_de_variables : lista_de_variables ';' ID 
		    | ID {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego el identificador"+ID);}
		  
	            ;



clousure : tipofuncion ID '(' ')' '{' bloque_sentencias '}' RETURN  {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego un clousure");}
        
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
	
	;



control : CASE '(' ID ')' '{' lista_acciones '}' 
	
	 ;



lista_acciones : lista_acciones accion 
		| accion {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una accion");}
		;

accion : cte ':' DO bloque
	
	 ;







cte : CTE_D
      | CTE_USLINTEGER{System.out.println("Leida CTE");}
	;


seleccion : IF '(' condicion ')' bloque_sentencias ELSE bloque_sentencias END_IF {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una seleccion");}
	  
	   ;



asignacion : ID {System.out.println("ASIGN ID");} ASIGNACION { System.out.println("ASIGN")} expresion {/*lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una asignacion")*/System.out.println("realice una asignacion");}
           
           ;



expresion : termino '*' factor {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una multiplicacion");} 
	| termino '/' factor {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una division");}
	|  termino {System.out.println("TERMINO a EXPR");}
          ;

termino : termino '*' factor {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una multiplicacion");} 
	| termino '/' factor {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una division");}
	| factor { System.out.println("FACTOR a TERMINO"); }
        ;

factor : cte { System.out.println("CTE a FACTOR"); }
	| factor_negado
	| ID {System.out.println("cargue un identificador");}
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
