package Tablas;

public class Entrada {

    //Salida es un lexema y un tipo de dato
    private static int linea = 0;
    private String token;
    private String lexema;
    private String tipo;

    public Entrada (String token, String lexema, String tipo) {
        this.linea = linea + 1;
        this.token = token;
        this.lexema = lexema;
        this.tipo = tipo;
    }
}
