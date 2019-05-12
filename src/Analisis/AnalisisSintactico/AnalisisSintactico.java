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
    *
    */
    private static final String EXP_ARIT =
            "[A-Za-z0-9]+[\\s][=][\\s]" +
                    "([A-Za-z0-9]+|[0-9]+([.][0-9]+)?)[\\s]" +
                    "([-+*/][\\s]" +
                    "([A-Za-z0-9]+|[0-9]+([.][0-9]+)?)[\\s])+[;]";

    private ArbolSintactico a = new ArbolSintactico();
    private Pattern expAritPattern = Pattern.compile(EXP_ARIT);

    private String archivoLexico = "src/utils/CodigoFuente.txt";
    private static String array[], arrayAux[];

    /*
    * Método que genera un "arbol sintáctico"
    * Convierte una expresion aritmetica a su formatomydata
    * en postfijo
    */
    public void iniciarSintactico () throws IOException {
        ArchivoIO archivo = new ArchivoIO();
        String s = archivo.leer(archivoLexico);
        /*
         * Reemplaza punto y coma, saltos de linea
         * y cualquier cantidad de espacios en blanco
         */
        s = s.replaceAll("(;)", " ;");
        s = s.replaceAll(",", " , ");
        s = s.replaceAll("[\\n]+[\\t]*[\\s]*", "\n");
        //s = s.replaceAll("(\\s)+", " ");
        s = s.trim();
        //System.out.println(s);
        array = s.split("[\\n]");

        /*
        Matcher mat = expAritPattern.matcher(s);
        while (mat.find()){
            System.out.println("Match: " + mat.group());
        }
         */

        /*
         * Conversion de expresion aritmetica
         * a postfijo
         */
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].trim();
            //System.out.println("------>\n" + array[i]+"\n");
            if (esExpAritmetica(array[i])) {
                //System.out.println("-----\n" + array[i]+"\n");
                a.convertirPosfijo(array[i]);
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