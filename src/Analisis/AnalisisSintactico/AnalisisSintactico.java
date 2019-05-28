package Analisis.AnalisisSintactico;

import Tablas.TablaTriplos;
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
                    "[\\s][A-Za-z]+[\\s](<|>|<=|>=)[\\s]([A-Za-z]+|[0-9]+)+[\\s][;]" +
                    "[\\s][A-Za-z]+[\\s][+][\\s][+][\\s][)]";
    private static final String DELIM_IZQ = "[{]";
    private static final String DELIM_DER = "[}]";

    private ArbolSintactico arbolSintactico = new ArbolSintactico();
    private TablaTriplos tablaTriplos = new TablaTriplos();

    private static StringBuilder stringBuilder = new StringBuilder();
    private Pattern expAritPattern = Pattern.compile(EXP_ARIT);
    private Pattern decVarPattern = Pattern.compile(DEC_VAR);
    private Pattern cicloForPattern = Pattern.compile(CICLO_FOR);
    private Pattern delimIzqPattern = Pattern.compile(DELIM_IZQ);
    private Pattern delimDerPattern = Pattern.compile(DELIM_DER);

    private static String array[], arrayAux[], arrayTriplo[], arrayFor[];
    private static int lineaSalto;
    private static String varInicioFor;
    private static String datoObjeto = "T";
    private static String varRelacional = "TR";
    private static String datoFuente;
    private static String operador;
    private static int contLinea = 1;
    private static int contDatoObjeto = 1;
    private static int contVarRel = 1;
    private static int contLineaTriplo = 1;

    /*
    * Método que genera un "arbol sintáctico"
    * Convierte una expresion aritmetica arbolSintactico su formatomydata
    * en postfijo
    */
    public void iniciarSintactico (String s) {
        stringBuilder.setLength(0);
        contLineaTriplo = 1;
        contDatoObjeto = 1;
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

                arrayTriplo = array[i].split(" ");

                for(int k = 2; k < arrayTriplo.length - 1; k++) {
                    if (k == 2) {
                        datoObjeto = datoObjeto + contDatoObjeto;
                        datoFuente = arrayTriplo[k];
                        operador = "=";
                        System.out.println("linea: " + contLineaTriplo
                                + " DatoObjeto: " + datoObjeto
                                + " DatoFuente: " + datoFuente
                                + " operador: " + operador);

                        tablaTriplos.agregarEntrada(contLineaTriplo, datoObjeto, datoFuente, operador);

                        contLineaTriplo++;
                    } else if (k % 2 != 0 && !arrayTriplo[k + 1].equals("")) {
                        datoObjeto = datoObjeto + contDatoObjeto;
                        datoFuente = arrayTriplo[k + 1];
                        operador = arrayTriplo[k];
                        System.out.println("linea: " + contLineaTriplo
                                + " DatoObjeto: " + datoObjeto
                                + " DatoFuente: " + datoFuente
                                + " operador: " + operador);

                        tablaTriplos.agregarEntrada(contLineaTriplo, datoObjeto, datoFuente, operador);

                        contLineaTriplo++;
                    } else if (k == arrayTriplo.length - 2) {
                        System.out.println("linea: " + contLineaTriplo
                                + " DatoObjeto: " + arrayTriplo[0]
                                + " DatoFuente: " + datoObjeto + contDatoObjeto
                                + " operador: " + arrayTriplo[1]);

                        tablaTriplos.agregarEntrada(contLineaTriplo, arrayTriplo[0], datoObjeto + contDatoObjeto, arrayTriplo[1]);
                        contLineaTriplo++;
                    }
                    datoObjeto = "T";
                }

                contDatoObjeto++;

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
                arrayFor = array[i].split(" ");

                lineaSalto = contLineaTriplo;
                varInicioFor = datoObjeto + contDatoObjeto;
                tablaTriplos.agregarEntrada(contLineaTriplo, datoObjeto + contDatoObjeto, arrayFor[3], arrayFor[4]);
                contDatoObjeto++;
                contLineaTriplo++;
            } else if (esDelimDer(array[i])) {
                tablaTriplos.agregarEntrada(contLineaTriplo, varInicioFor, "1", "+");
                contLineaTriplo++;
                tablaTriplos.agregarEntrada(contLineaTriplo, arrayFor[3], varInicioFor, "=");
                contLineaTriplo++;
                tablaTriplos.agregarEntrada(contLineaTriplo, varInicioFor, arrayFor[9], arrayFor[8]);
                contLineaTriplo++;

                varRelacional = varRelacional + contVarRel;
                tablaTriplos.agregarEntrada(contLineaTriplo, varRelacional, "TRUE", String.valueOf(lineaSalto));
                contVarRel++;
                varRelacional = "TR"+contVarRel;

                contLineaTriplo++;
                int falso = contLineaTriplo + 1;
                tablaTriplos.agregarEntrada(contLineaTriplo, varRelacional, "FALSE", String.valueOf(falso));
                contVarRel++;
                varRelacional = "TR"+contVarRel;

                contDatoObjeto++;
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