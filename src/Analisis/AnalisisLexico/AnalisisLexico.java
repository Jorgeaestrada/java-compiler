package Analisis.AnalisisLexico;

import com.google.common.collect.*;
import utils.LeerArchivo;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
1.- ANALISIS LEXICO

        El analizador lexico opera bajo peticion del analizador sintactico con el
        fin de que este pueda avanzar en la gramatica. Tiene como proposito agrupar
        las expresiones en fichas.

        Esta compuesto por 3 elementos:  Compornentes Lexicos (Token), Lexemas, Patron

        distancia = velocidad + aceleracion  * 30


        FUNCIONES ANALISIS LEXICO

        - Eliminacion de espacios en blanco
        - Generación de Tokens
        - Manejo de errores Léxicos:
        - flloaat x;
*/

public class AnalisisLexico {
    /*
    * TOKENS:
    */
    private static final String TIPOS = "(int|double|float|String)";
    private static final String VAR = "[A-Za-z]*|";
    private static final String OP_REL = "(>|<|>=|<=|!=|==|=)";
    private static final String NUM = "([0-9]*)";
    private static final String DELIM = "[(]|[{]|[}]|[)]|[;]";
    private static final String OP_AR = "[-+*/]";
    private static final String PAL_RES = "(for)";

    Pattern tiposPattern = Pattern.compile(TIPOS);
    Pattern varPattern = Pattern.compile(VAR);
    Pattern opRelPattern = Pattern.compile(OP_REL);
    Pattern numPattern = Pattern.compile(NUM);
    Pattern delimPattern = Pattern.compile(DELIM);
    Pattern opArPattern = Pattern.compile(OP_AR);

    //Pattern forPattern = Pattern.compile(PAL_RES);
    /*
    * Genera Tokens para cada entrada
    */
    private String varToken = "VAR_";
    private String opRelToken = "OP_REL_";
    private String opAritToken = "OP_AR_";
    private String numToken = "NUM_";
    private String delimToken = "DELIM_";
    private String tiposToken = "TIPO_";

    //private static int contLinea = 0;
    private static int contVarToken = 1;
    private static int contOpRelToken = 1;
    private static int contOpAritToken = 1;
    private static int contNumToken = 1;
    private static int contDelimToken = 1;
    private static int contTiposToken = 1;

    private static LinkedListMultimap<String, String> hashMapTokens = LinkedListMultimap.create();
    private static final String directorioTexto = "src/utils/archivo.txt";
    private static String array[];

    /*
     * Método que Crea las entradas en la tabla de símbolos
     */
    public void iniciarLexico () throws IOException {

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

        for (int i = 0; i < array.length; i++) {
            generaToken(array[i]);
        }
        /*
         * Descomentar para generar tabla de simbolos
         * en la terminal
         */
        System.out.println("----------------------------");
        System.out.println("----------------------------");
        System.out.println("Tabla de Símbolos\n");
        System.out.printf("%-15s%s\n\n", "lexema", "token");
        for (Map.Entry<String, String> entry : getHashMapTokens().entries()) {
            String lexema = entry.getKey();
            String token = entry.getValue();
            System.out.printf("%-15s%s\n", lexema, token);
        }
        System.out.println("----------------------------");
        System.out.println("----------------------------");
    }

