package Tablas;

public class TablaSimbolos {

    //Salida es un léxema y un tipo de dato
    private static int linea;
    private String token;
    private String lexema;
    private String tipo;

    public TablaSimbolos () {
        linea = 0;
        token = "";
        lexema = "";
        tipo = "";
    }

    public void agregar (int linea, String token, String lexema, String tipo) {
        this.linea = linea + 1;
        this.token = token;
        this.lexema = lexema;
        this.tipo = tipo;
    }

    public static int getLinea() {
        return linea;
    }

    public static void setLinea(int linea) {
        TablaSimbolos.linea = linea;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
