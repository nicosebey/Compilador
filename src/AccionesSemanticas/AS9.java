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
//CONCATENAR LOS CARACTERES EN EL BUFFER(PODRIA HABER SIDO EL 3 PERO PRIMERO ENTENDIMOS MAL)
public class AS9 extends AccSemantica{

    @Override
    public int ejecutar(char c, ArchController ac) {
       if(c != '\n')
          if(c != '-')
             ac.setBuffer(ac.getBuffer()+c);   
    return 0;   
    }
    
}
