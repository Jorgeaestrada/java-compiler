package Analisis.AnalisisSintactico;

import utils.ArchivoIO;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalisisSintactico {
    /*
     * Ejemplo de error sintáctico: float x y ,, z;
     */
    /*
    * Expresion Aritmetica de la forma -> x = 123 * 4.5 / 0.0;
    */
    private static final String EXP_ARIT =
            "[VAR_][OP_REL][VAR_]([OP_AR_][VAR_][DELIM_])+";

    private static String array[];
    private Pattern expAritPattern = Pattern.compile(EXP_ARIT);
    private static final String directorioTexto = "src/utils/CodigoFuente.txt";
    private ArbolSintactico arbolSintactico = new ArbolSintactico();

    /*
    * Método que genera un "arbol sintáctico"
    * Convierte una expresion aritmetica a su formato
    * en postfijo
    */
    public void iniciarSintactico () throws IOException {

        ArbolSintactico a = new ArbolSintactico();
        ArchivoIO archivo = new ArchivoIO();
        String s = archivo.leer(directorioTexto);
        /*
         * Reemplaza punto y coma, saltos de linea
         * y cualquier cantidad de espacios en blanco
         */
        s = s.replaceAll("(\n)", "");
        s = s.replaceAll("(\\s)+", " ");
        array = s.split("(?<=;)");

        /*
         * Conversion de expresion aritmetica
         * a postfijo
         */
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].trim();
            if (esExpAritmetica(array[i])) {
                arbolSintactico.convertirPosfijo(array[i]);
                }
            }
        /*
         * Descomentar para generar tabla de postfijo
         * en la terminal
         */
        System.out.println("----------------------------");
        System.out.println("----------------------------");
        System.out.println("Tabla de Postfijo\n");
        System.out.printf("%-15s%s\n\n", "Linea", "Postfijo");

        for (Map.Entry<Integer, String> entry : a.getTreeMapPostfijo().entrySet()) {
            int key = entry.getKey();
            String value = entry.getValue();
            System.out.printf("%-15s%s\n", key, value);
        }
        System.out.println("----------------------------");
        System.out.println("----------------------------");
    }


    private boolean esExpAritmetica(String token) {
        boolean b = false;
        Matcher matcher = expAritPattern.matcher(token);
        if (matcher.matches()) {
            b = true;
        }
        return b;
    }
}