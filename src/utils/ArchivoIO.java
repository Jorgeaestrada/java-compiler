package utils;

import java.io.*;

public class ArchivoIO {

    private static final String rutaEscritura = "src/utils/AnalisisLexico.txt";

    private static StringBuilder stringBuilder = new StringBuilder();

    public String leer(String archivo) throws IOException {
        stringBuilder.setLength(0);
        try(BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            for(String line; (line = br.readLine()) != null; ) {
                stringBuilder.append(line + "\n");
            }
        }
        return stringBuilder.toString();
    }

    public void escribir (String archivo) {
        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(rutaEscritura);
            printWriter.println(archivo);
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}