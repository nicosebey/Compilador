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
//CONCATENA CARACTER AL BUFFER
public class AS3 extends AccSemantica{
  /*  private static final long limite_inf = 0; 
    private static final long limite_sup = (long) (2E32)-1;
     boolean error = false;
    @Override
    public int ejecutar(char c, ArchController ac) {
      String buffer = ac.getBuffer()+c;
      long largo = Long.parseLong(buffer.substring(0, buffer.length()));
      if (((largo>=limite_inf) && (largo <=limite_sup))){
        ac.setBuffer(ac.getBuffer()+c);
       return 0;}
      else
          error = true;
      if (error){
              ac.agregarError("el token que quiere crear esta fuera de rango y su valor es "+largo);
              ac.termino(); 
              ac.setEstadofinal();
              return 1;
      }
          
     
    return 0;
    
}*/
    
    
    public int ejecutar (char c, ArchController ac){
       
        ac.setBuffer(ac.getBuffer()+c);
        return 0;
    }
}
