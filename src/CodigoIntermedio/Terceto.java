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
public class Terceto {
    
    private int nroTerceto;
    private Token t1;
    private Token t2;
    private String operador;
    
    
    public Terceto(String uno, Token dos, Token tres){
           
           this.operador = uno;
           this.t1 = dos;
           this.t2 = tres;
           this.nroTerceto = nroTerceto;
    }

    void setNumero(int i) {
        this.nroTerceto = i;
    }
    public int getNro(){
        return  nroTerceto;
    }  
}
