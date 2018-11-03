/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodigoIntermedio;

import AnalizadorLexico.Token;

/**
 *
 * @author nicol
 */
class TercetoSimple {
    private Token token;
    private boolean esToken;
    
    public TercetoSimple(Token token){
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
    
    
}
