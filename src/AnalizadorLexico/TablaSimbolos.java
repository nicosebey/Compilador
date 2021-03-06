/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalizadorLexico;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

/**
 *
 * @author nicol
 */
public class TablaSimbolos {
 private static Hashtable<String,String> tSimbol = new Hashtable<String,String>();
 private static Hashtable<String,Token> declaradas = new Hashtable<String,Token>();
 private int contadorCte=0;


public void agregar(String lexema,String tipo){
    if (tSimbol.containsKey(lexema))
        System.out.println("el identificador "+lexema+" ya existe");
        else
            tSimbol.put(lexema,tipo);
    }


public String getL(String s){
    if (tSimbol.containsKey(s))
        return tSimbol.get(s);
    else
        return null;
}

public boolean existeL(String s){
    return(tSimbol.containsKey(s)); 
}

    public static Hashtable<String, String> gettSimbol() {
        return tSimbol;
    }

public void setDeclaracion(String lexema, Token token ){
    if(Character.isDigit(lexema.charAt(0))){
        token.setNombreCte("c"+this.contadorCte);
        contadorCte++;
    }
    declaradas.put(lexema, token);
}

public boolean fueDeclarada(String lexema){

    if (declaradas.containsKey(lexema) == true)
            
               return true;
    else 
            return false;
}


public Token getToken(String lexema){
    return declaradas.get(lexema);
}

    public  Hashtable<String, Token> getDeclaradas() {
        return declaradas;
    }

    public TablaSimbolos() {
        this.declaradas = new Hashtable<String,Token>();
    }


public void setInvisibles(String ambito){
    //por toda la lista de declaradas si el ambito == ambito setear visible(false);
}

public Collection<Token> getTokens(){
                return declaradas.values();
        
    }
public String getAssembler() {
        String assembler="";
        Collection<Token> tokens= getTokens();
        
        for(Token t: tokens){
            
            if(t.getTipo() == ArchController.CTE_D){
                assembler = assembler + t.getNombreCte() + " " + "DQ" + " " + t.getId()+'\n';
            }
            else{
                if(t.getTipo()== ArchController.CTE_USLINTEGER){

                    assembler = assembler +t.getNombreCte()  + " " + "DD"+ " " + t.getId() + '\n';
                }
                else{  
                    if (t.getTipo() == ArchController.CADENA){  
                        
                        
                        assembler = assembler + "cadena1" + " " + "db" + " " + t.getId() + " " + "," + " " + "0"+'\n';
                    }
                    else{
                        //CASO VARIABLES
                        
                        String tipoAssembler = getTipoAssember(t);
                        assembler = assembler + t.getId()+ " " + tipoAssembler + " "+ "?" + '\n';
                    }
                }
            }
        }
        return assembler;
    }

    private String getTipoAssember(Token t) {
        String tipo = "";
        //AnalizadorLexico analizador = new AnalizadorLexico(null, null); //es para usar las constantes

        if ( t.getTipoReal().equals("double") || t.getTipoReal().equals("uslinteger")){ //CASO DE VARIABLES
            if (t.getTipoReal().equals("double"))
                tipo = "DQ"; //dq = 64 bits
            else
                tipo = "DD"; //dd = 32 bits

        }
        return tipo;
    }

}
