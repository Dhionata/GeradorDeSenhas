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
    private JRadioButton radioNumeros;
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
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(senha), null);
            senha = null;
            System.gc(); //Limpar memória.
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(rootPane, "Reportar ao desenvolvedor!\n" + e.getMessage());
        }
    }

    private void qualVaiGerar() {
        if (!textNumero.getText().isBlank()) {
            try {
                int temp = Integer.parseInt(textNumero.getText());
                if (temp > 0) {
                    if (radioLetras.isSelected()) {
                        senha = Gerador.letras(temp);
                    } else if (letrasENumerosRadioButton.isSelected()) {
                        senha = Gerador.letrasENumeros(temp);
                    } else if (letrasNumerosECaracteresRadioButton.isSelected()) {
                        senha = Gerador.tudoMisturado(temp);
                    } else if (radioNumeros.isSelected()) {
                        senha = Gerador.numeros(temp);
                    }
                    copy();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Deve-se ter um valor maior que 0.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(rootPane, "Tem que ser um valor númerico !\n" + e.getMessage());
            }
        }
    }
}