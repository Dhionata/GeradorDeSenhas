package br.com.ui;

import br.com.logic.Gerador;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class UI {
    private JButton gerarButton;
    private JTextField textoSenha;
    private JPanel rootPane;
    private JTextField textNumero;


    public UI() {
        gerarButton.addActionListener(e -> {
            try {
                textoSenha.setText(new Gerador().tudoMisturado(Integer.parseInt(textNumero.getText())));
                copy();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(rootPane, "Deu ruim cara...", "Vixi...", JOptionPane.ERROR_MESSAGE);
            }
        });
        textNumero.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                textNumero.setText("");
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
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String text = textoSenha.getText();
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, null);
    }
}




