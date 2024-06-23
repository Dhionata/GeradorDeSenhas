package main

import main.Generator.allMixed
import main.Generator.letterNumbersAccentsAndSpecials
import main.Generator.letterNumbersAndAccents
import main.Generator.letters
import main.Generator.lettersAndAccents
import main.Generator.lettersAndNumbers
import main.Generator.lettersNumbersAndSpecials
import main.Generator.numbers
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.util.logging.Level.INFO
import java.util.logging.Level.WARNING
import java.util.logging.Logger
import javax.swing.*

class Logic {
    private lateinit var rootPane: JPanel
    private lateinit var textNumber: JFormattedTextField
    private lateinit var comboBox: JComboBox<String>
    private val logger = Logger.getLogger(this.javaClass.name)

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
            logger.log(WARNING, "O valor $textNumberText não passou pelo Regex!")
            return false
        }

        val number: Int = textNumberText.toInt()

        if (number > 1000000) {
            JOptionPane.showMessageDialog(
                rootPane,
                "Deve-se ter um valor menor que 1.000.000\nVai escrever um livro aleatório?",
                "Então…",
                JOptionPane.WARNING_MESSAGE
            )
            logger.log(WARNING, "Deve-se ter um valor menor que 1.000.000\nVai escrever um livro aleatório?")
            return false
        }

        if (number <= 0) {
            JOptionPane.showMessageDialog(
                rootPane, "Deve-se ter um valor maior que 0.", "Então…", JOptionPane.WARNING_MESSAGE
            )
            logger.log(INFO, "Deve-se ter um valor maior que 0.")
            return false
        }

        return true
    }

    private fun generatePassword(length: Int) {
        copyToClipboard(when (comboBox.getSelectedIndex()) {
            0 -> letters(length)
            1 -> numbers(length)
            2 -> lettersAndAccents(length)
            3 -> lettersAndNumbers(length)
            4 -> letterNumbersAndAccents(length)
            5 -> lettersNumbersAndSpecials(length)
            6 -> letterNumbersAccentsAndSpecials(length)
            7 -> allMixed(length)
            else -> throw IllegalArgumentException("Opção inválida.")
        }.also {
            val passwordStrength = PasswordStrength().estimatePasswordStrength(it)
            logger.log(
                INFO, "\nTempo para quebrar: $passwordStrength"

            )
        })
    }
}
