package Analisis.AnalisisSintactico;

import java.util.*;

public class ArbolSintactico {

    private StringBuilder listaPostfijo = new StringBuilder();
    private Stack<String> pila = new Stack<>();
    private static TreeMap<Integer, String> treeMapPostfijo = new TreeMap<>();

    private String simboloTope = "";
    private int precedencia = 0;
    private static int linea = 0;
    /*
    * Método que convierte una expresión
    * infija a una expresión sufija
    */
    public void convertirPosfijo(String expInfija) {
        listaPostfijo.setLength(0);
        expInfija = expInfija.replaceAll(";","");
        String[] palabraDividida = expInfija.split("[\\s]");
        for (int i = 0; i < palabraDividida.length; i++) {
            char operando = palabraDividida[i].charAt(0);
            if (Character.isLetterOrDigit(operando)) {
                listaPostfijo.append(palabraDividida[i] + " ");
            } else if (palabraDividida[i].equals("(")) {
                pila.push(palabraDividida[i]);
            } else if (palabraDividida[i].equals(")")) {
                simboloTope = pila.pop();
                while (!simboloTope.equals("(")) {
                    listaPostfijo.append(simboloTope + " ");
                    simboloTope = pila.pop();
                }
            } else {
                while (!pila.isEmpty() &&
                        calcularPrecedencia(pila.lastElement()) >=
                                calcularPrecedencia(palabraDividida[i])) {
                    listaPostfijo.append(pila.pop() + " ");
                }
                pila.push(palabraDividida[i]);
            }
        }
        while (!pila.isEmpty()) {
            listaPostfijo.append(pila.pop() + " ");
        }
        // System.out.println("Postfijo: " + listaPostfijo.toString());
        treeMapPostfijo.put(linea, listaPostfijo.toString());
        linea++;
    }

    private int calcularPrecedencia(String simbolo) {
        switch (simbolo) {
            case "^":
                precedencia = 4;
                break;
            case "*":
            case "/":
                precedencia = 3;
                break;
            case "+":
            case "-":
                precedencia = 2;
                break;
            case "=":
                precedencia = 1;
                break;
            case ";":
                precedencia = 0;
                break;
        }
        return precedencia;
    }

    public TreeMap<Integer, String> getTreeMapPostfijo() {
        return treeMapPostfijo;
    }

}