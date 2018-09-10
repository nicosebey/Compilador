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
/*
CONCATENA EN EL BUFFER Y TERMINA
*/
public class AS8 extends AccSemantica{

    @Override
    public int ejecutar(char c, ArchController ac) {
        ac.token().SetCaracter(c
        //FALTA HACER EL CHEQUEO SI EXISTE YA EL IDENTIFICADOR
        return 0;
    }
    
}
