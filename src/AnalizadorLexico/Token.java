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
   private String id;
   
   
   
   
   
   
   
   public String getId(){
       return id;
   }

    public void setLexema(String s){
        id = s;
    }
    public void setLexemaSingular(char c){
        id = String.valueOf(c);
    }
            
   
    
   
}
