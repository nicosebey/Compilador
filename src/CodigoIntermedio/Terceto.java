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
    private CtrlTercetos controladorTercetos;
    
    public Terceto(String uno, Token dos, Token tres){
           
           this.operador = uno;
           this.t1 = dos;
           this.t2 = tres;
           
    }

    void setNumero(int i) {
        this.nroTerceto = i;
    }
    public int getNro(){
        return  nroTerceto;
    }  

    public Token getToken(int i) {
        if(i == 1)
            return t1;
        else
            return t2;
    }

    void setElemento(int i, Token nuevo) {
            if (i == 1)
                t1 = nuevo;
            else
                t2 = nuevo;
    }
    public void ImprimirTerceto(){
        if(t1!=null)
            if(t2!=null)
                System.out.println("Terceto nro "+nroTerceto+"["+operador+","+t1.getId()+","+t2.getId()+ "]");
            else
                System.out.println("Terceto nro "+nroTerceto+"["+operador+","+t1.getId()+","+ "-]");
        else
            if(t2!=null)
                System.out.println("Terceto nro "+nroTerceto+"["+operador+","+"-"+","+t2.getId()+ "]");
            else
                System.out.println("Terceto nro "+nroTerceto+"["+operador+","+"-"+","+"-]");
                }
    
    //MODIFICAR LAS PALABRAS DE LOS CASE
    public String getAssembler() {

        String code= "";
        switch(operador) {
            case "+":
                //caso 1: (OP, variable, variable)
                if ( ( !this.t1.esTerceto() ) && ( !this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "ADD" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "ADD" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                }
                //caso 2: (OP, terceto, variable)
                if ( ( this.t1.esTerceto() ) && ( !this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "ADD" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "ADD" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                }
                //caso 3: (OP, variable, terceto)
                if ( ( !this.t1.esTerceto() ) && ( this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "ADD" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "ADD" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                }
                //caso 4: (OP, terceto, terceto)
                if ( ( this.t1.esTerceto() ) && ( this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "ADD" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "ADD" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                }
            return code;
            case "-":
                //caso 1: (OP, variable, variable)
                if ( ( !this.t1.esTerceto() ) && ( !this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "SUB" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "SUB" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                }
                //caso 2: (OP, terceto, variable)
                if ( ( this.t1.esTerceto() ) && ( !this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "SUB" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "SUB" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                }
                //caso 3: (OP, variable, terceto)
                if ( ( !this.t1.esTerceto() ) && ( this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "SUB" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "SUB" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                }
                //caso 4: (OP, terceto, terceto)
                if ( ( this.t1.esTerceto() ) && ( this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "SUB" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "SUB" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                }
                return code;
            case "*":
                //caso 1: (OP, variable, variable)
                if ( ( !this.t1.esTerceto() ) && ( !this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "MUL" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "MUL" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                }
                //caso 2: (OP, terceto, variable)
                if ( ( this.t1.esTerceto() ) && ( !this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "MUL" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "MUL" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                }
                //caso 3: (OP, variable, terceto)
                if ( ( !this.t1.esTerceto() ) && ( this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "MUL" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "MUL" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                }
                //caso 4: (OP, terceto, terceto)
                if ( ( this.t1.esTerceto() ) && ( this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "MUL" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "MUL" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + "aux1"+ "," + "R1";
                    }
                }
                return code;
            case "/":

                //caso 1: (OP, variable, variable)
                if ((!this.t1.esTerceto()) && (!this.t2.esTerceto())) {
                    if (this.t1.getTipoReal().equals("double")) {
                        code = "MOV" + " " + "R1" + "," + this.t1.getId() + "\n";
                        code += "DIV" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code += "MOV" + " " + "aux1" + "," + "R1";
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code = "MOV" + " " + "R1" + "," + this.t1.getId() + "\n";
                        code += "DIV" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code += "MOV" + " " + "aux1" + "," + "R1";
                    }
                }
                //caso 2: (OP, terceto, variable)
                if ((this.t1.esTerceto()) && (!this.t2.esTerceto())) {
                    if (this.t1.getTipoReal().equals("double")) {
                        code = "MOV" + " " + "R1" + "," + this.t1.getId() + "\n";
                        code += "DIV" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code += "MOV" + " " + "aux1" + "," + "R1";
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code = "MOV" + " " + "R1" + "," + this.t1.getId() + "\n";
                        code += "DIV" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code += "MOV" + " " + "aux1" + "," + "R1";
                    }
                }
                //caso 3: (OP, variable, terceto)
                if ((!this.t1.esTerceto()) && (this.t2.esTerceto())) {
                    if (this.t1.getTipoReal().equals("double")) {
                        code = "MOV" + " " + "R1" + "," + this.t1.getId() + "\n";
                        code += "DIV" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code += "MOV" + " " + "aux1" + "," + "R1";
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code = "MOV" + " " + "R1" + "," + this.t1.getId() + "\n";
                        code += "DIV" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code += "MOV" + " " + "aux1" + "," + "R1";
                    }
                }
                //caso 4: (OP, terceto, terceto)
                if ((this.t1.esTerceto()) && (this.t2.esTerceto())) {
                    if (this.t1.getTipoReal().equals("double")) {
                        code = "MOV" + " " + "R1" + "," + this.t1.getId() + "\n";
                        code += "DIV" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code += "MOV" + " " + "aux1" + "," + "R1";
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code = "MOV" + " " + "R1" + "," + this.t1.getId() + "\n";
                        code += "DIV" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code += "MOV" + " " + "aux1" + "," + "R1";
                    }
                }
                return code;

            case ":=":
                //caso (:=, variable, variable)
                if ((!this.t1.esTerceto()) && (!this.t2.esTerceto())) {
                    code= "MOV" + " " + "R1"+ "," + this.t2.getId() + "\n";
                    code+= "MOV" + " " + this.t1.getId() + "," + "R1" + "\n";
                }
                //caso (:=,variable,terceto)
                else {
                    code= "MOV" + " " + "R1"+ "," + this.t2.getNroTerceto() + "\n";
                    code+= "MOV" + " " + this.t1.getId() + "," + "R1" + "\n";
                }

            case ">":
            case "<":
            case ">=":
            case "!=":
            case "==":
            case "INVOCACION":
            case "FUNCION":
            case "CUERPO_FUNCION":
            case "PROGRAM":
            case "IF":
            case "CONDICION_IF":
            case "CUERPO_IF":
            case "BLOQUE_THEN":
            case "BLOQUE_ELSE":
            case "RETURN":
            case "CASE":
            case "CUERPO_CASE":
            case "BLOQUE":
            case "PRINT":
        }
        return code;
        }

    void setControladorTercetos(CtrlTercetos aThis) {
        this.controladorTercetos=aThis;
    }
}
