import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public final class UserInterface {
    private JPanel rootPane;
    private JFormattedTextField textNumber;
    private JComboBox<String> comboBox;

    private UserInterface() { // Núcleo da UserInterface
        textNumber.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                Logic.INSTANCE.init(rootPane, textNumber, comboBox);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                Logic.INSTANCE.init(rootPane, textNumber, comboBox);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Não é acionado para alterações no conteúdo do texto
                // Nada a ser feito quando o estilo do texto é alterado
            }
        });

        comboBox.addItemListener(e ->
                SwingUtilities.invokeLater(() -> {
                    textNumber.setText("");
                    textNumber.requestFocus();
                }));
    }

    public static void comecar() {
        createUIComponents();
    }

    private static void createUIComponents() {
        JFrame frame = new JFrame("Gui");
        frame.setContentPane(new UserInterface().rootPane);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

}

