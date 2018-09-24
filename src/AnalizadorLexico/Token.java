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
   private int tipo;
   
   
   
   
   
   public Token(String id){
       this.id = id ;
   }
   public Token (){
       this.id= null;
   }
   
   public String getId(){
       return id;
   }

    public void setLexema(String s){
        id = s;
    }
    public void setLexemaSingular(char c){
        id = String.valueOf(c);
    }
    public void setTipo(int tipo ){
        this.tipo = tipo;
    }
    public int getTipo(){
        return this.tipo;
    }
   
    
   
}
