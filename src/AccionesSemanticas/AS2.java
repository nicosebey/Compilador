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
//INICIALIZA BUFFER Y COMO SON TOKENS DIRECTOS ( + / * ( ) , ETC) LOS DEVUELVE
public class AS2 extends AccSemantica{

    @Override
    public int ejecutar(char c, ArchController ac) {
        ac.inicBuffer(c);
        
        ac.creaTokenSingular(c);
        ac.token().setTipo(c);
        ac.añadirTokenTS(ac.getBuffer(),"unarios");
        /*/---------------PRUEBA--------------------//
                                         System.out.println(ac.getBuffer());
         //----------------------------------------/*/
        ac.termino();
        ac.setEstadofinal();
        return 0;    
    }
    
}
