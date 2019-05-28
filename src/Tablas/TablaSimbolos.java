package Tablas;

import java.util.ArrayList;

public class TablaSimbolos {

    private static ArrayList<EntradaSimbolo> entradas = new ArrayList<>();
    private static StringBuilder stringBuilder = new StringBuilder();

    public void agregarEntrada (int linea, String token, String lexema, String tipo) {
        entradas.add(new EntradaSimbolo(linea, token, lexema, tipo));
    }

    public String resultado () {
        for(int i = 0; i < entradas.size(); i++) {
            EntradaSimbolo entrada = entradas.get(i);
            stringBuilder.append(entrada.toString());
        }
        return stringBuilder.toString();
    }

    public void limpiarTablaSimbolos () {
        stringBuilder.setLength(0);
        entradas.clear();
    }

    public String buscarSimbolo(String value) {
        String result = "";
        for (int i = 0; i < entradas.size(); i++) {
            if(entradas.get(i).getLexema().equals(value)
            && entradas.get(i).getToken().matches("(VAR_)[1-9]+")){
                System.out.println("iguales");
                result = entradas.get(i).getTipo();
            }
        }
        return result;
    }

    private class EntradaSimbolo {

        private int linea;
        private String token;
        private String lexema;
        private String tipo;

        public EntradaSimbolo(int linea, String token, String lexema, String tipo) {
            this.linea = linea;
            this.token = token;
            this.lexema = lexema;
            this.tipo = tipo;
        }

        @Override
        public String toString() {
            return linea + "\t" + token + "\t" + lexema + "\t" + tipo+ "\n";
        }

        public int getLinea() {
            return linea;
        }

        public void setLinea(int linea) {
            this.linea = linea;
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
}


