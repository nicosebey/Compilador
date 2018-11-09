/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodigoIntermedio;

import AnalizadorLexico.Token;
import java.util.ArrayList;

/**
 *
 * @author nicol
 */
public class CtrlTercetos {
    private int nroTercetoActual;
    private ArrayList<Terceto> tercetos;
    private ArrayList<Integer> pila;

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
    public void agregarTerceto(Terceto t){
        t.setNumero(tercetos.size());
        tercetos.add(t);
    }
    public String getNroStringTerceto(){
        return String.valueOf(nroTercetoActual);
    }
    public void apilar(int nroTerceto){
        pila.add(nroTerceto);
    }
    public void desapilar(){
        int tercetoAcompletar = pila.get(pila.size()-1);
        pila.remove(pila.size()-1);
        Terceto nuevo = tercetos.get(tercetoAcompletar);
        Token t = new Token( String.valueOf(tercetos.size()) ); 
        t.setEsTerceto(false); 
        if (nuevo.getToken(1) == null)
            nuevo.setElemento(1, t);
        else
            nuevo.setElemento(2, t);
        tercetos.set(tercetoAcompletar, nuevo);
        
        
    }
    
}
