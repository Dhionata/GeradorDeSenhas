package br.com.ui;

import br.com.logic.Gerador;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;

public class UI {
    private JPanel rootPane;
    private JTextField textNumero;
    private JRadioButton radioLetras;
    private JRadioButton letrasENumerosRadioButton;
    private JRadioButton letrasNumerosECaracteresRadioButton;
    private String senha;


    private UI() {

        textNumero.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                textNumero.setText("");
            }
        });

        textNumero.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                qualVaiGerar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                qualVaiGerar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        ActionListener listener1 = e -> {
            System.out.println(e.getActionCommand());
            switch (e.getActionCommand()) {
                case "Letras":
                    letrasENumerosRadioButton.setSelected(false);
                    letrasNumerosECaracteresRadioButton.setSelected(false);
                    break;
                case "Letras e Números":
                    radioLetras.setSelected(false);
                    letrasNumerosECaracteresRadioButton.setSelected(false);
                    break;
                case "Letras, Números e Caracteres":
                    radioLetras.setSelected(false);
                    letrasENumerosRadioButton.setSelected(false);
                    break;
            }
        };

        radioLetras.addActionListener(listener1);
        letrasENumerosRadioButton.addActionListener(listener1);
        letrasNumerosECaracteresRadioButton.addActionListener(listener1);
    }

    private void gerarSenha(int i) {
        try {
            switch (i) {
                case 1:
                    senha = Gerador.soLetras(Integer.parseInt(textNumero.getText()));
                    break;
                case 2:
                    senha = Gerador.letrasENumeros(Integer.parseInt(textNumero.getText()));
                    break;
                case 3:
                    senha = Gerador.tudoMisturado(Integer.parseInt(textNumero.getText()));
                    break;
            }
            copy();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, "Deu ruim cara...\n--Erro--\n\n" + ex, "Vixi...", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void createUIComponents() {
        JFrame frame = new JFrame("Gui");
        frame.setContentPane(new UI().rootPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    public static void comecar() {
        createUIComponents();
    }

    private void copy() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(senha), null);
        System.out.println(senha);
    }

    private void qualVaiGerar() {
        if (!textNumero.getText().equals("")) {
            int i = 0;
            if (radioLetras.isSelected()) {
                i = 1;
            } else if (letrasENumerosRadioButton.isSelected()) {
                i = 2;
            } else if (letrasNumerosECaracteresRadioButton.isSelected()) {
                i = 3;
            }
            gerarSenha(i);
        }
    }
}




