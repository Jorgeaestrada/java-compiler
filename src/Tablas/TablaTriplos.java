package Tablas;

import java.util.ArrayList;

public class TablaTriplos {

    private static ArrayList<EntradasTriplo> entradas = new ArrayList<>();
    private static StringBuilder stringBuilder = new StringBuilder();

    public void agregarEntrada (int linea, String datoObjeto, String datoFuente, String operador) {
        entradas.add(new EntradasTriplo(linea, datoObjeto, datoFuente, operador));
    }

    public String resultado () {
        for(int i = 0; i < entradas.size(); i++) {
            EntradasTriplo entrada = entradas.get(i);
            stringBuilder.append(entrada.toString());
        }
        return stringBuilder.toString();
    }

    public class EntradasTriplo {

        private String datoObjeto;
        private String datoFuente;
        private String operador;
        private int linea;

        public void limpiarTablaTriplos () {
            stringBuilder.setLength(0);
            entradas.clear();
        }

        public EntradasTriplo(int linea, String datoObjeto, String datoFuente, String operador) {
            this.linea = linea;
            this.datoObjeto = datoObjeto;
            this.datoFuente = datoFuente;
            this.operador = operador;
        }

        @Override
        public String toString() {
            return linea + "\t" + datoObjeto + "\t" + datoFuente + "\t" + operador+ "\n";
        }

    }
}
