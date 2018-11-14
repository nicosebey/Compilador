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




error_bloque : error bloque_sentencias '}' { lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" Error sintactico: falta  llave ");}
               | '{' bloque_sentencias error {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" Error sintactico: falta llave ");} 
             ;


sentencia   : declaracion ',' {lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se cargo una lista de variables");}
            | ejecucion   ',' 
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
                                                   }
                                                System.out.println("imprimo las variables de la tablaaaaaaaaaaaaaaaaaaa");   
                                                Hashtable<String, Token> ts = lexico.getTS().getDeclaradas();
                                                Enumeration e = ts.keys();
                                                String clave;
                                                while( e.hasMoreElements() ){
                                                clave = (String) e.nextElement();
                                                System.out.println( "Clave : " + clave );
                                                System.out.println(ts.get(clave).getId()+" "+ts.get(clave).getTipo()+" : TIPO"+ts.get(clave).getTipoReal()+" : TIPOreal"); 
                                                }
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
		    |error_lista_de_variables {/*ArrayList<Token> lista = new ArrayList<>();
                                         lista.add((Token)$1.obj);
                                         $$ = new ParserVal(lista);
                                         //MENSAJE ERROR */}
	            ;

error_lista_de_variables : lista_de_variables  ID {lexico.getLexico().agregarError( " en la linea "+lexico.getFuente().getLinea() +" falta el ; que separa las variables");}
		         ;

