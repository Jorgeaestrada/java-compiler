package Analisis.AnalisisSemantico;

/*
3.- ANALISIS SEMANTICO

        Determina si los componentes se esta usando correctamente, segun el contexto
        en el que aparecen, esta etapa cumple una funcion principal que es la de
        verificacion, para finalmente generar la salida (arbol semantico)con el fin
        de que se genere el codigo intermedio.

        FUNCION ANALISIS SEMANTICO

        - Identifica tipo de datos de las variables
        - Comprueba la consistencia semantica
        - Manejo de errores semanticos

        Errores:

        - Incompatibilidad de tipos
        - Indefinida la variable
        -
 */

import Analisis.AnalisisLexico.AnalisisLexico;
import utils.ArchivoIO;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalisisSemantico {
    /*
     * Expresion Aritmetica de la forma -> int x = y * z;
     */
    private static final String DEC_VAR =
            "(int|double|char|boolean|float)[\\s][A-Za-z0-9]+([,][\\s][A-Za-z0-9]+)*[;]";

    private static final String directorioTexto = "src/utils/CodigoFuente.txt";
    private Pattern decVarPattern = Pattern.compile(DEC_VAR);

    private static String array[], arrayAux[];
    private static AnalisisLexico al = new AnalisisLexico();
    private static LinkedHashMap<String, String> hashMapTipos = new LinkedHashMap<>();

    public void identificarTipos () throws IOException {
        ArchivoIO archivo = new ArchivoIO();
        String s = archivo.leer(directorioTexto);

        s = s.replaceAll("(\n)", "");
        s = s.replaceAll("(\\s)+", " ");
        array = s.split("(?<=;)");

        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].trim();
            if (esDecVariable(array[i])) {
                array[i] = array[i].replaceAll(";","");
                array[i] = array[i].replaceAll(",","");
                arrayAux = array[i].split("(\\s)");
                /*
                * Obtener tipos
                */
                for (int j = 1; j < arrayAux.length; j++) {
                    System.out.printf("%-20s%s\n", arrayAux[0], arrayAux[j]);
                    hashMapTipos.put(arrayAux[j], arrayAux[0]);
                }
            }
        }
    }

    public LinkedHashMap<String, String> getHashMapTokens() {
        return hashMapTipos;
    }

    public void consistenciaSemantica () {
    }

    private boolean esDecVariable(String token) {
        boolean b = false;
        Matcher matcher = decVarPattern.matcher(token);
        if (matcher.matches()) {
            b = true;
        }
        return b;
    }
}
