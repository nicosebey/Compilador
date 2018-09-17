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


public class AS13 extends AccSemantica{

    @Override
    public int ejecutar(char c, ArchController ac) {
       if(c != '-')
           if(c != '-')
             ac.setBuffer(ac.getBuffer()+c);
             else
                System.out.println("ERROR LA ULTIMA LINEA NO LLEVA GUION");
       return 0;
    }
    
}
