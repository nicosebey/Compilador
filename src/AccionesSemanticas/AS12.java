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



//CHEQUEA VALIDES DEL LONG INT
public class AS12 extends AccSemantica{
    private static final long limite_inf = 0; 
    private static final long limite_sup = (long) (2E32)-1;
    @Override
    public int ejecutar(char c, ArchController ac) {
       
	ac.setConcateno(false);
         boolean error = false;
		try {	
			String constante = ac.getBuffer();
			long largo = Long.parseLong(constante.substring(0, constante.length()-2));
			if (((largo>limite_inf) && (largo < limite_sup))) {
				 ac.creaToken(ac.getBuffer());
                                 ac.añadirTokenTS(ac.getBuffer());
                                 ac.termino();
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
                        return 1;
                }
                
	return 0;	
    }
 
    
}
