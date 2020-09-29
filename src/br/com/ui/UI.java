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
    private String senha;


    public UI() {

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
                if (!textNumero.getText().equals("")) {
                    gerarSenha();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!textNumero.getText().equals("")) {
                    gerarSenha();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    private void gerarSenha() {
        try {
            senha = new Gerador().tudoMisturado(Integer.parseInt(textNumero.getText()));
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
    }
}