funcion : encabezado_fun bloque_sentencias clousure {Terceto terceto2 = new Terceto("Corte_cl",null,null);//ESTE TERCETO LO CREO PARA SABER CUANDO TERMINA EL BLOQUE DE LA FUNCION
                                                ctrlTercetos.agregarTerceto(terceto2);
                                                }RETURN '(' retorno_funcion fin_fun {lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego un clousure FUN");}
                                                {
                                                 //Terceto terceto = new Terceto ($3.sval,,null);
                                                }
                                  
        | encabezado_fun   bloque_sentencias RETURN
                                                 { String nombreFun = ctrlTercetos.getFuncionActual();
                                                   Terceto terceto2 = new Terceto("Corte_Fun",null,null);//ESTE TERCETO LO CREO PARA SABER CUANDO TERMINA EL BLOQUE DE LA FUNCION
                                                   ctrlTercetos.agregarTerceto(terceto2);
                                                   if(!lexico.getTS().fueDeclarada(nombreFun)){           
                                                        Token funcion = new Token(nombreFun);
                                                        lexico.getTS().setDeclaracion(nombreFun,funcion);
                                                    
                                                    }
                                                    } 
               '(' retorno_funcion {        
                                                Terceto terceto3 = new Terceto ("Retorno_func",(Token) $4.obj,new Token("-"));
                                                ctrlTercetos.agregarTerceto(terceto3);
                                                Terceto terceto4 = new Terceto ("corte_retorno",new Token("-"),new Token("-"));//ESTE TERCETO LO CREO PARA SABER CUANDO TERMINA EL RETORNO DE LA FUNCION
                                                ctrlTercetos.agregarTerceto(terceto4);
                                                lexico.getTS().setInvisibles(ctrlTercetos.getFuncionActual());
                                                ambito.remove(ambito.size()-1);

                                        }  fin_fun {lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego un FUN");

                                                 ctrlTercetos.setFuncionActual("main");}
                                                
        | FUN ID '(' ')' bloque_sentencias RETURN  {lexico.getLexico().agregarError("error en la creacion del clousure fun"+" en la linea "+lexico.getFuente().getLinea() +" falta alguna llave");
                                // Terceto terceto = new Terceto ($3.sval,,null);
                                   //MENSAJE ERROR 
                                }
        /*| encabezado_fun bloque_sentencias RETURN '(' retorno_funcion ')'   {lexico.getLexico().agregarError("error en la creacion del clousure fun"+" en la linea "+lexico.getFuente().getLinea() +" falta alguna llave");
                             //Terceto terceto = new Terceto ($3.sval,,null);
                             //MENSAJE ERROR    
                            }*/
        ;


encabezado_fun : FUN  ID '(' ')' '{'{                    
                                                ctrlTercetos.setFuncionActual(((Token)$2.obj).getId());
                                                String nombreF = ((Token)$2.obj).getId();    
                                                ambito.add(nombreF);
                                                String nombreFun = ((Token)$2.obj).getId();
                                                Token funcion = new Token (nombreF);
                                                Terceto terceto = new Terceto ("FUNCION",funcion,null);
                                                ctrlTercetos.agregarTerceto(terceto);
                                                
                                                //TENGO QUE CONTROLAR QUE LA FUNCION NO SE HAYA DECLARADO ANTES, QUE LAS VARIABLES ESTEN DECLARADAS EN LA FUN
                };


fin_fun : ')' '}'
            | ')' {lexico.getLexico().agregarError("error en la creacion del clousure fun"+" en la linea "+lexico.getFuente().getLinea() +" falta alguna llave");
                   Terceto terceto = new Terceto("corte_fun",new Token("-"),new Token("-"));}
            ;

retorno_funcion : ID '(' ')'  //CONTROLAR QUE ESTE ID YA EXISTA
                | bloque_sentencias {System.out.println("55555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555");}
                ;

clousure : VOID ID '(' ')' {    
                                                String nombreFun = ((Token)$2.obj).getId();    
                                                ambito.add(nombreFun);
                                                Token funcion = new Token (nombreFun);
                                                Terceto terceto = new Terceto ("FUNC_clousure", funcion,null);
                                                ctrlTercetos.agregarTerceto(terceto);
                                                
                            }
                '{' bloque_sentencias '}' {lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego una funcion VOID");
                                                 String nombreFun = ((Token)$2.obj).getId();
                                                 if(!lexico.getTS().fueDeclarada(nombreFun)){           
                                                    Token funcion = new Token(nombreFun);
                                                    lexico.getTS().setDeclaracion(nombreFun,funcion);
                                                    }
                                          }              
        | VOID ID '(' ')'  {    
                                                String nombreFun = ((Token)$2.obj).getId();    
                                                ambito.add(nombreFun);
                                                Token funcion = new Token (nombreFun);
                                                Terceto terceto = new Terceto ("FUNC_clousure", funcion,null);
                                                ctrlTercetos.agregarTerceto(terceto);
                                               }
                     bloque_sentencias '}'{lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego una funcion VOID");
                                                 String nombreFun = ((Token)$2.obj).getId();
                                                 if(!lexico.getTS().fueDeclarada(nombreFun)){           
                                                    Token funcion2 = new Token(nombreFun);
                                                    lexico.getTS().setDeclaracion(nombreFun,funcion2);
                                                    }
                                          }  {lexico.getLexico().agregarError("error en la creacion del clousure void"+" en la linea "+lexico.getFuente().getLinea() +"falta la primer llave");}
                                                   





        | VOID ID '(' ')' {    
                                                String nombreFun = ((Token)$2.obj).getId();    
                                                ambito.add(nombreFun);
                                                Token funcion = new Token (nombreFun);
                                                Terceto terceto = new Terceto ("FUNC_clousure", funcion,null);
                                                ctrlTercetos.agregarTerceto(terceto);
                                                lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego una funcion VOID");
                                                 String nombreFun2 = ((Token)$2.obj).getId();
                                                 if(!lexico.getTS().fueDeclarada(nombreFun2)){           
                                                    Token funcion2 = new Token(nombreFun2);
                                                    lexico.getTS().setDeclaracion(nombreFun2,funcion2);}
                                                
                            } /*'{' bloque_sentencias */   error     {lexico.getLexico().agregarError("error en la creacion del clousure void"+" en la linea " + lexico.getFuente().getLinea() +"falta la segunda llave");}
        | VOID ID  {    
                                                String nombreFun = ((Token)$2.obj).getId();    
                                                ambito.add(nombreFun);
                                                Token funcion = new Token (nombreFun);
                                                Terceto terceto = new Terceto ("FUNC_clousure", funcion,null);
                                                ctrlTercetos.agregarTerceto(terceto);
                                                lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego una funcion VOID");
                                                 String nombreFun2 = ((Token)$2.obj).getId();
                                                 if(!lexico.getTS().fueDeclarada(nombreFun2)){           
                                                    Token funcion2 = new Token(nombreFun2);
                                                    lexico.getTS().setDeclaracion(nombreFun2,funcion2);}}error  {lexico.getLexico().agregarError("error en la creacion del clousure void"+" en la linea "+lexico.getFuente().getLinea() +"falta el primer parentesis");}
        | VOID ID '(' {    
                                                String nombreFun = ((Token)$2.obj).getId();    
                                                ambito.add(nombreFun);
                                                Token funcion = new Token (nombreFun);
                                                Terceto terceto = new Terceto ("FUNC_clousure", funcion,null);
                                                ctrlTercetos.agregarTerceto(terceto);
                                                lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego una funcion VOID");
                                                 String nombreFun2 = ((Token)$2.obj).getId();
                                                 if(!lexico.getTS().fueDeclarada(nombreFun2)){           
                                                    Token funcion2 = new Token(nombreFun2);
                                                    lexico.getTS().setDeclaracion(nombreFun2,funcion);} {lexico.getLexico().agregarError("error en la creacion del clousure void"+" en la linea "+lexico.getFuente().getLinea() +"falta el segundo parentesis");}
                                                 }       
     // | VOID ID '(' ')' {    }
                                               
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

print : PRINT '(' CADENA ')'  {lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se hizo un print");
                               
                               Token t = new Token (((Token)$3.obj).getId()+"'");//ACA NO ME ANDA EL SVAL
                               Terceto terceto = new Terceto("Print",t,new Token("-"));
                               t.setTipo(lexico.CADENA);
                               lexico.getTS().setDeclaradacion(t.getId(),t);
                               ctrlTercetos.agregarTerceto(terceto);
                                }
	|error_print{           // ESTO NO VA ACA VA EN LOS ERRORES
                               Token t = new Token ("error del print");//ACA NO ME ANDA EL SVAL
                               Terceto terceto = new Terceto("Print",t,new Token("-"));
                               ctrlTercetos.agregarTerceto(terceto);
                               
                               
                    }
	;



error_print : PRINT error CADENA ')' {lexico.getLexico().agregarError( "en la linea "+lexico.getFuente().getLinea() +" falta el '(' ");}
	     | PRINT '(' CADENA error {lexico.getLexico().agregarError( " en la linea "+lexico.getFuente().getLinea() +" falta el ')'");}
	     | PRINT CADENA {lexico.getLexico().agregarError( " en la linea "+lexico.getFuente().getLinea() +" falta los '(' ')' ");}
	     ;
//corte desde acaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa

control :case_encabezado  '{' lista_acciones '}' {lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una case");
                                                  ctrlTercetos.desapilarPilaVariablesCase();
                                                 }
        | error_control
	 ;
error_control :
	      	case_encabezado error lista_acciones '}' {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta llave inicio ");
                                                            ctrlTercetos.desapilarPilaVariablesCase();}
	      |	case_encabezado '{' lista_acciones error {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta llave cierre");
                                                            ctrlTercetos.desapilarPilaVariablesCase();}
	      
	      |	CASE  ID   lista_acciones  	error    {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta parentesis y llave");
                                                            ctrlTercetos.desapilarPilaVariablesCase();}
	      | case_encabezado  lista_acciones   error  {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" faltan llaves ");
                                                            ctrlTercetos.desapilarPilaVariablesCase();}
	      ;      

