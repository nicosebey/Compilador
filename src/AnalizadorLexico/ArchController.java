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
        { 3, 1, 2, F, F, F, F, F, 4, F, F, F, F, F, F, 6, 7, 8, 9, 1, 1,10, 0, 2,0 },//0
        {11,12,11, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F,-1},//1
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
        { as3, as3, as3, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4,err7},//1
        { as5, as5, as3, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as3, as3, as5, as5, as3, as5},//2
        { as3, as3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3},//3
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
  
    public static final int S_MAYOR_IGUAL = 257;
    public static final int S_MENOR_IGUAL = 258;
    public static final int S_DISTINTO = 259;
    public static final int S_SE_VUELVE = 260;
    public static final int ID = 261;
    public static final int CADENA = 262;
    public static final int CTE = 263;
    
        
    //PALABRAS RESERVADAS
    public static final int IF = 264;
    public static final int ELSE =265;
    public static final int END_IF = 266;
    public static final int PRINT = 267;
    public static final int USLINTEGER = 268;
    public static final int DOUBLE = 269;
    public static final int CASE = 270;
    public static final int DO = 271;
    public static final int VOID = 272;
    public static final int FUN = 273;
    public static final int RETURN = 274;
    
    
   
    private ArrayList<String> listaPalReservadas=new ArrayList<String>();  //PALABRAS RESERVADAS
    private Token token = new Token();
    private String buffer = new String();
    private TablaSimbolos tablaS = new TablaSimbolos();
    private ArrayList<Token> ltokens=new ArrayList<Token>();;
    private boolean concateno = true;
    private ArrayList <String> errores=new ArrayList<String>();
    
    
    
    
    
    
    
    public ArchController(Fuente codFuente){
        this.codigoF = codFuente;
        //CARGO PALABRAS RESERVADAS A LA LISTA
        listaPalReservadas.add("case");
        listaPalReservadas.add("do");
        listaPalReservadas.add("void");
        listaPalReservadas.add("fun");
        listaPalReservadas.add("return");
       
        
       
       
       
    }
    
    public String getToken(){    
     int estado = 0; //Estado inicial.  
     token = new Token();
     
     while ((codigoF.hasFinished()==false)&&(estado != F)){ 
        concateno = true;
        char c = codigoF.getChar();
        int simbolo = codigoF.getCol(c);
        AccSemantica as = matrizAS[estado][simbolo];
         System.out.println(estado+simbolo);
        if(as.ejecutar(c,this)== 0){
            if(termino){
                return token.getId();
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
     }
     if(estado == F){
         char ch = codigoF.getChar();
         int simbolo2 = codigoF.getCol(ch);
         AccSemantica as1 = matrizAS[estado][simbolo2];
         as1.ejecutar(ch, this);
         return token.getId();
        
     }       
        
     return null;   
        
    }
    
    
    public void recorrerCodFuente(){
        while(!codigoF.hasFinished()){
            System.out.println("token n1: "+getToken());
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
        token.setLexema(lexema);
        ltokens.add(token);
    }

    public void añadirTokenTS(String buffer) {
        tablaS.agregar(buffer);
        
    }

    public void termino() {
        termino = true;
    }

    public void agregarError(String error){
        errores.add(error);
    }
}
    

    

