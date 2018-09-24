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



programa  :   bloque_sentencias
           ;

bloque_sentencias  :   bloque_lista_sentencias
                    |  sentencia
                    ;

bloque_lista_sentencias : '{' bloque_sentencias sentencia '}'


sentencia   : declaracion
            | ejecucion
            ;


declaracion  :  tipo lista_de_variables { } 
	     | clousure {};


lista_de_variables : lista_de_variables ';' ID {}
		  
		    | ID {} ;

clousure : tipofuncion ID '(' ')' '{' bloque_sentencias '}' RETURN ;

tipofuncion : FUN {}
            | VOID {} ;


tipo :  USLINTEGER{}
	| DOUBLE{};



ejecucion : control
	  | seleccion
	  | print 
	  | asignacion 	
		

print : PRINT '(' CADENA ')' ',' ;

control : CASE '(' ID ')' '{' lista_acciones '}' ',' ;

lista_acciones : lista_acciones accion | accion ;

accion : cte DO bloque_sentencias ',' ;

cte : CTE_D
      | CTE_USLINTEGER


seleccion : IF '(' condicion ')' bloque_sentencias ELSE bloque_sentencias END_IF;


asignacion : ID ':=' expresion ','{};


expresion : expresion '+' termino {} 
	  | expresion '-' termino {}
	  | termino {};



termino : termino '*' factor {} 
	| termino '/' factor {}
	| factor {};



factor : CTE_D {}
	| '-' CTE_D{}
	| CTE_USLINTEGER{}
	| ID {};


condicion :  expresion comparador expresion {};

comparador : '<'{}
	   |  '>'{}
	   |   '=' {}
           |  S_MAYOR_IGUAL{}
	   |  S_MENOR_IGUAL{}
	   |  S_DISTINTO {};