case_encabezado : CASE '(' ID ')'   { Terceto terceto = new Terceto("Encabezado_case",(Token)$3.obj,new Token("-"));
                                       ctrlTercetos.agregarTerceto(terceto); 
                                       ctrlTercetos.apilarPilaCase(terceto);   
                                       
                                     }
                 | CASE  ID ')' { Terceto terceto = new Terceto("Encabezado_case",(Token)$2.obj,new Token("-"));
                                       ctrlTercetos.agregarTerceto(terceto); 
                                       ctrlTercetos.apilarPilaCase(terceto);
                                       lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta parentesis ");
                                     }
                 | CASE '(' ID { Terceto terceto = new Terceto("Encabezado_case",(Token)$3.obj,new Token("-"));
                                       ctrlTercetos.agregarTerceto(terceto); 
                                       ctrlTercetos.apilarPilaCase(terceto);  
                                        lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta parentesis "); 
                                     }
               
                ;

lista_acciones : lista_acciones accion 
		| accion {lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una accion");}
		;


accion : cte ':'DO { 
                    Token comparacion = ctrlTercetos.getTercetoPilaCase().getToken(1);
                    Terceto terceto = new Terceto("=",(Token)$1.obj,comparacion);
                    ctrlTercetos.agregarTerceto(terceto);
                    ////////// creo el terceto con el bf y lo meto en la pila para cuando termine el bloque saber a donde saltar
                    Terceto terc = new Terceto("bf_case",new Token(String.valueOf(ctrlTercetos.getTercetos().size()-1)),null);
                    ctrlTercetos.agregarTerceto(terc);
                    ctrlTercetos.setSaltoCase(terc);
                    ///////////
                   
                    if(!comparacion.getTipoReal().equals(((Token)$1.obj).getTipoReal())){
                        {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" los tipos son incompatibles ");}
                            }
                    }
                bloque  {Terceto terceto = new Terceto("bi_case",null,null);
                         ctrlTercetos.agregarTerceto(terceto); 
                         ctrlTercetos.apilarPilaVariablesCase(terceto);
                        ctrlTercetos.getSaltoCase();
                        }
        | error_accion {}
	 ;

