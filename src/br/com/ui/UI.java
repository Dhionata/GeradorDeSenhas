package br.com.ui;

import br.com.logic.Generator;

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
    private JFormattedTextField textNumber;
    private JRadioButton radioLatters;
    private JRadioButton lettersAndNumbersRadioButton;
    private JRadioButton lettersNumbersAndCaracteresRadioButton;
    private JRadioButton radioNumbers;
    private String senha;

    public static void comecar() {
        createUIComponents();
    }

    private UI() { //Núcleo da UI
        textNumber.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                textNumber.setValue("");
            }
        });

        textNumber.getDocument().addDocumentListener(new DocumentListener() {
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

    private void copy() {
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(senha), null);
            System.gc(); //Limpar memória.
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(rootPane, "Reportar ao desenvolvedor!\n" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void qualVaiGerar() {
        if (!textNumber.getText().isBlank()) {
            try {
                int temp = Integer.parseInt(textNumber.getText());
                if (temp > 0) {
                    if (radioLatters.isSelected()) {
                        senha = Generator.letters(temp);
                    } else if (lettersAndNumbersRadioButton.isSelected()) {
                        senha = Generator.lettersAndNumbers(temp);
                    } else if (lettersNumbersAndCaracteresRadioButton.isSelected()) {
                        senha = Generator.allMixed(temp);
                    } else if (radioNumbers.isSelected()) {
                        senha = Generator.numbers(temp);
                    } else {
                        throw new Exception("Rapaz... como vc tirou o ponto????");
                    }
                    System.out.println("vai copiar...\nSenha:\n" + senha);
                    copy();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Deve-se ter um valor maior que 0.", "Então...", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(rootPane, "Tem que ser um valor númerico !\n" + e.getMessage(), "Deui ruim!", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Deu Ruim!", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            System.out.println("Rapaz, isso tá vazio!");
        }
    }
}