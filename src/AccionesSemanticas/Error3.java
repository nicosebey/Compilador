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
//esperaba - o .
public class Error3 extends AccSemantica {


    @Override
    public int ejecutar(char c, ArchController ac) {
       ac.agregarError("se esperaba un '-' o un '.' y llego un  "+c);
        
        return 1;
    }
    
}
