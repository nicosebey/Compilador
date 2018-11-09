/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalizadorLexico;

import java.util.Hashtable;

/**
 *
 * @author nicol
 */
public class TablaSimbolos {
 private static Hashtable<String,String> tSimbol = new Hashtable<String,String>();
 private static Hashtable<String,Token> declaradas = new Hashtable<String,Token>();


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

}
