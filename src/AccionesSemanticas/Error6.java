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
public class Error6 extends AccSemantica{// un comentario no puede terminar luego de un -

    @Override
    public int ejecutar(char c, ArchController ac) {
        ac.agregarError("la ultima linea de una cadena de caracteres no lleva -");
        return 1;
    }
    
}
