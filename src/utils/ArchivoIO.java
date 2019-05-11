package utils;

import java.io.*;

public class ArchivoIO {

    private static final String rutaEscritura = "src/utils/AnalisisLexico.txt";

    private static StringBuilder stringBuilder = new StringBuilder();

    public String leer(String archivo) throws IOException {
        String cadena;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        while ((cadena = b.readLine()) != null) {
            stringBuilder.append(cadena + "\n");
        }
        b.close();
        return stringBuilder.toString();
    }

    public void escribir (String archivo) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(rutaEscritura);
            pw = new PrintWriter(fichero);

            pw.println(archivo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != fichero)
                    fichero.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}