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

//COMO LLEGA UN CARACTER VALIDO ( EL = )CONCATENA Y DEVUELVE TOKEN 

public class AS6 extends AccSemantica{

    @Override
    public int ejecutar(char c, ArchController ac) {
         ac.setBuffer(ac.getBuffer()+c);
         ac.creaToken(ac.getBuffer());
         ac.añadirTokenTS(ac.getBuffer(),"SeVuelve");
         //ac.añadirTokenLista(ac.token());
         ac.termino();
         /*/---------------PRUEBA--------------------//
                                         System.out.println(ac.getBuffer());
                                 //----------------------------------------/*/
         ac.setEstadofinal();
         return 0;   
        
    }
    
}