    private void generaToken (String lexema) {

        if (esTipo(lexema)) {
            if (estaEnLista(lexema)) {
                String key = hashMapTokens.keySet().iterator().next();
                String value = hashMapTokens.get(key).iterator().next();
                hashMapTokens.put(lexema, value);
                //System.out.println("Clave:" + key + "Value: " + value);
            } else {
                hashMapTokens.put(lexema, tiposToken + contTiposToken);
                contTiposToken++;}
        } else if (esVar(lexema)) {
            if (estaEnLista(lexema)) {
                String key = hashMapTokens.keySet().iterator().next();
                String value = hashMapTokens.get(key).iterator().next();
                hashMapTokens.put(lexema, value);
                //System.out.println("Clave:" + key + "Value: " + value);
            } else {
                hashMapTokens.put(lexema, varToken + contVarToken);
                contVarToken++;
            }
        } else if (esNum(lexema)) {
            if (estaEnLista(lexema)) {
                String key = hashMapTokens.keySet().iterator().next();
                String value = hashMapTokens.get(key).iterator().next();
                hashMapTokens.put(lexema, value);
                //System.out.println("Clave:" + key + "Value: " + value);
            } else {
                hashMapTokens.put(lexema, numToken + contNumToken);
                contNumToken++;
            }
        } else if (esOpArit(lexema)) {
            if (estaEnLista(lexema)) {
                String key = hashMapTokens.keySet().iterator().next();
                String value = hashMapTokens.get(key).iterator().next();
                hashMapTokens.put(lexema, value);
                //System.out.println("Clave:" + key + "Value: " + value);
            } else {
                hashMapTokens.put(lexema, opAritToken + contOpAritToken);
                contOpAritToken++;
            }
        } else if (esOpRel(lexema)) {
            if (estaEnLista(lexema)) {
                String key = hashMapTokens.keySet().iterator().next();
                String value = hashMapTokens.get(key).iterator().next();
                hashMapTokens.put(lexema, value);
                //System.out.println("Clave:" + key + "Value: " + value);
            } else {
                hashMapTokens.put(lexema, opRelToken + contOpRelToken);
                contOpRelToken++;
            }
        } else if (esDelim(lexema)){
            if (estaEnLista(lexema)) {
                String key = hashMapTokens.keySet().iterator().next();
                String value = hashMapTokens.get(key).iterator().next();
                hashMapTokens.put(lexema, value);
                //System.out.println("Clave:" + key + "Value: " + value);
            } else {
                hashMapTokens.put(lexema, delimToken + contDelimToken);
                contDelimToken++;
            }
        } else {
            // Soltar un error léxico
        }
    }

    /*
    * Comprueba si un lexema ya está agregado en la lista
     */
    private boolean estaEnLista (String lexema) {
        boolean b = false;
        if (hashMapTokens.containsKey(lexema)){
            b = true;
        }
        return b;
    }

    public LinkedListMultimap<String, String> getHashMapTokens() {
        return hashMapTokens;
    }

    private boolean esTipo(String token) {
        boolean b = false;

        Matcher matcher = tiposPattern.matcher(token);

        if (matcher.matches()) {
            b = true;
        }
        return b;
    }

    private boolean esDelim(String token) {
        boolean b = false;

        Matcher matcher = delimPattern.matcher(token);

        if (matcher.matches()) {
            b = true;
        }
        return b;
    }

    /*
    private boolean esFor(String token) {
        boolean b = false;

        Matcher matcher = forPattern.matcher(token);

        if (matcher.matches()) {
            b = true;
        }
        return b;
    }
     */

    private boolean esVar(String token) {
        boolean b = false;

        Matcher matcher = varPattern.matcher(token);

        if (matcher.matches()) {
            b = true;
        }
        /*
        * Generar error léxico y agregar a Tabla de errores
        */
        return b;
    }

    private boolean esOpRel(String token) {
        boolean b = false;

        Matcher matcher = opRelPattern.matcher(token);

        if (matcher.matches()){
            b = true;
        }
        /*
         * Generar error léxico y agregar a Tabla de errores
         */
        return b;
    }

    private boolean esOpArit(String token) {
        boolean b = false;

        Matcher matcher = opArPattern.matcher(token);

        if (matcher.matches()){
            b = true;
        }
        /*
         * Generar error léxico y agregar a Tabla de errores
         */
        return b;
    }

    private boolean esNum(String token) {
        boolean b = false;

        Matcher matcher = numPattern.matcher(token);

        if (matcher.matches()){
            b = true;
        }
        /*
         * Generar error léxico y agregar a Tabla de errores
         */
        return b;
    }
}
