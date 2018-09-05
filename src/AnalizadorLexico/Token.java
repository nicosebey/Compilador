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
public class Token {
   private String buffer;
   private int identificador;
   
   
   
   
   
   
   public int getIdentificador(){
       return identificador;
   }

    void reset() {
        buffer = "";
        
    }
}
