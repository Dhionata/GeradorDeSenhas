import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public final class UserInterface {
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

    private UserInterface() { //Núcleo da UserInterface
        textNumber.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                textNumber.setValue("");
            }
        });

        Document document = textNumber.getDocument();
        document.addDocumentListener(new DocumentListener() {
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
        frame.setContentPane(new UserInterface().rootPane);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    private void copy() {
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(senha), null);
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(rootPane, "Reportar ao desenvolvedor!\n%s".formatted(e.getMessage()),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void qualVaiGerar() {
        if (textNumber.getText().isBlank()) {
            System.out.println("Rapaz, isso tá vazio!");
        } else {
            try {
                int temp = Integer.parseInt(textNumber.getText());
                if (0 < temp) {
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
                    System.out.printf("vai copiar...\nSenha:\n%s%n", senha);
                    copy();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Deve-se ter um valor maior que 0.",
                            "Então...", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (HeadlessException | NumberFormatException e) {
                JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Deu Ruim!", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
