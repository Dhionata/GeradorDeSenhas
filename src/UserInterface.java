import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public final class UserInterface {
	private JPanel rootPane;
	private JFormattedTextField textNumber;
	private JComboBox<String> comboBox;

	private UserInterface() {
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
				// Não é acionado para alterações no conteúdo do texto e sim alterações no estilo
			}
		});

		comboBox.addItemListener(e ->
				SwingUtilities.invokeLater(() -> {
					textNumber.setText("");
					textNumber.requestFocus();
				}));
	}

	public static void start() {
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
