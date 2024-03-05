import Generator.allMixed
import Generator.letterNumbersAccentsAndSpecials
import Generator.letterNumbersAndAccents
import Generator.letters
import Generator.lettersAndAccents
import Generator.lettersAndNumbers
import Generator.lettersNumbersAndSpecials
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

        val number: Int = textNumberText.toInt()

        if (number > 1000000) {
            JOptionPane.showMessageDialog(
                rootPane, "Deve-se ter um valor menor que 1.000.000\n" +
                        "Vai escrever um livro aleatório?", "Então…", JOptionPane.WARNING_MESSAGE
            )
            System.err.println("Deve-se ter um valor menor que 1.000.000\nVai escrever um livro aleatório?")
            return false
        }

        if (number <= 0) {
            JOptionPane.showMessageDialog(
                rootPane,
                "Deve-se ter um valor maior que 0.",
                "Então…",
                JOptionPane.WARNING_MESSAGE
            )
            System.err.println("Deve-se ter um valor maior que 0.")
            return false
        }

        return true
    }

    private fun generatePassword(length: Int) {
        copyToClipboard(
            when (comboBox.getSelectedIndex()) {
                0 -> letters(length)
                1 -> numbers(length)
                2 -> lettersAndAccents(length)
                3 -> lettersAndNumbers(length)
                4 -> letterNumbersAndAccents(length)
                5 -> lettersNumbersAndSpecials(length)
                6 -> letterNumbersAccentsAndSpecials(length)
                7 -> allMixed(length)
                else -> throw IllegalArgumentException("Opção inválida.")
            }
        )
    }
}
