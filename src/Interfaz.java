import Analisis.AnalisisLexico.AnalisisLexico;
import Analisis.AnalisisSintactico.AnalisisSintactico;
import Tablas.TablaTriplos;
import Tablas.TablaErrores;
import Tablas.TablaSimbolos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que genera la interfaz gráfica en la que
 * se mostrará la información procesada
 */
public class Interfaz extends JFrame implements ActionListener {

    private static AnalisisLexico lexico = new AnalisisLexico();
    private static AnalisisSintactico sintactico = new AnalisisSintactico();
    private static TablaErrores tablaErrores  = new TablaErrores();
    private static TablaSimbolos tablaSimbolos = new TablaSimbolos();
    private static TablaTriplos tablaTriplos = new TablaTriplos();

    private Container contenedor;
    //Creacion de jlabel
    private JLabel instrucciones;
    private JLabel postfijoLabel;
    private JLabel simboloLabel;
    private JLabel errorLabel;
    private JLabel triploLabel;
    //Declaracion de areas de texto
    private JTextArea codeTextArea;
    private JTextArea postfijoTextArea;
    private JTextArea simboloTextArea;
    private JTextArea errorTextArea;
    private JTextArea triploTextArea;
    //Declaracion de botones
    private JButton botonAbrir;
    private JButton botonGuardar;
    private JButton botonCompilar;
    //Declaracion de scrollpane
    private JScrollPane codePaneArea;
    private JScrollPane postfijoPaneArea;
    private JScrollPane simbolosPaneArea;
    private JScrollPane errorPaneArea;
    private JScrollPane triploPaneArea;
    //Clase FileUtils

    public Interfaz() {
        codePaneArea = new JScrollPane();
        postfijoPaneArea = new JScrollPane();
        simbolosPaneArea = new JScrollPane();
        errorPaneArea = new JScrollPane();
        triploPaneArea = new JScrollPane();
        codeTextArea = new JTextArea();
        postfijoTextArea = new JTextArea();
        simboloTextArea = new JTextArea();
        errorTextArea = new JTextArea();
        triploTextArea = new JTextArea();
        instrucciones = new JLabel();
        postfijoLabel = new JLabel();
        simboloLabel = new JLabel();
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

        postfijoLabel.setText("POSTFIJO");
        postfijoLabel.setBounds(420, 20, 180, 23);

        simboloLabel.setText("TABLA DE SIMBOLOS");
        simboloLabel.setBounds(580, 20, 180, 23);

        errorLabel.setText("TABLA DE ERRORES");
        errorLabel.setBounds(390, 320, 180, 23);

        triploLabel.setText("TRIPLOS");
        triploLabel.setBounds(620, 320, 180, 23);

        //permite que no queden palabras incompletas al hacer el salto de linea
        codeTextArea.setWrapStyleWord(true);
        postfijoTextArea.setWrapStyleWord(true);
        simboloTextArea.setWrapStyleWord(true);
        errorTextArea.setWrapStyleWord(true);
        triploTextArea.setWrapStyleWord(true);
        //Deshabilitamos la edición
        postfijoTextArea.setEditable(false);
        simboloTextArea.setEditable(false);
        errorTextArea.setEditable(false);
        triploTextArea.setEditable(false);

        codePaneArea.setBounds(20, 50, 330, 530);
        postfijoPaneArea.setBounds(370, 50, 170, 270);
        simbolosPaneArea.setBounds(570, 50, 170, 270);
        errorPaneArea.setBounds(370, 350, 170, 270);
        triploPaneArea.setBounds(570, 350, 170, 270);

        codePaneArea.setViewportView(codeTextArea);
        postfijoPaneArea.setViewportView(postfijoTextArea);
        simbolosPaneArea.setViewportView(simboloTextArea);
        errorPaneArea.setViewportView(errorTextArea);
        triploPaneArea.setViewportView(triploTextArea);

        codePaneArea.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        postfijoPaneArea.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        simbolosPaneArea.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        errorPaneArea.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        triploPaneArea.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

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
        contenedor.add(postfijoLabel);
        contenedor.add(simboloLabel);
        contenedor.add(errorLabel);
        contenedor.add(triploLabel);
        contenedor.add(codePaneArea);
        contenedor.add(postfijoPaneArea);
        contenedor.add(simbolosPaneArea);
        contenedor.add(errorPaneArea);
        contenedor.add(triploPaneArea);
        contenedor.add(botonAbrir);
        contenedor.add(botonGuardar);
        contenedor.add(botonCompilar);

        setTitle("COMPILADOR");
        setSize(900, 650);
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
                    String s = codeTextArea.getText();
                    lexico.iniciarLexico(s);
                    sintactico.iniciarSintactico(s);
                    /*
                    * Asignación de texto a textArea's
                     */
                    simboloTextArea.setText(tablaSimbolos.resultado());
                    postfijoTextArea.setText(sintactico.resultado());
                    errorTextArea.setText(tablaErrores.resultado());
                    triploTextArea.setText(tablaTriplos.resultado());
                    System.out.println(tablaTriplos.resultado());
            }
        }
    }
}

