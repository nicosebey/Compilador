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


public void agregar(String lexema){
    if (tSimbol.containsKey(lexema))
        System.out.println("el identificador ya existe");
        else
            tSimbol.put(lexema,"tipo");
    }


public String getL(String s){
    if (tSimbol.containsKey(s))
        return s;
    else
        return null;
}

public boolean existeL(String s){
    return(tSimbol.containsKey(s)); 
}

}
