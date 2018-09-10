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

//CONCATENA CARACTER = Y DEVULEVE TOKEN

public class AS6 extends AccSemantica{

    @Override
    public int ejecutar(char c, ArchController ac) {
       ac.token().SetCaracter(c);
       //TENEMOS QUE HACER EL CHEQUEO QUE NO EXISTA EN LA TABLA DE IDENTIFICADORES Y DEVOLVER EL VALOR.
       return 0;
    }
    
}
