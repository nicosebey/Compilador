/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccionesSemanticas;

import AnalizadorLexico.ArchController;

/**
 *
 * @author nicol
 */
//SI EL CARACTER QUE LLEGO ES EL = LO CONCATENO Y TERMINO Y DEVUELVO EL TOKEN, SINO LO MISMO SIN CONCATENAR

public class AS7 extends AccSemantica{

    @Override
    public int ejecutar(char c, ArchController ac) {
        if(c == '='){
         ac.setBuffer(ac.getBuffer()+c); 
        }
        else{
             ac.setConcateno(false);
        }
        ac.creaToken(ac.getBuffer());
       
        ac.termino();
        
        
        return 0;
    }
    
}
