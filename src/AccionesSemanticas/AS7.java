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
//SI EL CARACTER QUE LLEGO ES EL = LO CONCATENO Y TERMINO Y DEVUELVO EL TOKEN, SINO LO MISMO SIN CONCATENAR ( el token seria singular (un <)) 

public class AS7 extends AccSemantica{

    @Override
    public int ejecutar(char c, ArchController ac) {
        if(c == '='){
         ac.setBuffer(ac.getBuffer()+c); 
         ac.creaToken(ac.getBuffer(),ac.getFuente().getLinea());
        }
        else{
             ac.setConcateno(false);
             ac.creaTokenSingular(ac.getBuffer().charAt(0),ac.getFuente().getLinea());
             //ac.token().setLexemaSingular(ac.getBuffer().charAt(0));
            }
        ac.añadirTokenTS(ac.getBuffer(),"comparador");
        ac.token().setTipo(ac.getIdentificador(ac.getBuffer()));
        ac.termino();
        /*/---------------PRUEBA--------------------//
                                         System.out.println(ac.getBuffer());
                                 //----------------------------------------/*/
        ac.setEstadofinal();
        return 0;
    }
    
}
