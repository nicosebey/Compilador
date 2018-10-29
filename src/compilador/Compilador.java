/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author nicol
 */
public class Compilador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        String direccion = args[0];     PARA EL EJECUTABLE DESCOMENTAR ESTO Y COMENTAR LA DE ABAJO
        String direccion = new String("C:\\Users\\nicol\\Desktop\\aa.txt");
        Main main = new Main(direccion);
        main.mainr();
    }
    
}
