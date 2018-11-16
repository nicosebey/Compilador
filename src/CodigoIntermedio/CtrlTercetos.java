/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodigoIntermedio;

import AnalizadorLexico.TablaSimbolos;
import AnalizadorLexico.Token;
import java.util.ArrayList;

/**
 *
 * @author nicol
 */
public class CtrlTercetos {
    public static final String BF = "bf";
    public static final String BI = "bi";

    private int nroTercetoActual;
    private ArrayList<Terceto> tercetos;
    private ArrayList<Integer> pila;
    private ArrayList<Terceto> pilaCase;
    private ArrayList<Terceto> pilaVariablesCase;     
    private String tipoCase;
    private String funcionActual;
    private Terceto saltoCase;
    private ArrayList<Integer> labelPendientes; // por el tema del if
    private TablaSimbolos tablaSimbolos;
    public CtrlTercetos(
    ) {
        nroTercetoActual = 0;
        tercetos = new ArrayList<Terceto>();
        pila = new ArrayList<Integer>();
        tipoCase = null;
         pilaCase = new ArrayList<Terceto>();
          pilaVariablesCase = new ArrayList<Terceto>();
          saltoCase = null;
          tablaSimbolos=null;
        labelPendientes = new ArrayList<Integer>();
    }

    public void setTablaSimbolos(TablaSimbolos tablaSimbolos) {
        this.tablaSimbolos = tablaSimbolos;
    }

    public int getNroTercetoActual() {
        return nroTercetoActual;
    }

    public void setSaltoCase(Terceto saltoCase) {
        this.saltoCase = saltoCase;
    }

    public void getSaltoCase() {
        Terceto terceto = saltoCase;
        terceto.setElemento(2, new Token(String.valueOf(tercetos.size())));
        tercetos.set(terceto.getNro(), terceto);
        //pilaCase.clear();
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
    public void imprimirTercetos(){
        for (Terceto t : tercetos){
            t.ImprimirTerceto();
        }
    }

    public ArrayList<Terceto> getPilaCase() {
        return pilaCase;
    }
    
   public Terceto getTercetoPilaCase(){
        return pilaCase.get(0);
    }

    public void apilarPilaCase(Terceto pila) {
        pilaCase.add(pila);
    }
    public void asignarSaltoCase(){
        
    }
    public void apilarPilaVariablesCase(Terceto t){
        pilaVariablesCase.add(t);
        
    }
    public void desapilarPilaVariablesCase(){
        Token t = null;
        Terceto terceto = null;
        for (Terceto i : pilaVariablesCase){
             t = new Token(String.valueOf(tercetos.size()));
             t.setEsTerceto(false);
             terceto = tercetos.get(i.getNro());
             terceto.setElemento(2,t);
             tercetos.set(i.getNro(), terceto);
        }
        pilaVariablesCase.clear();
    }

    public void setFuncionActual(String funcionActual) {
        this.funcionActual = funcionActual;
    }

    public String getFuncionActual() {
        return funcionActual;
    }
    public void desapilarPilaCase(){
        Terceto terceto = pilaCase.get(0);
        terceto.setElemento(2, new Token(String.valueOf(tercetos.size())));
        pilaCase.clear();
    }
/*
    public void SetearSaltosIncondicionalesCase(Integer nroT){
        for(Terceto t:pilaVariablesCase)
              t.setElemento(2, new Token(String.valueOf(nroT)));
        pilaVariablesCase.clear();
    }
    */
    public void addLabelPendiente(int labelPendiente) {
    this.labelPendientes.add( labelPendiente );
}

    public int borrarLabelPendiente() {
        int l = labelPendientes.get( labelPendientes.size()-1 );
        labelPendientes.remove( labelPendientes.size()-1 );
        return l;
    }
    public String generarAssembler(){
        String assembler="";
        nroTercetoActual = 1; //numero de terceto para colocar el label
        for (Terceto t: this.tercetos){
            t.setControladorTercetos(this);
            t.setTablaSimbolos(tablaSimbolos);
            assembler = assembler + t.getAssembler();


            nroTercetoActual++;
            if((!labelPendientes.isEmpty()) && (nroTercetoActual == labelPendientes.get(labelPendientes.size()-1))) {
                assembler = assembler + "Label" + String.valueOf(labelPendientes.get(labelPendientes.size()-1))+ "\n";
                borrarLabelPendiente();
            }
        }
        return assembler;
    }
}
