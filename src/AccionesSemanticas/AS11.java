/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccionesSemanticas;

import AnalizadorLexico.ArchController;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nicol
 */
//CHEQUEA  VALIDEZ DEL DOUBLE
public class AS11 extends AccSemantica{
    private static final double limite_inf = 2.2250738585072014E-308; 
    private static final double limite_sup = 1.7976931348623157E308;
    private static final double cero = 0.0;
    @Override
    public int ejecutar(char c, ArchController ac) {
    ac.setConcateno(false);
    boolean error = false;
		try {
			String constante = ac.getBuffer();
                        String constanteR=constante.replace("D","E");
                        //System.out.println(constanteR+"aaaaaaaaaaaa");
			if (constante.startsWith(".")) constanteR = "0" + constanteR;
			
			Float doble = Float.parseFloat(constanteR);
			if (((doble>limite_inf) && (doble < limite_sup))|| doble==cero) {
				 ac.creaToken(ac.getBuffer());  //ACA EL TOKEN SIGUE TENIENDO LA D en vez de la E DEL EXPONENTE QUE ESTA BIEN?
                                 ac.añadirTokenTS(ac.getBuffer(),"double");
                                 ac.termino();
                                 /*/---------------PRUEBA--------------------//
                                         System.out.println(ac.getBuffer());
                                 //----------------------------------------/*/
                                 return 0;
			}
			else
				error = true;
		}
		catch (Exception ex) {
			error = true;
                        Logger.getLogger(ArchController.class.getName()).log(Level.SEVERE, null, ex);
			}
		if (error){
                        
			//ac.addError("Linea: " + a.getCode().getLine() + ": Constante double fuera de rango.");
                        /*/---------------PRUEBA--------------------//
                                         System.out.println(ac.getBuffer());
                                 //----------------------------------------/*/
                                 
                        return 1;
                }
                
	return 0;	
	}
    
    
}
