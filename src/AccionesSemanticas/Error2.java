/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccionesSemanticas;

import AnalizadorLexico.ArchController;

/**
 *
 * @author nicol merge prueba 7777
 */
public class Error2 extends AccSemantica{//esperaba un digito 

    @Override
    public int ejecutar(char c, ArchController ac) {
        ac.agregarError("caracter invalido: se esperaba un digito y llego un "+c);
        return 1;
    }
    
}
