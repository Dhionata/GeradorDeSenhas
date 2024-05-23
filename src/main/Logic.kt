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
import java.math.BigInteger
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

    private class PasswordStrength {
        fun estimatePasswordStrength(password: String): String {
            val length = password.length
            val uniqueChars = password.toSet().size

            // Calcula o espaço de busca usando BigInteger
            val totalCombinations = BigInteger.valueOf(uniqueChars.toLong()).pow(length)

            // Assume uma taxa de tentativas por segundo
            val attemptsPerSecond = BigInteger.valueOf(10_000_000_000) // 1 bilhão de tentativas por segundo

            // Calcula o tempo em segundos
            val secondsToCrack = totalCombinations.divide(attemptsPerSecond)

            // Convertendo o tempo em uma forma mais legível
            val timeToCrack = formatTime(secondsToCrack)

            return timeToCrack
        }

        private fun formatTime(seconds: BigInteger): String {
            val secondsInMinute = BigInteger.valueOf(60)
            val secondsInHour = secondsInMinute.multiply(BigInteger.valueOf(60))
            val secondsInDay = secondsInHour.multiply(BigInteger.valueOf(24))
            val secondsInYear = secondsInDay.multiply(BigInteger.valueOf(365))
            val secondsInCentury = secondsInYear.multiply(BigInteger.valueOf(100))
            val secondsInMillennium = secondsInYear.multiply(BigInteger.valueOf(1000))

            return when {
                seconds < secondsInMinute -> "$seconds segundos"
                seconds < secondsInHour -> "${seconds.divide(secondsInMinute)} minutos"
                seconds < secondsInDay -> "${seconds.divide(secondsInHour)} horas"
                seconds < secondsInYear -> "${seconds.divide(secondsInDay)} dias"
                seconds < secondsInCentury -> "${seconds.divide(secondsInYear)} anos"
                seconds < secondsInMillennium -> "${seconds.divide(secondsInCentury)} séculos"
                else -> "${seconds.divide(secondsInMillennium)} milênios"
            }
        }
    }
}
