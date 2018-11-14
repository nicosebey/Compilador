%{
 package ParserP; 
import AnalizadorLexico.ArchController;
import AnalizadorLexico.Token;
import AnalizadorLexico.TablaSimbolos;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import CodigoIntermedio.*;
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


    declaracion  :  tipo lista_de_variables {   String tipo = ((Token) $1.obj).getId();
                                               // System.out.println("el tipo de las variables es "+tipo);
                                                for(Token t : (ArrayList<Token>)$2.obj){
                                                   
                                                   t.setTipoReal(tipo);
                                                   t.setAmbito(ambito.get(ambito.size()-1));
                                                   t.setVisible(true);

                                                   if(!lexico.getTS().fueDeclarada(t.getId())){
                                                      lexico.getTS().setDeclaracion(t.getId(),t);
                                                      System.out.println("se agrego el id "+t.getId()+"del tipo "+tipo);
                                                     }
                                                    else
                                                      System.out.println("el id ya existe");
                                                   }/*
                                                System.out.println("imprimo las variables de la tablaaaaaaaaaaaaaaaaaaa");   
                                                Hashtable<String, Token> ts = lexico.getTS().getDeclaradas();
                                                Enumeration e = ts.keys();
                                                String clave;
                                                while( e.hasMoreElements() ){
                                                clave = (String) e.nextElement();
                                                System.out.println( "Clave : " + clave );
                                                System.out.println(ts.get(clave).getId()); 
                                                }*/
                                           }    
             | funcion
            ;


lista_de_variables : lista_de_variables ';' ID  { ArrayList<Token> lista  = (ArrayList<Token>) $1.obj;
                                                 // System.out.println(((Token) $3.obj).getId()+" SE AGREGO A LA LISTA DE VARIABLES");
                                                  lista.add((Token)$3.obj);
                                                  $$ = new ParserVal(lista);   
                                                }
		    | ID  {lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea() +" se agrego el identificador"+ID);
                           // System.out.println(((Token) $1.obj).getId()+" SE AGREGO A LA LISTA DE VARIABLES ");
                            ArrayList<Token> lista = new ArrayList<>();
                            lista.add((Token)$1.obj);
                            $$ = new ParserVal(lista);
                            }
		    |error_lista_de_variables 
	            ;

error_lista_de_variables : lista_de_variables  ID {lexico.getLexico().agregarError( " en la linea "+lexico.getFuente().getLinea() +" falta el ; que separa las variables");}
		         ;

