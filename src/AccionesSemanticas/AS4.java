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
 */public class AS4 extends AccSemantica {

    @Override
    public int ejecutar(char c, ArchController ac) {
       
       ac.creaToken(ac.getBuffer());
       ac.añadirTokenTS(ac.getToken());
       ac.setConcateno(false);
       ac.termino();
       return 0;
    }
 }