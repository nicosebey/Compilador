/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalizadorLexico;

/**
 *
 * @author nicol
 */
public class Error {
    String descripcion;
    String tipo;//Warning o error de compilacion
    int linea;

    public Error(String descripcion, String tipo, int linea){
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.linea = linea;
    }

    public Error(String descripcion, String tipo){
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public String Imprimir(){
        String l= "";
        l= l + "Linea "+ String.valueOf(linea)+": ";
        return l + descripcion ;
    }


}
