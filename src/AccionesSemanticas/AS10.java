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
//CHEQUEA QUE LA LONGITUD DEL TOKEN NO SEA MAYOR A 25
public class AS10 extends AccSemantica {

    @Override
    public int ejecutar(char c, ArchController ac) {
        ac.setConcateno(false);
        
        if(ac.getBuffer().length() >25 ){
            System.out.println("la longitud del identificador sobrepasa el maximo");
            return 1;
        }else{
            if(!ac.getTS().existeL(ac.getBuffer())){
               ac.creaToken(ac.getBuffer());
               ac.añadirTokenTS(ac.getBuffer(),"identificador");
               ac.termino();
               return 0;}
            else
                System.out.println("el TOKEN YA EXISTE");
                            /*/---------------PRUEBA--------------------//
                                         System.out.println(ac.getBuffer());
                                 //----------------------------------------/*/
                            
        
            return 1;
        }
    }
    
}
