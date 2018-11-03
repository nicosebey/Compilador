/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodigoIntermedio;

import java.util.ArrayList;

/**
 *
 * @author nicol
 */
public class Terceto {
    
    private int nroTerceto;
    private ArrayList<TercetoSimple> terceto;
    
    
    public Terceto(TercetoSimple uno, TercetoSimple dos, TercetoSimple tres, int nroTerceto){
           terceto = new ArrayList<TercetoSimple>();
           terceto.add(uno);
           terceto.add(dos);
           terceto.add(tres);
           this.nroTerceto = nroTerceto;
    }
            
}