error_accion : cte error DO{Token comparacion = ctrlTercetos.getTercetoPilaCase().getToken(1);
                    Terceto terceto = new Terceto("=",(Token)$1.obj,comparacion);
                    ctrlTercetos.agregarTerceto(terceto);
                    ////////// creo el terceto con el bf y lo meto en la pila para cuando termine el bloque saber a donde saltar
                    Terceto terc = new Terceto("bf_case",new Token(String.valueOf(ctrlTercetos.getTercetos().size()-1)),null);
                    ctrlTercetos.agregarTerceto(terc);
                    ctrlTercetos.setSaltoCase(terc);
                    lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta el : ");
    }

         bloque{        Terceto terceto = new Terceto("bi_case",null,null);
                        ctrlTercetos.agregarTerceto(terceto); 
                        ctrlTercetos.apilarPilaVariablesCase(terceto);
                        ctrlTercetos.getSaltoCase();
    };

//HASTA ACAAAAAAAAAAAAAAAAAAAAAAAAA










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
                                     Terceto terceto = new Terceto("bf",(Token) $3.obj,null);
                                     ctrlTercetos.agregarTerceto(terceto);
                                     ctrlTercetos.apilar(terceto.getNro());
                                    }
             | IF error condicion ')' {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta el '(' de la condicion ");
                                        Terceto terceto = new Terceto("bf",(Token) $3.obj,null);
                                          ctrlTercetos.agregarTerceto(terceto);
                                          ctrlTercetos.apilar(terceto.getNro());
                                           }
             | IF '(' condicion error {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta el ')' de la condicion ");
                                        Terceto terceto = new Terceto("bf",(Token) $3.obj,null);
                                        ctrlTercetos.agregarTerceto(terceto);
                                        ctrlTercetos.apilar(terceto.getNro());
                                        }
             | IF condicion  error   {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" faltan los parentesis de la condicion");
                                             Terceto terceto = new Terceto("bf",(Token) $2.obj,null);
                                           ctrlTercetos.agregarTerceto(terceto);
                                           ctrlTercetos.apilar(terceto.getNro());
                                           }
            ;

