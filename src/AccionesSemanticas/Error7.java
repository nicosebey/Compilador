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
public class Error7 extends AccSemantica{

    @Override
    public int ejecutar(char c, ArchController ac) {
        ac.agregarError("caracter invalido: el _ por si solo no es valido");  
     return 1;
    }
    
}
