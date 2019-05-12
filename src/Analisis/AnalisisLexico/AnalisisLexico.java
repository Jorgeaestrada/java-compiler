package Analisis.AnalisisLexico;

import com.google.common.collect.*;
import utils.ArchivoIO;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalisisLexico {

    private static LinkedListMultimap<String, String> hashMapTokens = LinkedListMultimap.create();
    private static StringBuilder stringBuilder = new StringBuilder();
    private static String array[], arrayAux[];

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
    /*
     * Contadores para cada Token
     */
    private static int contVarToken = 1;
    private static int contOpRelToken = 1;
    private static int contOpAritToken = 1;
    private static int contNumToken = 1;
    private static int contDelimToken = 1;
    private static int contTiposToken = 1;
    private static int contPalResToken = 1;

    private String rutaLectura = "src/utils/CodigoFuente.txt";
    /*
     * Método que crea las entradas en la tabla de símbolos
     * se llama para hacer el procedimiento principal
     */
    public void iniciarLexico () throws IOException {

        ArchivoIO archivo = new ArchivoIO();
        String s = archivo.leer(rutaLectura);
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
            }
        }

        /*
         * Descomentar para generar tabla de simbolos
         * en la terminal
         */
        System.out.println("----------------------------");
        System.out.println("----------------------------");
        System.out.println("Tabla de Símbolos\n");
        System.out.printf("%-15s%s\n\n", "lexema", "token");
        for (Map.Entry<String, String> entry : getLinkedListTokens().entries()) {
            String lexema = entry.getKey();
            String token = entry.getValue();
            System.out.printf("%-15s%s\n", lexema, token);
            stringBuilder.append(lexema + "\n");
        }
        System.out.println("----------------------------");
        System.out.println("----------------------------");

        escribirResultado();
    }

    public void escribirResultado () {
        ArchivoIO archivoIO = new ArchivoIO();
        archivoIO.escribir(stringBuilder.toString());
    }

    public LinkedListMultimap<String, String> getLinkedListTokens() {
        return hashMapTokens;
    }

    private void generaToken (String lexema) {
        if (esTipo(lexema)) {
            if (!estaEnLista(lexema)) {
                hashMapTokens.put(lexema, tiposToken + contTiposToken);
                contTiposToken++;
            }
        } else if (esPalRes(lexema)){
            if (!estaEnLista(lexema)) {
                hashMapTokens.put(lexema, palResToken + contPalResToken);
                contDelimToken++;
            }
        } else if (esVar(lexema)) {
            if (!estaEnLista(lexema)) {
                hashMapTokens.put(lexema, varToken + contVarToken);
                contVarToken++;
            }
        } else if (esNum(lexema)) {
            if (!estaEnLista(lexema)) {
                hashMapTokens.put(lexema, numToken + contNumToken);
                contNumToken++;
            }
        } else if (esOpArit(lexema)) {
            if (!estaEnLista(lexema)) {
                hashMapTokens.put(lexema, opAritToken + contOpAritToken);
                contOpAritToken++;
            }
        } else if (esOpRel(lexema)) {
            if (!estaEnLista(lexema)) {
                hashMapTokens.put(lexema, opRelToken + contOpRelToken);
                contOpRelToken++;
            }
        } else if (esDelim(lexema)){
            if (!estaEnLista(lexema)) {
                hashMapTokens.put(lexema, delimToken + contDelimToken);
                contDelimToken++;
            }
        } else {
            // Soltar un error léxico
            System.out.printf("Error Lexico:" + lexema + "<---");
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
            hashMapTokens.put(lexema, s.toString());
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
