import Analisis.AnalisisSintactico.AnalisisSintactico;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Clase que genera la interfaz gráfica en la que
 * se mostrará la información procesada
 */
public class Interfaz extends JFrame implements ActionListener {
    private Container contenedor;
    //Creacion de jlabel
    private JLabel instrucciones;
    private JLabel expLabel;
    private JLabel decLabel;
    private JLabel errorLabel;
    private JLabel triploLabel;
    //Declaracion de areas de texto
    private JTextArea codeTextArea;
    private JTextArea expTextArea;
    private JTextArea decTextArea;
    private JTextArea errorTextArea;
    private JTextArea triploTextArea;
    //Declaracion de botones
    private JButton botonAbrir;
    private JButton botonGuardar;
    private JButton botonCompilar;
    //Declaracion de scrollpane
    private JScrollPane codePaneArea;
    private JScrollPane expPaneArea;
    private JScrollPane decPaneArea;
    private JScrollPane errorPaneArea;
    private JScrollPane triploPaneArea;
    //Clase FileUtils

    public Interfaz() {
        codePaneArea = new JScrollPane();
        expPaneArea = new JScrollPane();
        decPaneArea = new JScrollPane();
        errorPaneArea = new JScrollPane();
        triploPaneArea = new JScrollPane();
        codeTextArea = new JTextArea();
        expTextArea = new JTextArea();
        decTextArea = new JTextArea();
        errorTextArea = new JTextArea();
        triploTextArea = new JTextArea();
        instrucciones = new JLabel();
        expLabel = new JLabel();
        decLabel = new JLabel();
        errorLabel = new JLabel();
        triploLabel = new JLabel();
        botonAbrir = new JButton();
        botonGuardar = new JButton();
        botonCompilar = new JButton();

        contenedor = getContentPane();
        contenedor.setLayout(null);

        //Insertamos titulo en una posicion determinada
        instrucciones.setText("INGRESE CODIGO JAVA");
        instrucciones.setBounds(100, 20, 180, 23);

        expLabel.setText("POSTFIJO");
        expLabel.setBounds(420, 20, 180, 23);

        decLabel.setText("TABLA DE SIMBOLOS");
        decLabel.setBounds(580, 20, 180, 23);

        errorLabel.setText("TABLA DE ERRORES");
        errorLabel.setBounds(390, 320, 180, 23);

        triploLabel.setText("TRIPLOS");
        triploLabel.setBounds(620, 320, 180, 23);

        //Ajusta el texto al textArea
        codeTextArea.setLineWrap(true);
        expTextArea.setLineWrap(true);
        decTextArea.setLineWrap(true);
        errorTextArea.setLineWrap(true);
        triploTextArea.setLineWrap(true);
        //permite que no queden palabras incompletas al hacer el salto de linea
        codeTextArea.setWrapStyleWord(true);
        expTextArea.setWrapStyleWord(true);
        decTextArea.setWrapStyleWord(true);
        errorTextArea.setWrapStyleWord(true);
        triploTextArea.setWrapStyleWord(true);
        //Deshabilitamos la edición
        expTextArea.setEditable(false);
        decTextArea.setEditable(false);
        errorTextArea.setEditable(false);
        triploTextArea.setEditable(false);

        codePaneArea.setBounds(20, 50, 330, 530);
        expPaneArea.setBounds(370, 50, 170, 270);
        decPaneArea.setBounds(570, 50, 170, 270);
        errorPaneArea.setBounds(370, 350, 170, 270);
        triploPaneArea.setBounds(570, 350, 170, 270);

        codePaneArea.setViewportView(codeTextArea);
        expPaneArea.setViewportView(expTextArea);
        decPaneArea.setViewportView(decTextArea);
        errorPaneArea.setViewportView(errorTextArea);
        triploPaneArea.setViewportView(triploTextArea);

        /*Propiedades del boton, lo instanciamos, posicionamos y
         * activamos los eventos*/
        botonAbrir.setText("Abrir");
        botonAbrir.setBounds(20, 595, 80, 23);
        botonAbrir.addActionListener(this);

        botonGuardar.setText("Guardar");
        botonGuardar.setBounds(140, 595, 80, 23);
        botonGuardar.addActionListener(this);

        botonCompilar.setText("Compilar");
        botonCompilar.setBounds(260, 595, 80, 23);
        botonCompilar.addActionListener(this);

        /*Agregamos los componentes al Contenedor*/
        contenedor.add(instrucciones);
        contenedor.add(expLabel);
        contenedor.add(decLabel);
        contenedor.add(errorLabel);
        contenedor.add(triploLabel);
        contenedor.add(codePaneArea);
        contenedor.add(expPaneArea);
        contenedor.add(decPaneArea);
        contenedor.add(errorPaneArea);
        contenedor.add(triploPaneArea);
        contenedor.add(botonAbrir);
        contenedor.add(botonGuardar);
        contenedor.add(botonCompilar);

        setTitle("COMPILADOR");
        setSize(750, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        //BOTON ABRIR
        if (evento.getSource().equals(botonAbrir)) {
        } else if (evento.getSource().equals(botonGuardar)) {
            //BOTON COMPILAR
        } else if (evento.getSource().equals(botonCompilar)) {
            //Si el texto tiene el formato correcto para el analisis
            //entonces continua
            if (codeTextArea != null) {
                AnalisisSintactico s = new AnalisisSintactico();
                try {
                    s.generarTablaSimbolos();
                } catch (IOException e) {


                }
            }
        }
    }
}

