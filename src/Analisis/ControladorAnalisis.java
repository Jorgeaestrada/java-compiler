package Analisis;

import Analisis.AnalisisLexico.AnalisisLexico;
import Analisis.AnalisisSintactico.AnalisisSintactico;

import java.io.IOException;

public class ControladorAnalisis {

    private static AnalisisLexico lexico = new AnalisisLexico();
    private static AnalisisSintactico sintactico = new AnalisisSintactico();

    public void analizar() throws IOException {
        /*
        * Inicia analizador lexico
         */

        lexico.iniciarLexico();
        /*
         * Inicia analizador sintactico
         */
        sintactico.iniciarSintactico();
    }
}
