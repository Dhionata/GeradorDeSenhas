package br.com.ui;

import br.com.logic.Gerador;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class UI {
    private JPanel rootPane;
    private JTextField textNumero;
    private JRadioButton radioLetras;
    private JRadioButton letrasENumerosRadioButton;
    private JRadioButton letrasNumerosECaracteresRadioButton;
    private String senha;

    private UI() { //Núcleo da UI
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

    }

    private void gerarSenha(int i) {
        try {
            if (i == 1) {
                senha = Gerador.soLetras(Integer.parseInt(textNumero.getText()));
            } else if (i == 2) {
                senha = Gerador.letrasENumeros(Integer.parseInt(textNumero.getText()));
            } else if (i == 3) {
                senha = Gerador.tudoMisturado(Integer.parseInt(textNumero.getText()));
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

    private void qualVaiGerar() {//equal == comparação
        if (!textNumero.getText().isBlank()) {
            int i = 0;
            if (radioLetras.isSelected()) {
                i = 1;
            } else if (letrasENumerosRadioButton.isSelected()) {
                i = 2;
            } else if (letrasNumerosECaracteresRadioButton.isSelected()) {
                i = 3;
            } else {
                JOptionPane.showMessageDialog(null, "Rapaz, tem que selecionar um dos 3 tipos de senhas...");
            }
            gerarSenha(i);
        }
    }
}