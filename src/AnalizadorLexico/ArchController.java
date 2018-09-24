/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalizadorLexico;

import AccionesSemanticas.AS1;
import AccionesSemanticas.AS10;
import AccionesSemanticas.AS11;
import AccionesSemanticas.AS12;
import AccionesSemanticas.AS13;
import AccionesSemanticas.Error1;
import AccionesSemanticas.AS2;
import AccionesSemanticas.AS3;
import AccionesSemanticas.AS4;
import AccionesSemanticas.AS5;
import AccionesSemanticas.AS6;
import AccionesSemanticas.AS7;
import AccionesSemanticas.AS8;
import AccionesSemanticas.AS9;
import AccionesSemanticas.AcBlanco;
import AccionesSemanticas.AccSemantica;
import AccionesSemanticas.Error2;
import AccionesSemanticas.Error3;
import AccionesSemanticas.Error4;
import AccionesSemanticas.Error5;
import AccionesSemanticas.Error6;
import AccionesSemanticas.Error7;
import java.util.ArrayList;

/**
 *
 * @author nicol
 */
public class ArchController {
    
    private Fuente codigoF;
    private boolean termino=false;
    public static int F = 2000;// VAMOS A USAR ESTA F COMO ESTADO FINAL
    
    
    
    private AccSemantica as1 = new AS1();
    private AccSemantica as2 = new AS2();
    private AccSemantica as3 = new AS3();
    private AccSemantica as4 = new AS4();
    private AccSemantica as5 = new AS5();
    private AccSemantica as6 = new AS6();
    private AccSemantica as7 = new AS7();
    private AccSemantica as8 = new AS8();
    private AccSemantica as9 = new AS9();
    private AccSemantica as10 = new AS10();
    private AccSemantica as11 = new AS11();
    private AccSemantica as12 = new AS12();   
    private AccSemantica as13 = new AS13();   
    private AccSemantica err1 = new Error1();
    private AccSemantica err2 = new Error2();
    private AccSemantica err3 = new Error3();
    private AccSemantica err4 = new Error4();
    private AccSemantica err5 = new Error5();
    private AccSemantica err6 = new Error6();
    private AccSemantica err7 = new Error7();
    private AccSemantica blak = new AcBlanco();
//numero de linea actual
         // private int line;
        //private char actual;
    
    //posicion dentro del buffer
        //private int pos;
    
