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
    private JComboBox<String> comboBox1;

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
                // Não é acionado para alterações no conteúdo do texto
                // Nada a ser feito quando o estilo do texto é alterado
            }
        });

        comboBox1.addItemListener(e -> SwingUtilities.invokeLater(() -> textNumber.setText("")));
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
        System.gc();
    }

    private void handleNumericInputChange() {
        if (!textNumber.getText().isBlank() && isValidNumericInput()) {
            whichWillGenerate();
        } else {
            SwingUtilities.invokeLater(() -> textNumber.setText(""));
        }
    }

    private boolean isValidNumericInput() {
        String input = textNumber.getText();
        try {
            if (!input.matches("\\d+")) {
                throw new NumberFormatException("Não passou pelo Regex!\n");
            }
            int number = Integer.parseInt(input);
            if (number > INT && !isSafeToContinue()) {
                System.out.println("O usuário optou por não prosseguir.");
                return false;
            }
            return true;
        } catch (NumberFormatException ex) {
            System.err.printf("O valor \"%s\" não é um número válido.\nErro: %s", input, ex.getMessage());
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
            generatePassword(temp);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Por favor, insira um número válido.\nEx: " + e.getMessage(),
                    "Deu Ruim!", JOptionPane.ERROR_MESSAGE);
        } catch (HeadlessException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage(),
                    "Deu Ruim!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generatePassword(int length) {
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
        System.out.printf("Senha gerada:\n%s\n", password);
    }
}