seleccion : if_condicion bloque END_IF            {lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una condicion IF");
                                                   ctrlTercetos.desapilar();  
                                                  }
          | if_condicion bloque ELSE{ Terceto terceto = new Terceto("bi",null,null);
                                      ctrlTercetos.agregarTerceto(terceto);
                                      ctrlTercetos.desapilar();
                                      ctrlTercetos.apilar(terceto.getNro());

                                    } bloque END_IF {lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una condicion IF con ELSE");
                                                     ctrlTercetos.desapilar();
                                                    }
          | if_condicion error                      {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" faltan el bloque de la condicion");
                                                      ctrlTercetos.desapilar();
                                                     //MENSAJE ERROR 
                                                    }

          | if_condicion bloque error               {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" faltan end_if");
                                                     ctrlTercetos.desapilar();
                                                   //MENSAJE ERROR 
                                                    }
          ;



asignacion : ID  ASIGNACION  expresion {lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego una asignacion");System.out.println("realice una asignacion");}
                { 
                   Token id = (Token) $1.obj;
                   Token exp = (Token) $3.obj;
                   Terceto terceto;
                   terceto = new Terceto(":=",id,exp);
                   ctrlTercetos.agregarTerceto(terceto);
                   if((lexico.getTS().fueDeclarada(id.getId())&&(lexico.getTS().fueDeclarada(exp.getId())))){
                        if( id.getTipoReal().equals(exp.getTipoReal())){ 
                            //terceto = new Terceto(":=",id,exp);
                           }
                         else{
                              //terceto = new Terceto (":=",id,exp);
                             lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" los tipos de la asignacion son incompatibles "+id.getTipoReal()+exp.getTipoReal());
                             }
                           //ctrlTercetos.agregarTerceto(terceto);  
                    }else{
                        //terceto= new Terceto(":=",id,exp);
                        lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +"alguna de las variables no fue declarada");
                        System.out.println(id.getId()+"HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
                        //ctrlTercetos.agregarTerceto(terceto);
                         }
                  
                    }
           | error_asignacion 
           ;


error_asignacion : ID expresion { System.out.println("Error"); lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta el := de la asignacion ");
                                 Token id = (Token) $1.obj;
                                 Token exp = (Token) $2.obj;
                                 Terceto terceto;
                                 if( id.getTipoReal().equals(exp.getTipoReal())){
                                     terceto = new Terceto(":=",id,exp);
                                 }
                                  else{ lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" los tipos de la asignacion son incompatibles ");
                                        terceto = new Terceto (":=",id,exp);}
                                     
                                 ctrlTercetos.agregarTerceto(terceto);  
                                }
                 | ASIGNACION expresion { System.out.println("Error"); lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta el ID de la asignacion ");
                                          Token id = null;
                                          Token exp = (Token) $2.obj;
                                          System.out.println(" en la linea "+lexico.getFuente().getLinea()+"no hay id no puedo generar bien el terceto");
                                          lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" los tipos de la asignacion son incompatibles ");
                                          Terceto terceto = new Terceto(":=",id,exp);
                                          ctrlTercetos.agregarTerceto(terceto);  
                 }
                 ;




expresion : expresion '+' termino {System.out.println("se hizo una suma ");}{lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una suma");
                                    Token termino1 = (Token) $1.obj;
                                        Token factor = (Token) $3.obj;
                                        String tipo = "incompatibles"; 
                                        if(termino1.getTipoReal().equals(factor.getTipoReal())){
                                                 tipo = termino1.getTipoReal();
                                            }
                                        else{
                                            System.out.println("ERROR, TIPOS INCOMPATIBLES"); 
                                            }
                                            String simbolo = "+";
                        
                                            Terceto terceto = new Terceto(simbolo,termino1,factor);
                                            ctrlTercetos.agregarTerceto(terceto);
                                            Token resultado = new Token(true,String.valueOf(terceto.getNro()));
                                            resultado.setTipoReal(factor.getTipoReal());
                                            System.out.println("estoy imprimiendo el tipo del resultado"+ resultado.getTipoReal());
                                            $$ = new ParserVal(resultado);
                                       }
                                    
                                    
	  | expresion '-' termino {lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una resta");
                                        Token termino1 = (Token) $1.obj;
                                        Token factor = (Token) $3.obj;
                                        String tipo = "incompatibles"; 
                                        if(termino1.getTipoReal().equals(factor.getTipoReal())){
                                                 tipo = termino1.getTipoReal();
                                            }
                                        else{
                                            System.out.println("ERROR, TIPOS INCOMPATIBLES"); 
                                            }
                                        String simbolo = "-" ;                       
                                        Terceto terceto = new Terceto(simbolo,termino1,factor);
                                        ctrlTercetos.agregarTerceto(terceto);
                                        Token resultado = new Token(true,String.valueOf(terceto.getNro()));
                                        resultado.setTipoReal(factor.getTipoReal());
                                        $$ = new ParserVal(resultado);
                                        
                                       
                                  $$= null;    
                                 }
	  | termino {System.out.println("TERMINO a EXPR");
                     $$ = new ParserVal ( (Token) $1.obj);
                    }
          ;

