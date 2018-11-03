%{
 package ParserP; 
import AnalizadorLexico.ArchController;
import AnalizadorLexico.Token;
import AnalizadorLexico.TablaSimbolos;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
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




error_bloque : error bloque_sentencias '}' {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" Error sintactico: falta '{' ");}
               | '{' bloque_sentencias error {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" Error sintactico: falta '}' ");} 
             ;


sentencia   : declaracion ',' {lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se cargo una lista de variables");}
            | ejecucion   ',' {}
            | error_sentencia_d 
            ;




error_sentencia_d : declaracion error { lexico.getLexico().agregarError( "en la linea "+lexico.getFuente().getLinea()+"Error sintactico: falta la coma");}
		   | ejecucion error {	lexico.getLexico().agregarError( "en la linea "+lexico.getFuente().getLinea()+"Error sintactico: falta la coma");}
                  
              	;


    declaracion  :  tipo lista_de_variables { /*  String tipo = ((Token) $1.obj).getId();
                                                
                                                for(Token t : (ArrayList<Token>)$2.obj){
                                                   Token nuevo = lexico.getTokenFromList(t.getId());
                                                   if(lexico.getTS().fueDeclarada(nuevo.getId())){
                                                      lexico.getTS().setDeclaracion(nuevo.getId(),tipo);
                                                      System.out.println("se agrego el id "+t.getId()+"del tipo "+tipo);
                                                     }
                                                    else
                                                      System.out.println("el id ya existe");
                                                   }
                                                System.out.println("imprimo las variables de la tablaaaaaaaaaaaaaaaaaaa");   
                                                Hashtable<String, String> ts = lexico.getTS().getDeclaradas();
                                                Enumeration e = ts.keys();
                                                String clave;
                                                while( e.hasMoreElements() ){
                                                clave = (String) e.nextElement();
                                                System.out.println( "Clave : " + clave );
                                                System.out.println(ts.get(clave)); 
                                                }
                                            */}    
             | funcion
            ;


lista_de_variables : lista_de_variables ';' ID  {/* ArrayList<Token> lista  = (ArrayList<Token>) $1.obj;
                                                  System.out.println(((Token) $3.obj).getId()+"AASDADASDASDADADADASDADADADADASDASDADASDASDASDASDASD");
                                                  lista.add((Token)$3.obj);
                                                  $$ = new ParserVal(lista);   
                                                */}
		    | ID  {lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea() +" se agrego el identificador"+ID);
                           /*  System.out.println(((Token) $1.obj).getId()+"AASDADASDASDADADADASDADADADADASDASDADASDASDASDASDASD");
                            ArrayList<Token> lista = new ArrayList<>();
                            lista.add((Token)$1.obj);
                            $$ = new ParserVal(lista);*/
                            }
		    |error_lista_de_variables 
	            ;

error_lista_de_variables : lista_de_variables  ID {lexico.getLexico().agregarError( " en la linea "+lexico.getFuente().getLinea() +" falta el ; que separa las variables");}
		         ;

