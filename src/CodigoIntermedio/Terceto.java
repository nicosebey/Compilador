/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodigoIntermedio;

import AnalizadorLexico.ArchController;
import AnalizadorLexico.TablaSimbolos;
import AnalizadorLexico.Token;
import javafx.scene.shape.Arc;

import java.util.ArrayList;

/**
 *
 * @author nicol
 */
public class Terceto {
    public static final String etiquetaSaltoIncondicional = "JMP";
    public static final String AUX = "auxiliar";
    public static final String reg2 = "EBX";
    public static final String reg1 = "ECX";
    public static final String reg3 = "EAX";
    public static final String reg4 = "EDX";
    public static final String ST = "ST";
    public static final String ST1 = "ST(1)";
    public static final String ST2 = "ST(2)";
    public static final String ST3 = "ST(3)";
    public static final String ST4 = "ST(4)";
    public static final String ST5 = "ST(5)";
    public static final String ST6 = "ST(6)";
    public static final String ST7 = "ST(7)";
    
    private int nroTerceto;
    private Token t1;
    private Token t2;
    private String operador;
    private CtrlTercetos controladorTercetos;
    private TablaSimbolos tablaSimbolos;
    private String tipoSalto;

    public Terceto(String uno, Token dos, Token tres){
           this.operador = uno;
           this.t1 = dos;
           this.t2 = tres;
           tablaSimbolos=null;

           controladorTercetos=null;
           tipoSalto=null;

           
    }
    public String getOperador(){return operador;
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

    public String getTipoSalto() {
        return tipoSalto;
    }

    public void setTipoSalto(String tipoSalto){

        if(tipoSalto== "<=")
            this.tipoSalto= "JL";
        else
        if(tipoSalto.equals("="))
            this.tipoSalto="JNE";
        else
        if(tipoSalto.equals(">="))
            this.tipoSalto= "JE";
        else
        if(tipoSalto.equals(">")){
            this.tipoSalto= "JGE";


            }
        else
        if(tipoSalto.equals("<"))
            this.tipoSalto= "JLE";
        else
        if(tipoSalto.equals("!="))
            this.tipoSalto= "JE";

    };
    
    //MODIFICAR LAS PALABRAS DE LOS CASE
    public String getAssembler() {
        String code= "";
        switch(operador) {
            case "+":
                //caso 1: (OP, variable, variable)
                if ( ( !this.t1.esTerceto() ) && ( !this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        if (t1.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t1.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t1.getNombreCte() + '\n';
                        if (t2.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t2.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t2.getNombreCte() + '\n';

                        code = code + "F"+"ADD"+ " " + '\n';
                        code = code + "FST " + AUX + this.nroTerceto + '\n';
                      /*  code= "MOV" + " " + "EAX"+ "," + this.t1.getId() + "\n";
                        code+= "ADD" + " " + "EAX" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "EAX"+ "\n";
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("double");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);*/
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "EAX"+ "," + this.t1.getId() + "\n";
                        code+= "MOV" + " " + "EBX"+"," + this.t2.getId() + '\n';
                        code+= "ADD" + " " + "EAX" + "," + "EBX" + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "EAX"+ "\n";
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                }
                //caso 2: (OP, terceto, variable)
                if ( ( this.t1.esTerceto() ) && ( !this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        if (t1.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t1.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t1.getNombreCte() + '\n';
                        if (t2.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t2.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t2.getNombreCte() + '\n';

                        code = code + "F"+"ADD"+ " " + '\n';
                        code = code + "FST " + AUX + this.nroTerceto + '\n';
                        /*
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "ADD" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "R1"+ "\n";*/
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("double");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "EAX"+ "," + this.t1.getId() + "\n";
                        code+= "MOV" + " " + "EBX"+"," + this.t2.getId() + '\n';
                        code+= "ADD" + " " + "EAX" + "," + "EBX" + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "EAX"+ "\n";
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                }
                //caso 3: (OP, variable, terceto)
                if ( ( !this.t1.esTerceto() ) && ( this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        if (t1.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t1.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t1.getNombreCte() + '\n';
                        if (t2.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t2.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t2.getNombreCte() + '\n';

                        code = code + "F"+"ADD"+ " " + '\n';
                        code = code + "FST " + AUX + this.nroTerceto + '\n';
                        /*
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "ADD" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "R1"+ "\n";*/
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("double");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "EAX"+ "," + this.t1.getId() + "\n";
                        code+= "MOV" + " " + "EBX"+"," + this.t2.getId() + '\n';
                        code+= "ADD" + " " + "EAX" + "," + "EBX" + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "EAX"+ "\n";
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                }
                //caso 4: (OP, terceto, terceto)
                if ( ( this.t1.esTerceto() ) && ( this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        if (t1.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t1.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t1.getNombreCte() + '\n';
                        if (t2.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t2.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t2.getNombreCte() + '\n';

                        code = code + "F"+"ADD"+ " " + '\n';
                        code = code + "FST " + AUX + this.nroTerceto + '\n';
                        /*/
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "ADD" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "R1"+ "\n";*/
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("double");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "EAX"+ "," + this.t1.getId() + "\n";
                        code+= "MOV" + " " + "EBX"+"," + this.t2.getId() + '\n';
                        code+= "ADD" + " " + "EAX" + "," + "EBX" + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "EAX"+ "\n";
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                }
            return code;
            case "-":
                //caso 1: (OP, variable, variable)
                if ( ( !this.t1.esTerceto() ) && ( !this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        if (t1.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t1.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t1.getNombreCte() + '\n';
                        if (t2.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t2.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t2.getNombreCte() + '\n';

                        code = code + "F"+"SUB"+ " " + '\n';
                        code = code + "FST " + AUX + this.nroTerceto + '\n';
                        /*
                        code= "MOV" + " " + "EAX"+ "," + this.t1.getId() + "\n";
                        code+= "SUB" + " " + "EAX" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "EAX"+ "\n";*/
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "EAX"+ "," + this.t1.getId() + "\n";
                        code+= "MOV" + " " + "EBX"+"," + this.t2.getId() + '\n';
                        code+= "SUB" + " " + "EAX" + "," + "EBX" + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "EAX"+ "\n";
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                }
                //caso 2: (OP, terceto, variable)
                if ( ( this.t1.esTerceto() ) && ( !this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        if (t1.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t1.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t1.getNombreCte() + '\n';
                        if (t2.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t2.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t2.getNombreCte() + '\n';

                        code = code + "F"+"SUB"+ " " + '\n';
                        code = code + "FST " + AUX + this.nroTerceto + '\n';
                        /*
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "SUB" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "R1"+ "\n";*/
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "EAX"+ "," + this.t1.getId() + "\n";
                        code+= "MOV" + " " + "EBX"+"," + this.t2.getId() + '\n';
                        code+= "SUB" + " " + "EAX" + "," + "EBX" + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "EAX"+ "\n";
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                }
                //caso 3: (OP, variable, terceto)
                if ( ( !this.t1.esTerceto() ) && ( this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        if (t1.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t1.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t1.getNombreCte() + '\n';
                        if (t2.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t2.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t2.getNombreCte() + '\n';

                        code = code + "F"+"SUB"+ " " + '\n';
                        code = code + "FST " + AUX + this.nroTerceto + '\n';
                        /*
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "SUB" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "R1"+ "\n";*/
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "EAX"+ "," + this.t1.getId() + "\n";
                        code+= "MOV" + " " + "EBX"+"," + this.t2.getId() + '\n';
                        code+= "SUB" + " " + "EAX" + "," + "EBX" + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "EAX"+ "\n";
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                }
                //caso 4: (OP, terceto, terceto)
                if ( ( this.t1.esTerceto() ) && ( this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        if (t1.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t1.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t1.getNombreCte() + '\n';
                        if (t2.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t2.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t2.getNombreCte() + '\n';

                        code = code + "F"+"SUB"+ " " + '\n';
                        code = code + "FST " + AUX + this.nroTerceto + '\n';
                        /*
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "SUB" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "R1"+ "\n";*/
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "EAX"+ "," + this.t1.getId() + "\n";
                        code+= "MOV" + " " + "EBX"+"," + this.t2.getId() + '\n';
                        code+= "SUB" + " " + "EAX" + "," + "EBX" + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "EAX"+ "\n";
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                }
                return code;
            case "*":
                //caso 1: (OP, variable, variable)
                if ( ( !this.t1.esTerceto() ) && ( !this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        if (t1.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t1.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t1.getNombreCte() + '\n';
                        if (t2.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t2.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t2.getNombreCte() + '\n';

                        code = code + "F"+"MUL"+ " " + '\n';
                        code = code + "FST " + AUX + this.nroTerceto + '\n';
                        /*
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "MUL" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "R1"+ "\n";*/
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "EAX"+ "," + this.t1.getId() + "\n";
                        code+= "MOV" + " " + "EBX"+"," + this.t2.getId() + '\n';
                        code+= "MUL" + " " + "EAX" + "," + "EBX" + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "EAX"+ "\n";
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                }
                //caso 2: (OP, terceto, variable)
                if ( ( this.t1.esTerceto() ) && ( !this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        if (t1.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t1.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t1.getNombreCte() + '\n';
                        if (t2.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t2.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t2.getNombreCte() + '\n';

                        code = code + "F"+"MUL"+ " " + '\n';
                        code = code + "FST " + AUX + this.nroTerceto + '\n';
                        /*
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "MUL" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "R1"+ "\n";*/
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "EAX"+ "," + this.t1.getId() + "\n";
                        code+= "MOV" + " " + "EBX"+"," + this.t2.getId() + '\n';
                        code+= "MUL" + " " + "EAX" + "," + "EBX" + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "EAX"+ "\n";
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }

                }
                //caso 3: (OP, variable, terceto)
                if ( ( !this.t1.esTerceto() ) && ( this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        if (t1.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t1.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t1.getNombreCte() + '\n';
                        if (t2.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t2.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t2.getNombreCte() + '\n';

                        code = code + "F"+"MUL"+ " " + '\n';
                        code = code + "FST " + AUX + this.nroTerceto + '\n';
                        /*
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "MUL" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "R1"+ "\n";*/
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "EAX"+ "," + this.t1.getId() + "\n";
                        code+= "MOV" + " " + "EBX"+"," + this.t2.getId() + '\n';
                        code+= "MUL" + " " + "EAX" + "," + "EBX" + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "EAX"+ "\n";
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                }
                //caso 4: (OP, terceto, terceto)
                if ( ( this.t1.esTerceto() ) && ( this.t2.esTerceto() ) ) {
                    if (this.t1.getTipoReal().equals("double")) {
                        if (t1.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t1.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t1.getNombreCte() + '\n';
                        if (t2.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t2.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t2.getNombreCte() + '\n';

                        code = code + "F"+"MUL"+ " " + '\n';
                        code = code + "FST " + AUX + this.nroTerceto + '\n';
                        /*
                        code= "MOV" + " " + "R1"+ "," + this.t1.getId() + "\n";
                        code+= "MUL" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "R1"+ "\n";*/
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "EAX"+ "," + this.t1.getId() + "\n";
                        code+= "MOV" + " " + "EBX"+"," + this.t2.getId() + '\n';
                        code+= "MUL" + " " + "EAX" + "," + "EBX" + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "EAX"+ "\n";
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                }
                return code;
            case "/":

                //caso 1: (OP, variable, variable)
                if ((!this.t1.esTerceto()) && (!this.t2.esTerceto())) {
                    if (this.t1.getTipoReal().equals("double")) {
                        if (t1.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t1.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t1.getNombreCte() + '\n';
                        if (t2.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t2.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t2.getNombreCte() + '\n';

                        code = code + "F"+"DIV"+ " " + '\n';
                        code = code + "FST " + AUX + this.nroTerceto + '\n';
                        /*
                        code = "MOV" + " " + "R1" + "," + this.t1.getId() + "\n";
                        code += "DIV" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code += "MOV" + " " + AUX+this.nroTerceto + "," + "R1"+ "\n";*/
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "EAX"+ "," + this.t1.getId() + "\n";
                        code+= "MOV" + " " + "EBX"+"," + this.t2.getId() + '\n';
                        code+= "DIV" + " " + "EAX" + "," + "EBX" + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "EAX"+ "\n";
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                }
                //caso 2: (OP, terceto, variable)
                if ((this.t1.esTerceto()) && (!this.t2.esTerceto())) {
                    if (this.t1.getTipoReal().equals("double")) {
                        if (t1.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t1.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t1.getNombreCte() + '\n';
                        if (t2.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t2.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t2.getNombreCte() + '\n';

                        code = code + "F"+"DIV"+ " " + '\n';
                        code = code + "FST " + AUX + this.nroTerceto + '\n';
                        /*
                        code = "MOV" + " " + "R1" + "," + this.t1.getId() + "\n";
                        code += "DIV" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code += "MOV" + " " + AUX+this.nroTerceto + "," + "R1"+ "\n";*/
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "EAX"+ "," + this.t1.getId() + "\n";
                        code+= "MOV" + " " + "EBX"+"," + this.t2.getId() + '\n';
                        code+= "DIV" + " " + "EAX" + "," + "EBX" + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "EAX"+ "\n";
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                }
                //caso 3: (OP, variable, terceto)
                if ((!this.t1.esTerceto()) && (this.t2.esTerceto())) {
                    if (this.t1.getTipoReal().equals("double")) {
                        if (t1.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t1.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t1.getNombreCte() + '\n';
                        if (t2.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t2.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t2.getNombreCte() + '\n';

                        code = code + "F"+"DIV"+ " " + '\n';
                        code = code + "FST " + AUX + this.nroTerceto + '\n';
                        /*
                        code = "MOV" + " " + "R1" + "," + this.t1.getId() + "\n";
                        code += "DIV" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code += "MOV" + " " + AUX+this.nroTerceto + "," + "R1"+ "\n";*/
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "EAX"+ "," + this.t1.getId() + "\n";
                        code+= "MOV" + " " + "EBX"+"," + this.t2.getId() + '\n';
                        code+= "DIV" + " " + "EAX" + "," + "EBX" + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "EAX"+ "\n";
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                }
                //caso 4: (OP, terceto, terceto)
                if ((this.t1.esTerceto()) && (this.t2.esTerceto())) {
                    if (this.t1.getTipoReal().equals("double")) {
                        if (t1.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t1.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t1.getNombreCte() + '\n';
                        if (t2.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxd" + t2.getNombreCte() + '\n';
                        else
                            code = code + "FLD " + t2.getNombreCte() + '\n';

                        code = code + "F"+"DIV"+ " " + '\n';
                        code = code + "FST " + AUX + this.nroTerceto + '\n';
                        /*
                        code = "MOV" + " " + "R1" + "," + this.t1.getId() + "\n";
                        code += "DIV" + " " + "R1" + "," + this.t2.getId() + "\n";
                        code += "MOV" + " " + AUX+this.nroTerceto + "," + "R1"+ "\n";*/
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                    if (this.t1.getTipoReal().equals("uslinteger")) {
                        code= "MOV" + " " + "EAX"+ "," + this.t1.getId() + "\n";
                        code+= "MOV" + " " + "EBX"+"," + this.t2.getId() + '\n';
                        code+= "DIV" + " " + "EAX" + "," + "EBX" + "\n";
                        code+= "MOV" + " " + AUX+this.nroTerceto+ "," + "EAX"+ "\n";
                        Token t= new Token(AUX+this.nroTerceto);
                        t.setTipoReal("uslinteger");
                        tablaSimbolos.setDeclaracion(AUX+this.nroTerceto,t);
                    }
                }
                return code;

            case ":=":
                //caso (:=, variable, variable)
                if ((!this.t1.esTerceto()) && (!this.t2.esTerceto())) {
                    code= "MOV" + " " + "EBX"+ "," + this.t2.getId() + "\n";
                    code+= "MOV" + " " + this.t1.getId() + "," + "EBX" + "\n";
                }
                //caso (:=,variable,terceto)
                else {
                    code= "MOV" + " " + "EBX"+ "," + AUX+this.t2.getNroTerceto() + "\n";
                    code+= "MOV" + " " + this.t1.getId() + "," + "EBX" + "\n";
                }
                return code;

            case ">":
                //caso 1: (OP, variable, variable)
                System.out.println(this.t1.esTerceto());
                System.out.println(this.t2.esTerceto());
                if ( ( !this.t1.esTerceto() ) && ( !this.t2.esTerceto() ) ) {
                    if (( this.t1.getTipoReal().equals("uslinteger") ) && (this.t2.getTipoReal().equals("uslinteger")) ){
                        code = code + "MOV " + "ECX "+ ", " + this.t1.getId() + '\n';
                        code = code + "MOV " + "EAX "+ ", " + this.t2.getId() + '\n';
                        code = code + "CMP" + " " + "ECX "+ ","  + "EAX" + '\n';
                    }
                    else
                    if ( (this.t1.getTipoReal().equals("double"))  && (this.t2.getTipoReal().equals("double")) ){
                        if (this.t2.getTipo()==ArchController.CTE_D)
                            code = code + "FLD " + "auxd" +t2.getNombreCte() + '\n';
                        else
                        {
                            /*if(elementos.get(2).getToken().isConvertido()){
                                assembler = assembler + "FILD " + elementos.get(2).getNombreVar() + '\n';
                            }
                            else*/
                                code = code + "FLD " + this.t2.getNombreCte() + '\n';

                        }

                        if (this.t1.getTipo()== ArchController.CTE_D)
                            code = code + "FLD " + "auxf" + this.t1.getNombreCte() + '\n';
                        else
                        {
                            /*if(elementos.get(1).getToken().isConvertido()){
                                assembler = assembler + "FILD " + elementos.get(1).getNombreVar() + '\n';
                            }
                            else*/
                                code = code + "FLD " + this.t1.getNombreCte() + '\n';

                        }
                        code = code + "FCOM" + '\n';
                        code = code + "FSTSW AX" + '\n';
                        code = code + "SAHF" + '\n';
                    }
                }
               else
                    //caso 2: (OP, registro, variable)
                    if ( ( this.t1.esTerceto() ) && ( !this.t2.esTerceto() ) ){
                        if (( this.t1.getTipoReal().equals("uslinteger") ) && (this.t2.getTipoReal().equals("uslinteger")) ){
                            code = code + "MOV " + "ECX" +", "+this.t2.getId()+'\n';
                            code = code + "CMP" + " " + AUX+this.t1.getNroTerceto()+", "+ "ECX" + '\n';
                        }
                        else
                        if (( this.t1.getTipoReal().equals("double") ) && (this.t2.getTipoReal().equals("double")) ){

                            if (this.t2.getTipo()==ArchController.CTE_D)
                                code = code + "FLD " + "auxd" + this.t2.getNombreCte() + '\n';
                           /* else {
                                if (elementos.get(2).getToken().isConvertido())
                                    assembler = assembler + "FILD " + elementos.get(2).getNombreVar() + '\n';
                                else
                                    assembler = assembler + "FLD " + elementos.get(2).getNombreVar() + '\n';
                            }*/
                           /* if (elementos.get(1).getToken().isConvertido())
                                assembler = assembler + "FILD " + AUX+terceto1.getNumeroTerceto() + '\n';
                            else
                                assembler = assembler + "FLD " + AUX+terceto1.getNumeroTerceto() + '\n';*/
                            code = code + "FCOM" + '\n';
                            code = code + "FSTSW AX" + '\n';
                            code = code + "SAHF" + '\n';
                        }
                    }

                    else
                        //caso 3: (OP, registro, registro)
                        if ( ( this.t1.esTerceto() ) && ( this.t2.esTerceto() ) ){
                            if (( this.t1.getTipoReal().equals("uslinteger") ) && (this.t2.getTipoReal().equals("uslinteger")) ){
                                code = code + "MOV " +AUX+this.t1.getNroTerceto()+", "+this.t1.getId()+'\n';
                                code = code + "MOV " +AUX+this.t2.getNroTerceto()+", "+this.t2.getId()+'\n';
                                code = code + "CMP" + " " +AUX+this.t1.getNroTerceto()+", "+AUX+this.t2.getNroTerceto()+'\n';
                            }
                            else
                            if (( this.t1.getTipoReal().equals("double") ) && (this.t2.getTipoReal().equals("double")) ){
                                /*if (elementos.get(2).getToken().isConvertido())
                                    assembler = assembler + "FILD " + AUX+terceto2.getNumeroTerceto() + '\n';
                                else
                                    assembler = assembler + "FLD " + AUX+terceto2.getNumeroTerceto() + '\n';
                                if (elementos.get(2).getToken().isConvertido())
                                    assembler = assembler + "FILD " + AUX+terceto1.getNumeroTerceto() + '\n';
                                else
                                    assembler = assembler + "FLD " + AUX+terceto1.getNumeroTerceto() + '\n';*/
                                code = code + "FCOM" + '\n';
                                code = code + "FSTSW AX" + '\n';
                                code = code + "SAHF" + '\n';
                            }
                        }
                        else
                            //caso 4: (OP, variable, registro)
                            if ( ( !this.t1.esTerceto() ) && ( this.t2.esTerceto() ) ){
                                if (( this.t1.getTipoReal().equals("uslinteger") ) && (this.t2.getTipoReal().equals("uslinteger")) ){
                                    code = code + "MOV " + "EBX" +", "+this.t1.getId()+'\n';
                                    code = code + "CMP" + " " + "EBX" +", "+AUX+this.t2.getNroTerceto()+ '\n';
                                }
                                else
                                if (( this.t1.getTipoReal().equals("double") ) && (this.t2.getTipoReal().equals("double")) ){
                                    /*if (elementos.get(2).getToken().isConvertido())
                                        assembler = assembler + "FILD " + AUX+terceto2.getNumeroTerceto() + '\n';
                                    else
                                        assembler = assembler + "FLD " + AUX+terceto2.getNumeroTerceto() + '\n';*/
                                    if (this.t2.getTipo()==ArchController.CTE_D)
                                        code = code + "FLD " + "auxd" + this.t1.getNombreCte() + '\n';
                                    else
                                    {
                                        /*if (elementos.get(1).getToken().isConvertido())
                                            assembler = assembler + "FILD " + elementos.get(1).getNombreVar() + '\n';
                                        else
                                            assembler = assembler + "FLD " + elementos.get(1).getNombreVar() + '\n';*/
                                    }

                                    code = code + "FCOM" + '\n';
                                    code = code + "FSTSW AX" + '\n';
                                    code = code + "SAHF" + '\n';
                                }
                            }
                return code;
            case "<":
            case ">=":
            case "!=":
            case "==":
            case "INVOCACION":
            case "FUNCION":
            case "CUERPO_FUNCION":
            case "PROGRAM":
            case "bf":
                code = "";
                String operador = this.operador;
                if (operador == controladorTercetos.BF){
                    System.out.println(this.tipoSalto+"SALTOOOO");

                    code = tipoSalto + " Label" + t2.getId() + '\n';
                    controladorTercetos.addLabelPendiente( Integer.parseInt(t2.getId() ) );
                }
                else{
                    code = etiquetaSaltoIncondicional + " Label" + t1.getId() + '\n';
                    code = code + "Label" + String.valueOf( controladorTercetos.borrarLabelPendiente() ) +":" + '\n';
                    controladorTercetos.addLabelPendiente( Integer.parseInt( t1.getId() ) );
                }
                return code;
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

    public void setTablaSimbolos(TablaSimbolos tablaSimbolos) {
        this.tablaSimbolos=tablaSimbolos;
    }
}
