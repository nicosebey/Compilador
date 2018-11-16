/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalizadorLexico;

/**
 *
 * 
 * @author nicol
 */
public class Token {
   private String id;
   private int tipo;
   private int linea;
   private boolean esTerceto;
   private String tipoReal;
   private String ambito;
   private boolean visible;
   private boolean negativo;
   private String nombreCte;
   
   
   
   
   
   
   
   public Token(String id){
       this.id = id ;
       esTerceto= false;
       
   }
   public Token (){
       this.id= null;
       this.linea = 1;
       this.negativo = false;
   }
   public Token(boolean terceto,String nroTerceto){
       this.id = nroTerceto;
       this.esTerceto = terceto;
   }

    public void setNegativo(boolean esNegativo) {
        this.negativo = esNegativo;
    }

    public boolean iszarpadNegativo() {
        return negativo;
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
    public void setLinea(int linea) {
        this.linea = linea;
    }
   public int getLinea(){
       return linea;
   }
    public void setTipoReal(String tipoReal) {
        this.tipoReal = tipoReal;
    }
    public String getTipoReal() {
        return tipoReal.toLowerCase();
    }
   public String getNroTerceto(){
       return id;
   }
   public boolean esTerceto(){
       return esTerceto;
   }

    public void setEsTerceto(boolean esTerceto) {
        this.esTerceto = esTerceto;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public String getNombreCte() {
        return nombreCte;
    }

    public void setNombreCte(String nombreCte) {
        this.nombreCte = nombreCte;
    }
   
   
   
   
   
   
   
   
}