termino : termino '*' factor {lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una multiplicacion");
                                        Token termino1 = (Token) $1.obj;
                                        Token factor = (Token) $3.obj;
                                        String tipo = "incompatibles"; 
                                        if(termino1.getTipoReal().equals(factor.getTipoReal())){
                                                 tipo = termino1.getTipoReal();
                                            }
                                        else{
                                            System.out.println("ERROR, TIPOS INCOMPATIBLES"); 
                                            }
                                            String simbolo = "*";
                                            
                                            Terceto terceto = new Terceto(simbolo,termino1,factor);
                                            ctrlTercetos.agregarTerceto(terceto);
                                            Token resultado = new Token(true,String.valueOf(terceto.getNro()));
                                            resultado.setTipoReal(factor.getTipoReal());
                                            System.out.println("estoy imprimiendo el tipo del resultado"+ resultado.getTipoReal());
                                            $$ = new ParserVal(resultado);
                                
                                    
                                
                             }
	| termino '/' factor {lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una division");
                              Token termino1 = (Token) $1.obj;
                                        Token factor = (Token) $3.obj;
                                        String tipo = "incompatibles"; 
                                        if(termino1.getTipoReal().equals(factor.getTipoReal())){
                                                 tipo = termino1.getTipoReal();
                                            }
                                        else{
                                            System.out.println("ERROR, TIPOS INCOMPATIBLES"); 
                                            }
                                            String simbolo = "/";
                                             tipo = termino1.getTipoReal();
                                            Terceto terceto = new Terceto(simbolo,termino1,factor);
                                            ctrlTercetos.agregarTerceto(terceto);
                                            Token resultado = new Token(true,String.valueOf(terceto.getNro()));
                                            resultado.setTipoReal(factor.getTipoReal());
                                            $$ = new ParserVal(resultado);
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
                                              if(!exp1.getTipoReal().equals(exp2.getTipoReal())){
                                                    lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" hay error de tipos ");
                                                    System.out.println(" en la linea "+lexico.getFuente().getLinea()+"los tipos de los datos no son iguales");
                                               }
                                                        
                                              Terceto terceto = new Terceto(simbolo,exp1,exp2);
                                              ctrlTercetos.agregarTerceto(terceto);
                                              Token resultado = new Token(true,String.valueOf(terceto.getNro()));   
                                              resultado.setTipoReal(exp1.getTipoReal());
                                              $$ = new ParserVal(resultado);
                                              
                                            }
           ;

comparador : '<' { String comparador = " <";
                   $$ = new ParserVal(new Token(comparador));
                   }
	   |  '>'{ String comparador = " >";
                   $$ = new ParserVal(new Token(comparador));
                   }
	   |   '='{ String comparador = "=";
                   $$ = new ParserVal(new Token(comparador));
                   } 
           |  S_MAYOR_IGUAL  {
                                $$ = new ParserVal(new Token(">="));
                                }
	   |  S_MENOR_IGUAL  {
                                $$ = new ParserVal(new Token("<="));
                                }
	   |  S_DISTINTO     {
                                $$ = new ParserVal(new Token("!="));
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

public CtrlTercetos getCtrlTercetos(){
    return ctrlTercetos;
}
