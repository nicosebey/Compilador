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

//Inicializa el buffer
public class AS1 extends AccSemantica{

    @Override
    public int ejecutar(char c, ArchController ac) {
        ac.inicBuffer(c);
        return 0;
    
    }
    
}
