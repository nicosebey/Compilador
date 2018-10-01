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
       if(!ac.getBuffer().startsWith("-")){
          
           ac.creaToken(ac.getBuffer(),ac.getFuente().getLinea());
           ac.añadirTokenTS(ac.getBuffer(),"cadena");
           ac.termino();
           ac.setEstadofinal();
           ac.setEstadofinal();
           ac.setBuffer("");
           return 0;
       }
        //System.out.println("entro acaaaaaaaaaaaaaaa");
       return 0;
    }
    
    
       
    
}
