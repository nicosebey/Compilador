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
    private AccSemantica as13 = new Error1();
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
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, F,-1,-1,-1}//18
         };
    private AccSemantica [][] matrizAS = {
         //d    _    l    +    *    /    -    =    :    (    )    {    }    ;    ,   !    <    >    .     u    l   ´    /n   D
         //0    1    2    3    4    5    6    7    8    9   10    11  12   13   14   15   16   17   18    19  20   21   22  23
        { as1, as1, as1, as2, as2, as2, as2, as2, as1, as2, as2, as2, as2, as2, as2, as1, as1, as1, as1, as2, as2, as1,  0, as1},//0
        { as3, as3, as3, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4},//1
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
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, F,-1,-1,-1}//18
         };
    private ArrayList <String> listaPalReservadas; //LISTA DE PALABRAS RESERVADAS
    private Token buffer;
    
    
    
    
    
    
    
    public ArchController(Fuente codFuente){
        this.codigoF = codFuente;
       // pos = 0;
       //  actual = buffer.charAt(pos);
       // line = 1;
       
       
        
    }
    
    public int getToken(){
        
     int estado = 0; //Estado inicial.   
     buffer.reset();//reseteo el buffer por si tenia algo de iteraciones anteriores
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
                return buffer.getIdentificador();
            }
        else{//resetea el token vuelve el estado a 0(LLEGO ACA POR UN ERROR)
            estado = 0;
            buffer.reset();
            termino = false;
            }
        }
     
        
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            if (sA.ejecutar(c, this) == 0){ //0 consume.
            	f.increasePosition();
            }
            if (!reset){
  	            estado = mTE [estado][f.getID(c)]; //Dame el nuevo estado
	            if (estado == F){
	                return tokenActual;
	            }
            }
            else { //resetea buffer despues de haber reconocido un error
            	estado = 0;//lo resetea cuando escuentra un error y sigue compilando. NO LE CABE UNA
            	buffer = "";
            	reset = false;
            }
        }
        
        return null; 
         
     }   
        
        
        
    }
    



}
    

    

