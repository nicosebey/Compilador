/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccionesSemanticas;

import AnalizadorLexico.ArchController;

/**
 *
 * @author nicol prueba
 */
public class Error1 extends AccSemantica{// esperaba un =

    @Override
    public int ejecutar(char c, ArchController ac) {
      ac.termino();
    }
    
}
