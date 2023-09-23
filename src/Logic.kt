import Generator.allMixed
import Generator.letterAccentsAndNumbers
import Generator.letters
import Generator.lettersAndAccents
import Generator.lettersAndNumbers
import Generator.lettersNumbersAndCaracteres
import Generator.numbers
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import javax.swing.*

object Logic {
	private lateinit var rootPane: JPanel
	private lateinit var textNumber: JFormattedTextField
	private lateinit var comboBox: JComboBox<String>

	private fun copyToClipboard(password: String) {
		val selection = StringSelection(password)
		val clipboard = Toolkit.getDefaultToolkit().systemClipboard
		clipboard.setContents(selection, selection)
		System.gc()
	}

	fun init(rootPane: JPanel, textNumber: JFormattedTextField, comboBox: JComboBox<String>) {

		this.rootPane = rootPane
		this.textNumber = textNumber
		this.comboBox = comboBox

		val textNumberText: String = textNumber.getText()
		if (textNumberText.isNotBlank() && isValidNumericInput(textNumberText)) {
			generatePassword(textNumberText.toInt())
		} else {
			SwingUtilities.invokeLater { textNumber.text = "" }
		}
	}

	private fun isValidNumericInput(textNumberText: String): Boolean {

		if (!textNumberText.matches("\\d+".toRegex())) {
			System.err.println("O valor $textNumberText não passou pelo Regex!")
			return false
		}

		val number = textNumberText.toInt()

		if (number > 10000000 && isSafeToContinue()) {
			System.err.println("O usuário optou por não prosseguir.")
			return false
		}

		if (number <= 0) {
			JOptionPane.showMessageDialog(
				rootPane, "Deve-se ter um valor maior que 0.", "Então...", JOptionPane.INFORMATION_MESSAGE
			)
			System.err.println("O valor deve ser maior que 0.")
			return false
		}

		return true
	}

	private fun isSafeToContinue(): Boolean {
		val message = "O valor digitado é muito grande e pode causar problemas de memória. Deseja continuar?"
		val option = JOptionPane.showConfirmDialog(
			rootPane, message, "Atenção", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE
		)
		return option != JOptionPane.OK_OPTION
	}

	private fun generatePassword(length: Int) {
		val password = when (comboBox.getSelectedIndex()) {
			0 -> letters(length)
			1 -> numbers(length)
			2 -> lettersAndAccents(length)
			3 -> lettersAndNumbers(length)
			4 -> letterAccentsAndNumbers(length)
			5 -> lettersNumbersAndCaracteres(length)
			6 -> allMixed(length)
			else -> throw IllegalArgumentException("Opção inválida selecionada.")
		}
		copyToClipboard(password)
	}
}
