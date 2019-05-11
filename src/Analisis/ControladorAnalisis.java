package Analisis;

import Analisis.AnalisisLexico.AnalisisLexico;

import java.io.IOException;

public class ControladorAnalisis {

    public void analizar () throws IOException {
        AnalisisLexico lexico = new AnalisisLexico();
        lexico.iniciarLexico();
    }

}
