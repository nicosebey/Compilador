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
//esperaba u y no llego o una l
public class Error4 extends AccSemantica{

    @Override
    public int ejecutar(char c, ArchController ac) {
        if(ac.getBuffer().charAt(ac.getBuffer().length())== 'u')
            ac.agregarError("se esperaba un l y llego un "+c);
        else
            ac.agregarError("se esperaba un u y llego un "+c);
        return 1;
    }
    
}
