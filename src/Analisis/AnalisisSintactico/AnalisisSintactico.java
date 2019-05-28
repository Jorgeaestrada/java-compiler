package Analisis.AnalisisSintactico;

import Tablas.TablaErrores;
import Tablas.TablaSimbolos;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalisisSintactico {

    private static TablaErrores tablaErrores = new TablaErrores();
    private static TablaSimbolos tablaSimbolos = new TablaSimbolos();
    private static final String EXP_ARIT =
            "[A-Za-z0-9]+[\\s][=][\\s]" +
                    "([A-Za-z0-9]+|[0-9]+([.][0-9]+)?)[\\s]" +
                    "([-+*/][\\s]" +
                    "([A-Za-z0-9]+|[0-9]+([.][0-9]+)?)[\\s])+[;]";

    private static final String DEC_VAR =
            "(int|double|char|boolean|float)[\\s]" +
                    "[A-Za-z0-9]+[\\s]([,][\\s][A-Za-z0-9]*[\\s])*[;]";

    private static final String CICLO_FOR =
            "(for)[\\s][(][\\s](int|double)[\\s][A-Za-z]+[\\s][=][\\s][0-9]+[\\s][;]" +
                    "[\\s][A-Za-z]+[\\s](<|>|<=|>=)[\\s][A-Za-z]+[\\s][;]" +
                    "[\\s][A-Za-z]+[\\s][+][\\s][+][\\s][)]";
    private static final String DELIM_IZQ = "[{]";
    private static final String DELIM_DER = "[}]";

    private ArbolSintactico arbolSintactico = new ArbolSintactico();

    private static StringBuilder stringBuilder = new StringBuilder();
    private Pattern expAritPattern = Pattern.compile(EXP_ARIT);
    private Pattern decVarPattern = Pattern.compile(DEC_VAR);
    private Pattern cicloForPattern = Pattern.compile(CICLO_FOR);
    private Pattern delimIzqPattern = Pattern.compile(DELIM_IZQ);
    private Pattern delimDerPattern = Pattern.compile(DELIM_DER);

    private static String array[], arrayAux[];
    private static int contLinea = 1;

    /*
    * Método que genera un "arbol sintáctico"
    * Convierte una expresion aritmetica arbolSintactico su formatomydata
    * en postfijo
    */
    public void iniciarSintactico (String s) {
        stringBuilder.setLength(0);
        /*
         * Reemplaza punto y coma, saltos de linea
         * y cualquier cantidad de espacios en blanco
         */
        s = s.replaceAll("(;)", " ;");
        s = s.replaceAll(",", " ,");
        s = s.replaceAll("[\\n]+([\\t]*[\\s]*|[\\s]*[\\t]*)", "\n");
        s = s.trim();
        array = s.split("[\\n]");
        /*
         * Conversion de expresion aritmetica
         * arbolSintactico postfijo
         */
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].trim();
            System.out.println(array[i]);
            if (esExpAritmetica(array[i])) {
                arbolSintactico.convertirPosfijo(array[i]);
                array[i] = array[i].replaceAll("[-|*|/|+|;|,|=|\\d+]" , "");
                array[i] = array[i].replaceAll("\\s+" , " ");
                arrayAux = array[i].split(" ");
                for (int j = 1; j < arrayAux.length; j++) {
                    if (!tablaSimbolos.buscarSimbolo(arrayAux[j - 1])
                        .equals(tablaSimbolos.buscarSimbolo(arrayAux[j]))) {
                            tablaErrores.agregarEntrada(contLinea ,"Error Semántico: Tipos Incompatibles entre '"
                                    + arrayAux[j - 1] + "' y '" + arrayAux[j] + "'");
                        System.out.println(">"+tablaSimbolos.buscarSimbolo(arrayAux[j - 1]));
                        System.out.println(">"+arrayAux[j]);
                    }
                }
            } else if (esDecVariable(array[i])){
               // System.out.println("declaracion: "+ array[i]);
            } else if (esCicloFor(array[i])) {
                //System.out.println("ciclo: " + array[i]);
            } else if (esDelimDer(array[i])) {
                //System.out.println("delim: " + array[i]);
            } else if (esDelimIzq(array[i])) {
                //System.out.println("delim: " + array[i]);
            } else{
                tablaErrores.agregarEntrada(contLinea, "Error Sintactico: el lexema '" + array[i] + "' está mal escrito");
            }
            contLinea++;
        }
        /*
         * Descomentar para generar tabla de postfijo
         * en la terminal
         */
        System.out.println("----------------------------");
        System.out.println("----------------------------");
        System.out.println("Tabla de Postfijo\n");
        System.out.printf("%-15s%s\n\n", "Linea", "Postfijo");

        for (Map.Entry<Integer, String> entry : arbolSintactico.getTreeMapPostfijo().entrySet()) {
            int key = entry.getKey();
            String value = entry.getValue();
            System.out.printf("%-15s%s\n", key, value);
            stringBuilder.append(key + "\t" + value + "\n");
        }
        contLinea = 1;
        arbolSintactico.limpiarVariables();
    }

    public String resultado () {
        return stringBuilder.toString();
    }

    private boolean esExpAritmetica(String lexema) {
        boolean b = false;
        Matcher matcher = expAritPattern.matcher(lexema);
        if (matcher.matches()) {
            b = true;
        }
        return b;
    }

    private boolean esDecVariable (String lexema) {
        boolean b = false;
        Matcher matcher = decVarPattern.matcher(lexema);
        if (matcher.matches()) {
            b = true;
        }
        return b;
    }
    private boolean esCicloFor (String lexema) {
        boolean b = false;
        Matcher matcher = cicloForPattern.matcher(lexema);
        if (matcher.matches()) {
            b = true;
        }
        return b;
    }
    private boolean esDelimIzq (String lexema) {
        boolean b = false;
        Matcher matcher = delimIzqPattern.matcher(lexema);
        if (matcher.matches()) {
            b = true;
        }
        return b;
    }
    private boolean esDelimDer (String lexema) {
        boolean b = false;
        Matcher matcher = delimDerPattern.matcher(lexema);
        if (matcher.matches()) {
            b = true;
        }
        return b;
    }
}