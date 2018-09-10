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
        ac.token().SetCaracter(c);
        if(ac.esReservada(ac.token().getBuffer())){
            System.out.println("es una palabra reservada");
            return 555555;//que tenemos que devolver en cada caso?
        }
        else{
            //aca hay que devolver el identificador proque seria un indentificador 
            return 0;
        }
    }
    
}
