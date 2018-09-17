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
//esperaba o un digito o un + o un -
public class Error5 extends AccSemantica{

    @Override
    public int ejecutar(char c, ArchController ac) {
       ac.agregarError(c+"es un caracter invalido, podias ingresar '+'o '-' o un digito");
       return 1;
    }
    
}
