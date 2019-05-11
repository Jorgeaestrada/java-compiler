package Analisis;

import Analisis.AnalisisLexico.AnalisisLexico;
import Analisis.AnalisisSemantico.AnalisisSemantico;
import Analisis.AnalisisSintactico.AnalisisSintactico;

import java.io.IOException;

public class ControladorAnalisis {

    public void analizar () throws IOException {
        AnalisisLexico lexico = new AnalisisLexico();
        lexico.iniciarLexico();
    }

}