funcion : FUN  ID '(' ')' '{' bloque_sentencias clousure RETURN '(' retorno_funcion ')' '}' {lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego un clousure FUN");}
        | FUN ID  '(' ')' '{' bloque_sentencias RETURN '(' retorno_funcion ')' '}' {lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego un FUN");}
        | FUN ID '(' ')' error {lexico.getLexico().agregarError("error en la creacion del clousure fun"+" en la linea "+lexico.getFuente().getLinea() +" falta alguna llave");} 
        | FUN ID  '(' ')' '{' bloque_sentencias RETURN '(' retorno_funcion ')' error  {lexico.getLexico().agregarError("error en la creacion del clousure fun"+" en la linea "+lexico.getFuente().getLinea() +" falta alguna llave");}
        ;

retorno_funcion : ID '(' ')'
                | bloque_sentencias
                ;


clousure : VOID ID '(' ')' '{' bloque_sentencias '}' {lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego una funcion VOID");}
         | VOID ID '(' ')' error bloque_sentencias '}' {lexico.getLexico().agregarError("error en la creacion del clousure void"+" en la linea "+lexico.getFuente().getLinea() +"falta la primer llave");}
         | VOID ID '(' ')' '{' bloque_sentencias  error       {lexico.getLexico().agregarError("error en la creacion del clousure void"+" en la linea " + lexico.getFuente().getLinea() +"falta la segunda llave");}
         | VOID ID  error  {lexico.getLexico().agregarError("error en la creacion del clousure void"+" en la linea "+lexico.getFuente().getLinea() +"falta el primer parentesis");}
         | VOID ID '(' error {lexico.getLexico().agregarError("error en la creacion del clousure void"+" en la linea "+lexico.getFuente().getLinea() +"falta el segundo parentesis");}
         | VOID ID '(' ')' '{' bloque_sentencias '}' error {lexico.getLexico().agregarError("error en la creacion del clousure void"+" en la linea "+lexico.getFuente().getLinea() +"falta return");}
;




tipo :  USLINTEGER { System.out.println(" lei un uslinteger ");}
                    { $$ = new ParserVal ( new Token("USLINTEGER"));
                        
                    }
	| DOUBLE    { System.out.println(" lei un double" );}
                    { $$ = new ParserVal ( new Token("DOUBLE"));}
        ;



ejecucion : control 
	  | seleccion 
	  | print 
	  | asignacion 	
	  ;		

print : PRINT '(' CADENA ')'  {lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se hizo un print");}
	|error_print
	;



error_print : PRINT error CADENA ')' {lexico.getLexico().agregarError( "en la linea "+lexico.getFuente().getLinea() +" falta el '(' ");}
	     | PRINT '(' CADENA error {lexico.getLexico().agregarError( " en la linea "+lexico.getFuente().getLinea() +" falta el ')'");}
	     | PRINT CADENA {lexico.getLexico().agregarError( " en la linea "+lexico.getFuente().getLinea() +" falta los '(' ')' ");}
	     ;


control : CASE '(' ID ')' '{' lista_acciones '}' {lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea() +" se creo un case");}
	| error_control
	 ;

error_control : CASE  error ID ')' '{' lista_acciones '}' {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta parentesis inicio");}
  	      |	CASE '(' ID error '{' lista_acciones '}' {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta parentesis de cierre");}
	      |	CASE '(' ID ')' error lista_acciones '}' {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta llave inicio ");}
	      |	CASE '(' ID ')' '{' lista_acciones error {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta llave cierre");}
	      |	CASE  ID error '{' lista_acciones '}'    {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta  parentesis  ");}
	      |	CASE  ID   lista_acciones  	error    {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta parentesis y llave");}
	      |	CASE  '('ID ')'  lista_acciones   error  {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" faltan llaves ");}
	      ;

lista_acciones : lista_acciones accion 
		| accion {lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una accion");}
		;

accion : cte ':' DO bloque
	| error_accion
	 ;


error_accion : cte error DO bloque {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta el ':' ");}
             ;






cte : CTE_D {System.out.println("leida DOUBLE");}
      | CTE_USLINTEGER{System.out.println("Leida CTE");}
	;



if_condicion : IF '(' condicion ')' 
             | IF error condicion ')' {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta el '(' de la condicion ");}
             | IF '(' condicion error {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta el ')' de la condicion ");}
             | IF condicion  error   {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" faltan los parentesis de la condicion");}
            ;

seleccion : if_condicion bloque END_IF            {lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una condicion IF");}
          | if_condicion bloque ELSE bloque END_IF {lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una condicion IF con ELSE");}
          | if_condicion error                      {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" faltan el bloque de la condicion");}
          | if_condicion bloque error               {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" faltan end_if");}
          ;



asignacion : ID  ASIGNACION  expresion {lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego una asignacion");System.out.println("realice una asignacion");}
                { }
           | error_asignacion
           ;


error_asignacion : ID expresion { System.out.println("Error"); lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta el := de la asignacion ");}
                 | ASIGNACION expresion { System.out.println("Error"); lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta el ID de la asignacion ");}
                 ;




expresion : expresion '+' termino {System.out.println("se hizo una suma ");}{lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una suma");}
	  | expresion '-' termino {lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una resta");}
	  | termino {System.out.println("TERMINO a EXPR");}
          ;

termino : termino '*' factor {lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una multiplicacion");}
	| termino '/' factor {lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una division");}
	| factor { System.out.println("FACTOR a TERMINO"); }
        ;

factor : cte { System.out.println("CTE a FACTOR"); }
	| factor_negado
	| ID {System.out.println("cargue un identificador");
               System.out.println("El identificador que tengo en $1 es " + ((String) $1.obj)  );
             }
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
private TablaSimbolos tSimbolos;
public Parser(ArchController lexico)
{
  this.lexico= lexico;
  this.tSimbolos = lexico.getTS();
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


