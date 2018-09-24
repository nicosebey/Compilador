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
 * 
 * 
 * 
 * //DA ERROR YA QUE "_ " Y CUALQUIER COSA QUE VENGA DESPUES QUE CAIGA EN ESTA ACC SEMANTICA NO ES  VALIDO
 */public class AS4 extends AccSemantica {

    @Override
    public int ejecutar(char c, ArchController ac) {
       
      /* ac.creaToken(ac.getBuffer());
       ac.añadirTokenTS(ac.getBuffer());
       ac.setConcateno(false);
       ac.termino();*/
       /*/---------------PRUEBA--------------------//
                                         System.out.println(ac.getBuffer());
        //----------------------------------------/*/
       
        ac.agregarError("caracter invalido:"+c+" NO ES VALIDO");  
       return 1;
    }
 }