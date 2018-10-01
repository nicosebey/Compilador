/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import AnalizadorLexico.ArchController;
import AnalizadorLexico.Fuente;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import ParserP.*;
/**
 *
 * @author nicol
 */
public class Main {
    private static BufferedReader codigo;
    
    private static String direccion;
    public Main(String direccion){
        this.direccion = direccion;
    }
    private static StringBuilder getCodigo(BufferedReader ubicacion){
        StringBuilder buffer = new StringBuilder();
        try{
            codigo = new BufferedReader( new FileReader( ubicacion.readLine() ) );
            String readLine;
            while ((readLine = codigo.readLine())!= null) {
                buffer.append(readLine+"\n");
            }
            buffer.append("$");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return buffer;
    }

    
      public static void mainr () {
       
         //-------------------------------carga del codigo fuente en un StringBuilder ------------------------------------------------// 
          
        //String direccion = args[0];//new String("C:\\Users\\nicol\\Desktop\\aa.txt");
        InputStream is = new ByteArrayInputStream(direccion.getBytes());
        

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        StringBuilder codigo = null;
        codigo = new StringBuilder( getCodigo( br ) );
        
      //-----------------------------------------------------------------------------------------------------------------------------
        
        
         // System.out.println(codigo);
        
       
        Fuente archivo = new Fuente(codigo);
        ArchController controlador = new ArchController(archivo);
        
        Parser parser = new Parser(controlador);
        parser.run();
       
          
        controlador.getEstructuras();
        controlador.mostrarErrores();
        controlador.mostrarTokens();
        //AHORA TENDRIA QUE PEDIR LOS TOKENS Y LOS ERRRORES
        
        
        
       /* controlador.recorrerCodFuente();
        controlador.mostrarErrores();*/
        
        }

    
      public void setDireccion (String direccion){
          this.direccion = direccion;
      }
}