    int [][] matrizTE ={
        //d  _  l  +  *  /  -  =  :  (  )  {  }  ;   ,  !  <  > .  u  l  '  /n D  ´ ´ 
        //0  1  2  3  4  5  6  7  8  9 10  11 12 13 14 15 16 17 18 19 20 21 22 23 24
        { 3, 1, 2, F, F, F, F, F, 4, F, F, F, F, F, F, 6, 7, 8, 9, 2, 2,10, 0, 2,0 },//0
        {11,12,11, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F,11,11, F, F,11,-1},//1
        { F, F, 2, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, 2, 2, F, F, 2,F },//2
        { 3, 5,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, F,-1, 9,-1,-1,-1,-1,-1,-1},//3
        {-1,-1,-1,-1,-1,-1,-1, F,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//4
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,18,-1,-1,-1,-1,-1},//5
        {-1,-1,-1,-1,-1,-1,-1, F,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//6
        { F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F,F },//7
        { F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F,F },//8
        { 9, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F,13,F },//9
        {10,10,10,10,10,10,15,10,10,10,10,10,10,10,10,10,10,10,10,10,10, F,10,10,10},//10
        {11, F,11, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F,11,11, F, F,11,F },//11
        {12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12, F,12,12},//12 CHEQUEAR SI LA F VA AHI O EN '
        {14,-1,-1,16,-1,-1,17,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//13
        {14, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F,F },//14
        {10,10,10,10,10,10,15,10,10,10,10,10,10,10,10,10,10,10,10,10,10,-1,10,10,10},//15
        {14,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//16 
        {14,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//17
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, F,-1,-1,-1,-1} //18
         };
    private AccSemantica [][] matrizAS = {
         //d    _    l    +    *    /    -    =    :    (    )    {    }    ;    ,   !    <    >    .     u    l   ´    /n   D    ' '
         //0    1    2    3    4    5    6    7    8    9   10    11  12   13   14   15   16   17   18    19  20   21   22  23    24
        { as1, as1, as1, as2, as2, as2, as2, as2, as1, as2, as2, as2, as2, as2, as2, as1, as1, as1, as1, as2, as2, as1,as13, as1,blak},//0    ES VALIDO ESE NULL?
        { as3, as3, as3, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as3, as3, as4, as4, as3,err7},//1
        { as5, as5, as3, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as3, as3, as5, as5, as3, as5},//2
        { as3, as3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3, as3,err3,err3,err3,err3,err3,err3},//3
        {err1,err1,err1,err1,err1,err1,err1, as6,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1},//4
        {err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4, as3,err4,err4,err4,err4,err4},//5
        {err1,err1,err1,err1,err1,err1,err1, as6,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1},//6
        { as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7,as7 },//7
        { as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7,as7 },//8
        { as3,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11, as3,as11},//9
        { as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as8, as9, as9,as9 },//10
        { as3,as10, as3,as10,as10,as10,as10,as10,as10,as10,as10,as10,as10,as10,as10,as10,as10,as10,as10, as9, as9,as10,as10, as3,as10},//11
        { as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as8, as9, as9,as9 },//12
        { as3,err5,err5, as3,err5,err5, as3,err5,err5,err5,err5,err5,err5,err5,err5,err5,err5,err5,err5,err5,err5,err5,err5,err5,err5},//13
        { as3,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11},//14
        {as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,err6,as13,as13,as13},//15
        { as3,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2},//16
        { as3,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2},//17
        {err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,as12,err4,err4,err4,err4} //18
         };
    
     //CONSTANTES  IDENTIFICADORES
  
    public final static short S_MAYOR_IGUAL=257;
public final static short S_MENOR_IGUAL=258;
public final static short S_DISTINTO=259;
public final static short COMENTARIO=262;
public final static short CADENA=263;
public final static short CTE_D=260;
public final static short CTE_USLINTEGER=261;
public final static short ID=264;
public final static short ASIGNACION=275;

    
        
    //PALABRAS RESERVADAS
    public static final int IF = 265;
    public static final int THEN = 266;
    public static final int ELSE =267;
    public static final int END_IF = 268;
    public static final int DO = 269;
    public static final int FUN = 270;
    public static final int DOUBLE = 271;
    public static final int USLINTEGER = 272;
    public static final int RETURN = 273;
    public static final int PRINT = 274;
    public static final int CASE = 276;
  
  

   
    
    
    
    
    
   
    private ArrayList<String> listaPalReservadas=new ArrayList<String>();  //PALABRAS RESERVADAS
    private Token token = new Token();
    private String buffer = new String();
    private TablaSimbolos tablaS = new TablaSimbolos();
    private ArrayList<Token> ltokens=new ArrayList<Token>();;
    private boolean concateno = true;
    private ArrayList <String> errores=new ArrayList<String>();
    private boolean esComentario = false;
    private int estado;
    
    
    
    
    
    
    
    public ArchController(Fuente codFuente){
        this.codigoF = codFuente;
        //CARGO PALABRAS RESERVADAS A LA LISTA
        listaPalReservadas.add("case");
        listaPalReservadas.add("do");
        listaPalReservadas.add("void");
        listaPalReservadas.add("fun");
        listaPalReservadas.add("return");
        listaPalReservadas.add("if");
        listaPalReservadas.add("else");
        listaPalReservadas.add("uslinteger");
        listaPalReservadas.add("end_if");
        listaPalReservadas.add("print");
        listaPalReservadas.add("id");
        
        estado = 0;
       
       
       
    }
    
    public Token getToken(){    
     estado = 0; //Estado inicial.  
     token = new Token();
     buffer = "";
     int prueba = 0;
     while ((codigoF.hasFinished()==false)&&(estado != F)){ 
        concateno = true;
        termino = false;
        char c = codigoF.getChar();
        int simbolo = codigoF.getCol(c);
        AccSemantica as = matrizAS[estado][simbolo];
         //System.out.println("matriz["+estado+"]"+"["+simbolo+"]");
         /*/-----------------PRUEBA----------------
        System.out.println("prueba"+prueba);
         System.out.println(estado);
         System.out.println(simbolo);
         prueba++;
        //-------------------------------------------*/
        if(as.ejecutar(c,this)== 0){
            if(termino){
                //System.out.println(token.getId()+"IDDDDD");
                //System.out.println(buffer);
                //System.out.println(getIdentificador(buffer)+"id");
                codigoF.siguiente();
                if(!getComentario())
                    return  ltokens.get(ltokens.size()-1);
               // else return null;//getIdentificador(buffer);
            }
            else{
                if(concateno){
                    codigoF.siguiente();
                }
            estado = matrizTE[estado][simbolo];
            }
        }
        else{//llego aca porque dio error (la acc semantica no devolvio 0
             estado = 0;
            buffer= "";
            termino = false;
            codigoF.siguiente();
        }
     }/*
     if(estado == F){
         char ch = codigoF.getChar();
         int simbolo2 = codigoF.getCol(ch);
         AccSemantica as1 = matrizAS[estado][simbolo2];
         as1.ejecutar(ch, this);
         codigoF.siguiente();
         //System.out.println(buffer);
         //System.out.println(getIdentificador(buffer)+"id");
         if(!getComentario())
              return  ltokens.get(ltokens.size()-1);
           // else return null;
        
     } */
        
     return null;   
        
    }
    
    public int getIdentificador(String lexema){
 
         if (esReservada(lexema)){
            switch (lexema){
                case "if": return IF;
                case "then": return THEN;
                case "else": return ELSE;
                case "end_if": return END_IF;
                case "case": return CASE;
                case "do": return DO;
                case "void": return VOID;
                case "fun": return FUN;
                case "return": return RETURN;
                case "print": return PRINT;
               
                default: return ID;
            }
        }

         else 
             if(lexema.length()>1){
                 //System.out.println(lexema);
                
                if(lexema.equals("!="))
                    return S_DISTINTO;
                else
                if(lexema.equals(">="))
                    return S_MAYOR_IGUAL;
                else
                if(lexema.equals("<="))
                    return S_MENOR_IGUAL;
                
                else/*
                if(lexema.charAt(0)==''' && valor.charAt(valor.length()-1)=='\'')*/
                    return CADENA;
            }
         //System.out.println("aaaaa");
         return (int) lexema.charAt(0);//ESTE ES EL CASO DE LOS TOKEN SIMPLES ( { ) } ; ETC
        }
        
        
        
        
        
        
    
    public boolean getComentario(){
        return esComentario;
    }
    
    public Fuente getCodFuente(){
        return codigoF;
    }
    
    
    public void recorrerCodFuente(){
        
        while(!codigoF.hasFinished()){
           // System.out.println("token n1: "+getToken());
           token = getToken();
         
            
           
        }
        
    }
    public void setConcateno(boolean concateno){
        this.concateno = concateno;
    }
    
    
    public Token token(){
        return token;
    }

    public String getBuffer(){//devuelve el buffer
        return buffer;
    }
    
    public void setBuffer(String s){
        buffer =s;
    }
    
    public boolean esReservada(String palabra){
        if (listaPalReservadas.contains(palabra))
            return true;
        else
            return false;
    }

    public void inicBuffer(char c) {
        buffer = ""+c;
    }

    public void creaToken(String lexema){
        
        Token token = new Token(lexema);
        token.setLexema(lexema);
        ltokens.add(token);
    }
    public void creaTokenSingular(char c){
        Token token2 = new Token();
        token2.setLexemaSingular(c);
        ltokens.add(token2);
    }

    public void añadirTokenTS(String buffer,String tipo) {
        tablaS.agregar(buffer,tipo);
        
        
    }
    public void añadirTokenLista(Token token){
        ltokens.add(token);
    }
    
    public TablaSimbolos getTS(){
        return tablaS;
    }

    public void termino() {
        termino = true;
    }

    public void agregarError(String error){
        errores.add(error);
    }
    public void mostrarErrores(){
       System.out.println(errores.size()+"ERRORES ENCONTRADOS");
        for (String s :errores) 
            System.out.println(s);
    }
    public void mostrarTokens(){
        System.out.println(ltokens.size()+"TOKENS ENCONTRADOS");
        for (Token t :ltokens) {
            System.out.println(t.getId());
            System.out.println(tablaS.getL(t.getId()));
            
            }
    }

    public void setEsComentario(boolean comentario) {
       esComentario = comentario;
    }

    public void setEstadofinal() {
        estado = F; //To change body of generated methods, choose Tools | Templates.
    }
}
    

    

