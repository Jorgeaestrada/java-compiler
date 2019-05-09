package utils;

import java.io.*;

public class LeerArchivo {

    StringBuilder stringBuilder = new StringBuilder();

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
}