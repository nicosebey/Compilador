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
CONCATENA EN EL BUFFER Y TERMINA ESTA ES PARA LOS COMENTARIOS ENTRE ' ' (HAY QUE CREAR TOKEN?)
*/
public class AS8 extends AccSemantica{

    @Override
    public int ejecutar(char c, ArchController ac) {
       ac.setBuffer(ac.getBuffer()+c);
       ac.creaToken(ac.getBuffer());
       ac.añadirTokenTS(ac.getBuffer());
       ac.termino();
       //---------------PRUEBA--------------------//
                                         System.out.println(ac.getBuffer());
                                 //----------------------------------------//
        return 0;
    }
    
}
