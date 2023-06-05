import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public final class UserInterface {
    private static final int INT = 10000000;
    private JPanel rootPane;
    private JFormattedTextField textNumber;
    private JComboBox comboBox1;

    private UserInterface() { // Núcleo da UserInterface
        textNumber.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleNumericInputChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleNumericInputChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Nada a ser feito quando o estilo do texto é alterado
            }
        });
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

    private static void copyToClipboard(String password) {
        StringSelection selection = new StringSelection(password);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    private void handleNumericInputChange() {
        if (isValidNumericInput()) {
            whichWillGenerate();
        }
    }

    private boolean isValidNumericInput() {
        if (textNumber.getText().isBlank()) {
            System.err.println("O campo está vazio!");
            return false;
        }

        String input = textNumber.getText();
        try {
            int number = Integer.parseInt(input);
            if (number > INT && !isSafeToContinue()) {
                System.out.println("O usuário optou por não prosseguir.");
                return false;
            }
            return true;
        } catch (NumberFormatException ex) {
            System.err.printf("O valor \"%s\" não é um número válido.%n", input);
            SwingUtilities.invokeLater(() -> textNumber.setText(""));
            return false;
        }
    }

    private boolean isSafeToContinue() {
        String message = "O valor digitado é muito grande e pode causar problemas de memória. Deseja continuar?";
        int option = JOptionPane.showConfirmDialog(rootPane, message, "Atenção", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE);
        return option == JOptionPane.OK_OPTION;
    }

    private void whichWillGenerate() {
        try {
            int temp = Integer.parseInt(textNumber.getText());
            if (temp <= 0) {
                JOptionPane.showMessageDialog(rootPane, "Deve-se ter um valor maior que 0.",
                        "Então...", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            var temp2 = generatePassword(temp);
            System.out.printf("Senha gerada:\n%s%n", temp2);
            copyToClipboard(temp2);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Por favor, insira um número válido.",
                    "Deu Ruim!", JOptionPane.ERROR_MESSAGE);
        } catch (HeadlessException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage(),
                    "Deu Ruim!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generatePassword(int length) {
        String password = switch (comboBox1.getSelectedIndex()) {
            case 0 -> Generator.letters(length);
            case 1 -> Generator.numbers(length);
            case 2 -> Generator.lettersAndAccents(length);
            case 3 -> Generator.lettersAndNumbers(length);
            case 4 -> Generator.letterAccentsAndNumbers(length);
            case 5 -> Generator.lettersNumbersAndCaracteres(length);
            case 6 -> Generator.allMixed(length);
            default -> throw new IllegalArgumentException("Invalid option selected.");
        };

        copyToClipboard(password);
        return password;
    }

}