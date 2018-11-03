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
public class CtrlTercetos {
    private int nroTercetoActual;
    private ArrayList<Terceto> tercetos;

    public CtrlTercetos() {
        nroTercetoActual = 0;
        tercetos = new ArrayList<Terceto>();
    }

    public int getNroTercetoActual() {
        return nroTercetoActual;
    }

    public ArrayList<Terceto> getTercetos() {
        return tercetos;
    }
    
    
    
    
}