funcion : FUN  ID '(' ')' '{' bloque_sentencias clousure RETURN '(' retorno_funcion ')' '}' {lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego un clousure FUN");}
                                                {
                                                 Terceto terceto = new Terceto ($3.sval,,null);
                                                }

        | FUN ID  '(' ')'{ 
                                                String nombreF = $2.sval;    
                                                ambito.add(nombreF);
                                                String nombreFun = $2.sval;
                                                Terceto terceto = new Terceto ("FUNCION",(Token) $6.obj,null);
                                                ctrlTercetos.agregar(tercetos);
                                                Terceto terceto2 = new Terceto("Corte_Fun",null,null);//ESTE TERCETO LO CREO PARA SABER CUANDO TERMINA EL BLOQUE DE LA FUNCION
                                                ctrlTercetos.agregar(Terceto2);
                                                //TENGO QUE CONTROLAR QUE LA FUNCION NO SE HAYA DECLARADO ANTES, QUE LAS VARIABLES ESTEN DECLARADAS EN LA FUN
                } '{' bloque_sentencias RETURN
                                                 { String nombreFun = $2.sval;
                                                   if(!lexico.getTS().fueDeclarada(nombreFun)){           
                                                        Token funcion = New Token(nombreFun);
                                                        lexico.getTS().setDeclaracion(nombreFun,funcion);
                                                    
                                                    }
                                                    }
               '(' retorno_funcion ')'{         
                                                Terceto terceto3 = new Terceto ("Retorno_func",(Token) $9.obj,null);
                                                ctrlTercetos.agregar(terceto3);
                                                Terceto terceto4 = new Terceto ("corte_retorno",null,null);//ESTE TERCETO LO CREO PARA SABER CUANDO TERMINA EL RETORNO DE LA FUNCION
                                                ctrlTercetos.agregar(terceto4);
                                                lexico.getTS().setInvisibles($2.sval);
                                                ambito.push();

                                        } '}' {lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego un FUN");}
                                                
        | FUN ID '(' ')' error {lexico.getLexico().agregarError("error en la creacion del clousure fun"+" en la linea "+lexico.getFuente().getLinea() +" falta alguna llave");} 
        | FUN ID  '(' ')' '{' bloque_sentencias RETURN '(' retorno_funcion ')' error  {lexico.getLexico().agregarError("error en la creacion del clousure fun"+" en la linea "+lexico.getFuente().getLinea() +" falta alguna llave");}
        ;

retorno_funcion : ID '(' ')'  //CONTROLAR QUE ESTE ID YA EXISTA
                | bloque_sentencias
                ;

clousure : VOID ID '(' ')' {    
                                                String nombreFun = $2.sval;    
                                                ambito.add(nombreFun);
                                                String nombreFun = $2.sval;
                                                Terceto terceto = new Terceto ("FUNCION",(Token) $6.obj,null);
                                                ctrlTercetos.agregar(tercetos);
                                                Terceto terceto2 = new Terceto("Corte_Fun",null,null);//ESTE TERCETO LO CREO PARA SABER CUANDO TERMINA EL BLOQUE DE LA FUNCION
                                                ctrlTercetos.agregar(Terceto2);
                            }
                '{' bloque_sentencias '}' {lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego una funcion VOID");
                                                 String nombreFun = $2.sval;
                                                 if(!lexico.getTS().fueDeclarada(nombreFun)){           
                                                    Token funcion = New Token(nombreFun);
                                                    lexico.getTS().setDeclaracion(nombreFun,funcion);
                                          }
                                    }           
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

// EL TERCETO VA A SER ["case",variable con la que chequeo,null(por ahora luego va a a ser el numero de terceto a donde salto)]
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






cte : CTE_D {System.out.println("leida DOUBLE");
             Token nuevo = (Token) $1.obj;
             lexico.getTS().setDeclaracion(nuevo.getId(),nuevo);
             nuevo.setTipoReal("double");
             $$ = new ParserVal(nuevo);
             }

      | CTE_USLINTEGER{System.out.println("Leida CTE");
                        Token nuevo = (Token) $1.obj;
                        lexico.getTS().setDeclaracion(nuevo.getId(),nuevo);
                        nuevo.setTipoReal("uslinteger");  
                        $$ = new ParserVal(nuevo);
                        }
	;



if_condicion : IF '(' condicion ')' {
                                     Terceto tercero = new Terceto(new Token("bf"),(Token) $3.obj,null);
                                     ctrlTercetos.agregarTerceto(terceto);
                                     ctrlTercetos.apilar(terceto.getNro());
                                    }
             | IF error condicion ')' {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta el '(' de la condicion ");}
             | IF '(' condicion error {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta el ')' de la condicion ");}
             | IF condicion  error   {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" faltan los parentesis de la condicion");}
            ;

seleccion : if_condicion bloque END_IF            {lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una condicion IF");
                                                   ctrlTercetos.desapilar();  
                                                  }
          | if_condicion bloque ELSE{ Terceto terceto = new Terceto(New Token("bi"),null,null);
                                      ctrlTercetos.agregarTerceto(terceto);
                                      ctrlTercetos.desapilar();
                                      ctrlTercetos.apilar(terceto.getNro());

                                    } bloque END_IF {lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una condicion IF con ELSE");
                                                     ctrlTercetos.desapilar();
                                                    }
          | if_condicion error                      {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" faltan el bloque de la condicion");}
          | if_condicion bloque error               {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" faltan end_if");}
          ;



asignacion : ID  ASIGNACION  expresion {lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego una asignacion");System.out.println("realice una asignacion");}
                { 
                   Token id = (Token) $1.obj;
                   Token exp = (Token) $3.obj;
                   if( id.getTipoReal().equals(exp.getTipoReal())){ 
                        
                      }
                    else
                        System.out.println("TIPOS INCOMPATIBLES");
                    
                }
           | error_asignacion
           ;


error_asignacion : ID expresion { System.out.println("Error"); lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta el := de la asignacion ");}
                 | ASIGNACION expresion { System.out.println("Error"); lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta el ID de la asignacion ");}
                 ;




expresion : expresion '+' termino {System.out.println("se hizo una suma ");}{lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una suma");
                                    Token termino1 = (Token) $1.obj;
                                    Token factor = (Token) $3.obj;
                                    if(termino1.getTipoReal().equals(factor.getTipoReal())){
                                    //ACA CREO EL TERCETO
                                            String simbolo = "+";
                                            String tipo = termino1.getTipoReal();
                                            Terceto terceto = new Terceto(simbolo,termino1,factor);
                                            ctrlTercetos.agregarTerceto(terceto);
                                            Token resultado = new Token(true,String.valueOf(terceto.getNro()));
                                            resultado.setTipoReal(factor.getTipoReal());
                                            System.out.println("estoy imprimiendo el tipo del resultado"+ resultado.getTipoReal());
                                            $$ = new ParserVal(resultado);
                                       }
                                    else
                                    System.out.println("ERROR, TIPOS INCOMPATIBLES");
                                    }
	  | expresion '-' termino {lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una resta");
                                        Token termino1 = (Token) $1.obj;
                                        Token factor = (Token) $3.obj;
                                        if(termino1.getTipoReal().equals(factor.getTipoReal())){
                                        //ACA CREO EL TERCETO
                                            String simbolo = "-" ;
                                            String tipo = termino1.getTipoReal();
                                            Terceto terceto = new Terceto(simbolo,termino1,factor);
                                            ctrlTercetos.agregarTerceto(terceto);
                                            Token resultado = new Token(true,String.valueOf(terceto.getNro()));
                                            resultado.setTipoReal(factor.getTipoReal());
                                            $$ = new ParserVal(resultado);
                                        }
                                        else
                                        System.out.println("ERROR, TIPOS INCOMPATIBLES"); 
                                  $$= null;    
                                 }
	  | termino {System.out.println("TERMINO a EXPR");
                     $$ = new ParserVal ( (Token) $1.obj);
                    }
          ;

termino : termino '*' factor {lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una multiplicacion");
                               Token termino1 = (Token) $1.obj;
                               Token factor = (Token) $3.obj;
                               if(termino1.getTipoReal().equals(factor.getTipoReal())){
                                 //ACA CREO EL TERCETO
                                            String simbolo = "*";
                                            String tipo = termino1.getTipoReal();
                                            Terceto terceto = new Terceto(simbolo,termino1,factor);
                                            ctrlTercetos.agregarTerceto(terceto);
                                            Token resultado = new Token(true,String.valueOf(terceto.getNro()));
                                            resultado.setTipoReal(factor.getTipoReal());
                                            System.out.println("estoy imprimiendo el tipo del resultado"+ resultado.getTipoReal());
                                            $$ = new ParserVal(resultado);
                                }
                                else
                                System.out.println("ERROR, TIPOS INCOMPATIBLES");
                                    
                                
                             }
	| termino '/' factor {lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una division");
                              Token termino1 = (Token) $1.obj;
                               Token factor = (Token) $3.obj;
                               if(termino1.getTipoReal().equals(factor.getTipoReal())){
                                 //ACA CREO EL TERCETO
                                            String simbolo = "/";
                                            String tipo = termino1.getTipoReal();
                                            Terceto terceto = new Terceto(simbolo,termino1,factor);
                                            ctrlTercetos.agregarTerceto(terceto);
                                            Token resultado = new Token(true,String.valueOf(terceto.getNro()));
                                            resultado.setTipoReal(factor.getTipoReal());
                                            $$ = new ParserVal(resultado);
                                }
                                else
                                System.out.println("ERROR, TIPOS INCOMPATIBLES"); 
                                }
	| factor { System.out.println("FACTOR a TERMINO"); 
                    $$ =  new ParserVal((Token) $1.obj);
                 }
        ;

factor : cte { System.out.println("CTE a FACTOR"); }
	| factor_negado
	| ID { Token nuevo =(Token) $1.obj;
                System.out.println(nuevo.getId()+"ESTE ES EL LEXEMA QUE VOY A BUSCAR EN LA TABLA DE SIMBOLOS");
                  
                if(lexico.getTS().fueDeclarada((nuevo.getId())))
                $$ = new ParserVal(lexico.getTS().getToken(((nuevo.getId()))));
               else
                    System.out.println("el id nunca fue declarado");
             }
	;

factor_negado : '-' CTE_D
              ;


condicion :  expresion comparador expresion { String simbolo = ((Token) $2.obj).getId(); 
                                              Token exp1 = (Token) $1.obj;
                                              Token exp2 = (Token) $3.obj;
                                              Terceto terceto = new Terceto(simbolo,exp1,exp2);
                                              ctrlTercetos.agregarTerceto(terceto);
                                              Token resultado = new Token(true,String.valueOf(terceto.getNro()));   
                                              resultado.setTipoReal(exp1.getTipoReal());
                                              $$ = new ParserVal(resultado);
                                              
                                            }
           ;

comparador : '<' { String comparador = ' <';
                   $$ = new ParserVal(new Token(comparador));
                   }
	   |  '>'{ String comparador = ' >';
                   $$ = new ParserVal(new Token(comparador));
                   }
	   |   '='{ String comparador = '=';
                   $$ = new ParserVal(new Token(comparador));
                   } 
           |  S_MAYOR_IGUAL  {
                                $$ = new ParserVal(new Token('>='));
                                }
	   |  S_MENOR_IGUAL  {
                                $$ = new ParserVal(new Token('<='));
                                }
	   |  S_DISTINTO     {
                                $$ = new ParserVal(new Token('!='));
                                }
            ;

%%





private ArchController lexico;
private TablaSimbolos tSimbolos;
private CtrlTercetos ctrlTercetos;
private ArrayList<String> ambito;
public Parser(ArchController lexico)
{
  this.lexico= lexico;
  this.tSimbolos = lexico.getTS();
  this.ctrlTercetos = new CtrlTercetos();
  ambito = new ArrayList<String>();
  ambito.add("main");
} 

public int yylex(){
    Token token = this.lexico.getToken();
   if(token != null ){ 
    int val =token.getTipo();
    yylval = new ParserVal(token);
    return val;
}
   else{
        for (Terceto t : ctrlTercetos.getTercetos())
                t.getNro();
        return 0;
        }
}

public void yyerror(String s){
    System.out.println("Parser: " + s);
}


