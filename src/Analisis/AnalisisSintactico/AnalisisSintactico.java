package Analisis.AnalisisSintactico;

/*
2.- ANALISIS SINTACTICO

        Esta fase se puede practicar, siempre y cuando la anterior haya sido realizada
        correctamente,  en este analizador se agrupan los componentes para construir
        frases, verifica que el lenguaje fuente cumpla con las especificaciones que
        necesita el compilador donde se va a ejecutar. Es la fase mas importante en
        el proceso de compilacion.

        FUNCIONES ANALISIS SINTACTICO

        - Crea las entradas en la tabla de símbolos
        - Genera arbol sintáctico
        - Maneja errores sintácticos
        - float x y ,, z;
*/

import Analisis.AnalisisLexico.AnalisisLexico;
import utils.LeerArchivo;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalisisSintactico {

    /*
    * Expresion Aritmetica de la forma -> x = 123 * 4.5 / 0.0;
    */
    private static final String EXP_ARIT =
            "[A-Za-z0-9]+[\\s][=][\\s]" +
                    "([A-Za-z0-9]+|[0-9]+([.][0-9]+)?)" +
                    "([\\s][-+*/][\\s]" +
                    "([A-Za-z0-9]+|[0-9]+([.][0-9]+)?))*[;][\\s|\\n]*";

    /*
     * Expresion Aritmetica de la forma -> int x = y * z;
     */
    private static final String REGEX_DECLARATION =
            "[\\s]*(int|double|char|boolean|float)[\\s][A-Za-z0-9]+([,][\\s][A-Za-z0-9]+)*[;]";

    private static final String directorioTexto = "src/utils/archivo.txt";
    private Pattern expAritPattern = Pattern.compile(EXP_ARIT);

    private static String array[];
    private ArbolSintactico arbolSintactico = new ArbolSintactico();

    /*
    * Método que genera un "arbol sintáctico"
    * Convierte una expresion aritmetica a su formato
    * en postfijo
    */
    public void generarArbolSintactico () throws IOException {

        ArbolSintactico a = new ArbolSintactico();
        LeerArchivo archivo = new LeerArchivo();
        String s = archivo.leer(directorioTexto);
        /*
         * Reemplaza punto y coma, saltos de linea
         * y cualquier cantidad de espacios en blanco
         */
        s = s.replaceAll("(\n)", "");
        s = s.replaceAll("(\\s)+", " ");
        array = s.split("(?<=;)");

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
        System.out.println("Tabla de Postfijos\n");
        System.out.printf("%-15s%s\n\n", "Linea", "Postfijo");
        for (Map.Entry<Integer, String> entry : a.getTreeMapPostfijo().entrySet()) {
            int key = entry.getKey();
            String value = entry.getValue();
            System.out.printf("%-15s%s\n", key, value);
        }
        System.out.println("----------------------------");
        System.out.println("----------------------------");

    }
    /*
    * Método que Crea las entradas en la tabla de símbolos
    */
    public void generarTablaSimbolos () throws IOException {
        AnalisisLexico l = new AnalisisLexico();
        LeerArchivo archivo = new LeerArchivo();
        String s = archivo.leer(directorioTexto);
        /*
        * Reemplaza punto y coma, saltos de linea
        * y cualquier cantidad de espacios en blanco
        */
        s = s.replaceAll("(;)", " ;");
        s = s.replaceAll("(\n)", "");
        s = s.replaceAll("(\\s)+", " ");
        array = s.split("[\\s]");

        // System.out.println(s);

        for (int i = 0; i < array.length; i++) {
            l.generaToken(array[i]);
        }

        /*
        * Descomentar para generar tabla de simbolos
        * en la terminal
         */
        System.out.println("----------------------------");
        System.out.println("----------------------------");
        System.out.println("Tabla de Símbolos\n");
        System.out.printf("%-15s%s\n\n", "Token", "lexema");
        for (Map.Entry<String, String> entry : l.getTreeMapTokens().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.printf("%-15s%s\n", value, key);
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
