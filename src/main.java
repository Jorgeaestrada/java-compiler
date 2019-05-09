import Analisis.AnalisisSintactico.AnalisisSintactico;
import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        /**/
        // Descomentar para probar la clase de lectura de archivos

        /*
        //Descomentar para probar el arbol de expresiones
        ArbolSintactico arbolExpresiones = new ArbolSintactico();
        arbolExpresiones.convertirPosfijo("par = 0;");
        */

        Interfaz i = new Interfaz();
        //i.setVisible(true);
        AnalisisSintactico s = new AnalisisSintactico();
        s.generarTablaSimbolos();
        s.generarArbolSintactico();
    }
}