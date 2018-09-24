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
        //System.out.println(ac.getBuffer());
        if(ac.esReservada(ac.getBuffer())){//si es reservada agrego token sino no
            ac.creaToken(ac.getBuffer());
            ac.termino();
            ac.setConcateno(false);
            ac.añadirTokenTS(ac.getBuffer(),"palReserv");
            //ac.añadirTokenLista(ac.token());
            /*/---------------PRUEBA--------------------//
                                         System.out.println(ac.getBuffer());
                                 //----------------------------------------/*/
            return 0;
    }
            else  
            /*/---------------PRUEBA--------------------//
                                         System.out.println(ac.getBuffer());
                                 //----------------------------------------/*/
            System.out.println(ac.getBuffer()+" no es una palabra reservada o esta mal escrita");
            return 1;
        
    }
    
}
