package Analisis.AnalisisLexico;

import java.util.TreeMap;
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
    private static final String ID = "[A-Za-z]*|";
    private static final String OP_REL = "(>|<|>=|<=|!=|==|=)";
    private static final String OP_ARIT = "[-*/+]";
    private static final String NUM = "([0-9]*|[0-9]*[.][0-9]*)";
    private static final String DELIM = "[(]|[{]|[}]|[)]|[;]";
    //private static final String PAL_RES = "(for)[(][)][{][}]";

    Pattern tiposPattern = Pattern.compile(TIPOS);
    Pattern idPattern = Pattern.compile(ID);
    Pattern opRelPattern = Pattern.compile(OP_REL);
    Pattern opAritPattern = Pattern.compile(OP_ARIT);
    Pattern numPattern = Pattern.compile(NUM);
    Pattern delimPattern = Pattern.compile(DELIM);
    //Pattern forPattern = Pattern.compile(PAL_RES);

    /*
    * Genera Tokens para cada entrada
    */
    private String idToken = "ID_";
    private String opRelToken = "OP_REL_";
    private String opAritToken = "OP_ARIT_";
    private String numToken = "NUM_";
    private String delimToken = "DEL_";

    //private static int contLinea = 0;
    private static int contIdToken = 0;
    private static int contOpRelToken = 0;
    private static int contOpAritToken = 0;
    private static int contNumToken = 0;
    private static int contDelimToken = 0;

    private static TreeMap<String, String> treeMapTokens = new TreeMap<>();

    public void generaToken (String lexema) {
        if (esTipo(lexema) !=estaEnLista(lexema)) {
            /*
            * Si detecta tipos de dato no hace nada
            */
        } else if (esId(lexema) && !estaEnLista(lexema)) {
            treeMapTokens.put(lexema, idToken + contIdToken);
            contIdToken++;
        } else if (esNum(lexema) && !estaEnLista(lexema)) {
            treeMapTokens.put(lexema, numToken + contNumToken);
            contNumToken++;
        } else if (esOpArit(lexema) && !estaEnLista(lexema)) {
            treeMapTokens.put(lexema, opAritToken + contOpAritToken);
            contOpAritToken++;
        } else if (esOpRel(lexema) && !estaEnLista(lexema)) {
            treeMapTokens.put(lexema, opRelToken + contOpRelToken);
            contOpRelToken++;
        } else if (esDelim(lexema) && !estaEnLista(lexema)){
            treeMapTokens.put(lexema, delimToken + contDelimToken);
            contDelimToken++;
        }
    }

    // Comprueba si un lexema ya está agregado en la lista
    public boolean estaEnLista (String lexema) {
        boolean b = false;
        if (treeMapTokens.containsKey(lexema)){
            b = true;
        }
        return b;
    }

    public TreeMap<String, String> getTreeMapTokens() {
        return treeMapTokens;
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

    private boolean esId(String token) {
        boolean b = false;

        Matcher matcher = idPattern.matcher(token);

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

        Matcher matcher = opAritPattern.matcher(token);

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
