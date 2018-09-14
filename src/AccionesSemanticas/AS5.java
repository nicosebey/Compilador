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

//CHEQUEA PAALABRAS RESERVADAS;
public class AS5 extends AccSemantica {

    @Override
    public int ejecutar(char c, ArchController ac) {
        
        if(ac.esReservada(ac.getBuffer())){//si es reservada agrego token sino no
            ac.creaToken(ac.getBuffer());
            ac.termino();
            return 0;
    }
            else  
        return 1;
        
    }
    
}
