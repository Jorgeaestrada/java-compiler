package Tablas;

import java.util.ArrayList;

public class TablaErrores {

    private static ArrayList<EntradaErrores> entradas = new ArrayList<>();
    private static StringBuilder stringBuilder = new StringBuilder();

    public void agregarEntrada (int linea, String mensaje) {
        entradas.add(new EntradaErrores(linea, mensaje));
    }

    public String resultado () {
        for(int i = 0; i < entradas.size(); i++) {
            EntradaErrores entrada = entradas.get(i);
            stringBuilder.append(entrada.toString());
        }
        return stringBuilder.toString();
    }

    public void limpiarTablaErrores () {
        stringBuilder.setLength(0);
        entradas.clear();
    }

    private class EntradaErrores {

        private int linea;
        private String mensaje;

        public EntradaErrores(int linea, String mensaje) {
            this.linea = linea;
            this.mensaje = mensaje;
        }

        @Override
        public String toString() {
            return linea + "\t" + mensaje + "\n";
        }
    }
}


