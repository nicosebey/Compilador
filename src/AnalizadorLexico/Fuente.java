/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalizadorLexico;

/**
 *
 * @author nicol
 */
public class Fuente {
    static final char saltoLinea =    '\n';
    static final char finArch = '$';
    private int linea;
    private char actual;

    
    
    //POSICION EN EL ARCHIVO
    private int pos;
    
    
    
    
    //ARCHIVO
    
    private StringBuilder archivo;

    public Fuente(StringBuilder archivo) {
        this.archivo = archivo;
        pos = 0;
        actual = archivo.charAt(pos);
        linea = 1; 
    }
    
    
    public int getLinea (){
        return linea;
    }
    
    
    
    
    
   
    
    public int getCol(char c) {//DEVUELVE EL NUMERO DE COLUMNA DEPENDIENDO EL CHAR QUE LE LLEGA
       // CHAR QUE LE LLEGA
        if (c == 'D')
            return 23;
        if (c == 'u')
            return 19;
        if (c == 'l')
            return 20;
        if (Character.isDigit(c))
            return 2;
        if (Character.isLetter(c))
            return 0;
        switch (c) {
            case '_': {
                return 1;
            }
            case '+': {
                return 3;
            }
            case '*': {
                return 4;
            }
            case '/': {
                return 5;
            }
            case '-': {
                return 6;
            }
            case '=': {
                return 7;
            }
            case ':': {
                return 8;
            }
            case '(': {
                return 9;
            }
            case ')': {
                return 10;
            }
            case '{': {
                return 11;
            }
            case '}': {
                return 12;
            }
            case ';': {
                return 13;
            }
            case ',': {
                return 14;
            }
            case '!': {
                return 15;
            }
            case '<': {
                return 16;
            }
            case '>': {
                return 17;
            }
            case '.': {
                return 18;
            }
            case '´': {
                return 21;
            }
            case '\n': {
                return 22;
            }
            case ' ':{
                return 24;
            }
        }
        return -1;
    }

    public boolean hasFinished() {//TERMINO DE LEER EL CODIGO FUENTE
        return actual == finArch;
        
    }

    public char getChar() {//devuelve el caracter a leer
        return actual;
    }

    public void siguiente() {
        if (actual == saltoLinea)
            linea++;
        if (pos<archivo.length()){
            pos++;
            actual=archivo.charAt(pos);
        }
    }
    
    
}
