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
import AccionesSemanticas.AccSemantica;
import AccionesSemanticas.Error2;
import AccionesSemanticas.Error3;
import AccionesSemanticas.Error4;
import AccionesSemanticas.Error5;
import AccionesSemanticas.Error6;
import java.util.ArrayList;

/**
 *
 * @author nicol
 */
public class ArchController {
    static final char saltoLinea =    '\n';
    static final char finArch = '$';
    //codigo fuente
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
//numero de linea actual
         // private int line;
        //private char actual;
    
    //posicion dentro del buffer
        //private int pos;
    
    int [][] matrizTE ={
        //d  _  l  +  *  /  -  =  :  (  )  {  }  ;   ,  !  <  > .  u  l  ´  /n D
        //0  1  2  3  4  5  6  7  8  9 10  11 12 13 14 15 16 17 18 19 20 21 22 23
        { 3, 1, 2, F, F, F, F, F, 4, F, F, F, F, F, F, 6, 7, 8, 9, F, F,10, 0, 2},//0
        {11,12,11, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F},//1
        { F, F, 2, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, 2, 2, F, F, 2},//2
        { 3, 5,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, F,-1, 9,-1,-1,-1,-1,-1},//3
        {-1,-1,-1,-1,-1,-1,-1, F,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//4
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,18,-1,-1,-1,-1},//5
        {-1,-1,-1,-1,-1,-1,-1, F,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//6
        { F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F},//7
        { F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F},//8
        { 9, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F,13},//9
        {10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10, F,10,10},//10
        {11, F,11, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F,11},//11
        {12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12, F,12},//12
        {14,-1,-1,16,-1,-1,17,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//13
        {14, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F, F},//14
        {10,10,10,10,10,10,15,10,10,10,10,10,10,10,10,10,10,10,10,10,10,-1,10,10},//15
        {14,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//16
        {14,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//17
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, F,-1,-1,-1} //18
         };
    private AccSemantica [][] matrizAS = {
         //d    _    l    +    *    /    -    =    :    (    )    {    }    ;    ,   !    <    >    .     u    l   ´    /n   D
         //0    1    2    3    4    5    6    7    8    9   10    11  12   13   14   15   16   17   18    19  20   21   22  23
        { as1, as1, as1, as2, as2, as2, as2, as2, as1, as2, as2, as2, as2, as2, as2, as1, as1, as1, as1, as2, as2, as1,as13, as1},//0
        { as3, as3, as3, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4},//1
        { as5, as5, as3, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as3, as3, as5, as5, as3},//2
        { as3, as3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3,err3},//3
        {err1,err1,err1,err1,err1,err1,err1, as6,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1},//4
        {err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4, as3,err4,err4,err4,err4},//5
        {err1,err1,err1,err1,err1,err1,err1, as6,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1,err1},//6
        { as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7},//7
        { as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7},//8
        { as3,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11, as3},//9
        { as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as8, as9, as9},//10
        { as3,as10, as3,as10,as10,as10,as10,as10,as10,as10,as10,as10,as10,as10,as10,as10,as10,as10,as10,as10,as10,as10,as10, as3},//11
        { as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as8, as9, as9},//12
        { as3,err5,err5, as3,err5,err5, as3,err5,err5,err5,err5,err5,err5,err5,err5,err5,err5,err5,err5,err5,err5,err5,err5,err5},//13
        { as3,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11,as11},//14
        {as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,as13,err6,as13,as13},//15
        { as3,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2},//16
        { as3,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2,err2},//17
        {err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,err4,as12,err4,err4,err4} //18
         };
   
    private ArrayList <String> listaPalReservadas;  //PALABRAS RESERVADAS
    private Token token;
    private String buffer = new String();
    
    
    
    
    
    
    
    
    public ArchController(Fuente codFuente){
        this.codigoF = codFuente;
        //CARGO PALABRAS RESERVADAS A LA LISTA
        listaPalReservadas.add("case");
        listaPalReservadas.add("do");
        listaPalReservadas.add("void");
        listaPalReservadas.add("fun");
        listaPalReservadas.add("return");
       // pos = 0;
       
       // line = 1;
       
       
        
    }
    
    public int getToken(){
        
     int estado = 0; //Estado inicial.   
     token = null;
     while ((codigoF.hasFinished())&&(estado != F)){ 
        char c = codigoF.getChar();
        int simbolo = codigoF.getCol(c);
        AccSemantica as = matrizAS[estado][simbolo];
        if(as.ejecutar(c,this)== 0){// si devuelve 0 significa que consumio el 
            codigoF.siguiente();
            simbolo = codigoF.getCol(c);
        }
        if(!termino){
            estado = matrizTE[estado][simbolo];
            if(estado == F){// Devuelve el identificador del token
                return token.getIdentificador();
            }
        else{//resetea el token vuelve el estado a 0(LLEGO ACA POR UN ERROR)
            estado = 0;
            buffer= "";
            termino = false;
            }
        }
     
    
         
     }   
        
     return 0;   
        
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


}
    

    

