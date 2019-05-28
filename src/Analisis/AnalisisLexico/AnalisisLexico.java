package Analisis.AnalisisLexico;

import Tablas.TablaTriplos;
import Tablas.TablaErrores;
import Tablas.TablaSimbolos;
import com.google.common.collect.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalisisLexico {

    private LinkedListMultimap<String, String> hashMapTokens = LinkedListMultimap.create();
    private static String array[], arrayAux[];
    private static TablaSimbolos tablaSimbolos = new TablaSimbolos();
    private static TablaErrores tablaErrores = new TablaErrores();
    private static TablaTriplos tablaTriplos = new TablaTriplos();
    /*
    * REGEX para comprobar lexemas
     */
    private static final String TIPOS = "(int|double|float|String|boolean)";
    private static final String VAR = "[A-Za-z]+";
    private static final String OP_REL = "(>|<|>=|<=|!=|==|=)";
    private static final String NUM = "([0-9]+)";
    private static final String DELIM = "[(]|[{]|[}]|[)]|[;]|[,]";
    private static final String OP_AR = "[-+*/]";
    private static final String PAL_RES = "(for)";

    Pattern tiposPattern = Pattern.compile(TIPOS);
    Pattern varPattern = Pattern.compile(VAR);
    Pattern opRelPattern = Pattern.compile(OP_REL);
    Pattern numPattern = Pattern.compile(NUM);
    Pattern delimPattern = Pattern.compile(DELIM);
    Pattern opArPattern = Pattern.compile(OP_AR);
    Pattern palResPattern = Pattern.compile(PAL_RES);
    /*
    * Nombres de Tokens para cada entrada
    */
    private String varToken = "VAR_";
    private String opRelToken = "OP_REL_";
    private String opAritToken = "OP_AR_";
    private String numToken = "NUM_";
    private String delimToken = "DELIM_";
    private String tiposToken = "TIPO_";
    private String palResToken = "PAL_RES_";
    private String tipo = "";
    /*
     * Contadores para cada Token
     */
    private static int contLineaErrores = 1;
    private static int contLineaSimbolos = 1;
    private static int contVarToken = 1;
    private static int contOpRelToken = 1;
    private static int contOpAritToken = 1;
    private static int contNumToken = 1;
    private static int contDelimToken = 1;
    private static int contTiposToken = 1;
    private static int contPalResToken = 1;
    /*
     * Método que crea las entradas en la tabla de símbolos
     * se llama para hacer el procedimiento principal
     */
    public void iniciarLexico (String s) {

        hashMapTokens.clear();
        tablaSimbolos.limpiarTablaSimbolos();
        tablaErrores.limpiarTablaErrores();
        tablaTriplos.limpiarTablaTriplos();

        /*
         * Reemplaza punto y coma, saltos de linea
         * y cualquier cantidad de espacios en blanco
         * y genera los Tokens
         */
        s = s.replaceAll("(;)", " ;");
        s = s.replaceAll(",", " , ");
        s = s.replaceAll("[\\n]+([\\t]*[\\s]*|[\\s]*[\\t]*)", "\n");
        //s = s.replaceAll("(\\s)+", " ");
        s = s.trim();
        //System.out.println(s);
        array = s.split("[\\n]");

        for (int i = 0; i < array.length; i ++) {
            arrayAux = array[i].split("[\\s]+");
            for (int j = 0; j < arrayAux.length; j++) {
                generaToken(arrayAux[j]);
                contLineaSimbolos++;
                System.out.println("token: "+ arrayAux[j]);
                System.out.println("linea simbolos:" + contLineaSimbolos);
            }
            contLineaErrores++;
        }
        limpiarVariables();
    }

    public void limpiarVariables () {
        hashMapTokens.clear();
        contLineaErrores = 1;
        contLineaSimbolos = 1;
        contVarToken = 1;
        contOpRelToken = 1;
        contOpAritToken = 1;
        contNumToken = 1;
        contDelimToken = 1;
        contTiposToken = 1;
        contPalResToken = 1;
    }

    private void generaToken (String lexema) {
        if (esTipo(lexema)) {
            tipo = lexema;
            if (!estaEnLista(lexema)) {
                hashMapTokens.put(lexema,tiposToken + contTiposToken);
                tablaSimbolos.agregarEntrada(contLineaSimbolos, tiposToken + contTiposToken, lexema, "");
                contTiposToken++;
            }
        } else if (esPalRes(lexema)){
            if (!estaEnLista(lexema)) {
                hashMapTokens.put(lexema, palResToken + contPalResToken);
                tablaSimbolos.agregarEntrada(contLineaSimbolos, palResToken + contPalResToken, lexema, "");
                contDelimToken++;
            }
        } else if (esVar(lexema)) {
            if (!estaEnLista(lexema)) {
                if(tipo.equals("")) {
                    tablaErrores.agregarEntrada(contLineaErrores, "Error Semántico: Linea "+ contLineaErrores+ ", la variable '" + lexema + "' no está declarada");
                }
                hashMapTokens.put(lexema, varToken + contVarToken);
                tablaSimbolos.agregarEntrada(contLineaSimbolos,varToken + contVarToken, lexema, tipo);
                contVarToken++;
            }
        } else if (esNum(lexema)) {
            if (!estaEnLista(lexema)) {
                hashMapTokens.put(lexema, numToken + contNumToken);
                tablaSimbolos.agregarEntrada(contLineaSimbolos, numToken + contNumToken, lexema, "");
                contNumToken++;
            }
        } else if (esOpArit(lexema)) {
            if (!estaEnLista(lexema)) {
                hashMapTokens.put(lexema, opAritToken + contOpAritToken);
                tablaSimbolos.agregarEntrada(contLineaSimbolos, opAritToken + contOpAritToken, lexema, "");
                contOpAritToken++;
            }
        } else if (esOpRel(lexema)) {
            if (!estaEnLista(lexema)) {
                hashMapTokens.put(lexema, opRelToken + contOpRelToken);
                tablaSimbolos.agregarEntrada(contLineaSimbolos, opRelToken + contOpRelToken, lexema, "");
                contOpRelToken++;
            }
        } else if (esDelim(lexema)){
            if (lexema.equals(";")){
                tipo = "";
            }
            if (!estaEnLista(lexema)) {
                hashMapTokens.put(lexema, delimToken + contDelimToken);
                tablaSimbolos.agregarEntrada(contLineaSimbolos, delimToken + contDelimToken, lexema, "");
                contDelimToken++;
            }
        } else {
            tablaErrores.agregarEntrada(contLineaErrores, "Error Lexico: el lexema '" + lexema + "' no forma parte del lenguaje");
        }
    }
    /*
    * Comprueba si un lexema ya está agregado en la lista
    * de ser así, recupera el token asignado a este para
    * evitar lexemas con tokens diferentes
     */
    private boolean estaEnLista (String lexema) {
        boolean b = false;
        if (hashMapTokens.containsKey(lexema)){
            List l = hashMapTokens.get(lexema);
            Object s = l.iterator().next();
            b = true;
        }
        return b;
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

    private boolean esPalRes(String token) {
        boolean b = false;
        Matcher matcher = palResPattern.matcher(token);
        if (matcher.matches()) {
            b = true;
        }
        return b;
    }
}
