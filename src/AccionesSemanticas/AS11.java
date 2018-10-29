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
    private double doble = 0;
    @Override
    public int ejecutar(char c, ArchController ac) {
    ac.setConcateno(false);
    boolean error = false;
		try {
			String constante = ac.getBuffer();
                        
                        //System.out.println(constante+"anibaaaaaaaaaaaaaaaaaal");
                        String constanteR=constante.replace("D","E");
                        //System.out.println(constanteR+"a4aaaaaaa");
			if (constante.startsWith(".")){
                            constanteR = "0" + constanteR;
                        
			                 
			 
                            
                        }
                        doble = Double.parseDouble(constanteR);
                       // doble = Double.parseDouble(constanteR);
                        
			if (((doble>=limite_inf) && (doble <= limite_sup))|| doble==cero) {
				                       
                                 ac.creaToken(String.valueOf(doble),ac.getFuente().getLinea());  //ACA EL TOKEN SIGUE TENIENDO LA D en vez de la E DEL EXPONENTE QUE ESTA BIEN?
                                 ac.token().setTipo(ac.getIdentificador(constanteR));
                                 ac.añadirTokenTS(String.valueOf(doble),"double");
                                 ac.termino();
                                 /*/---------------PRUEBA--------------------//
                                         System.out.println(ac.getBuffer());
                                 //----------------------------------------/*/
                                
                                 ac.token().setLinea(ac.getFuente().getLinea());
                                 ac.setBuffer("");
                                 ac.setEstadofinal();
                                 
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
                        ac.agregarError("el token que quiere crear esta fuera de rango y su valor es "+doble);
                          ac.termino(); 
                          ac.setEstadofinal();
                          
                        return 1;
                }
               
	return 0;	
	}
    
    
}
