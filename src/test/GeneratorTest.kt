package test

import Generator
import Generator.ACCENTS
import Generator.SPECIALS
import Generator.allMixed
import Generator.unicodeChars
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test

class GeneratorTest {
    private val n = 1000000

    @Test
    fun letters() {
        val letters = Generator.letters(n)
        Assertions.assertTrue(letters.all { it.isLetter() })
    }

    @Test
    fun numbers() {
        val numbers = Generator.numbers(n)
        Assertions.assertTrue(numbers.all { it.isDigit() })
    }

    @Test
    fun `letters and accents`() {
        val lettersAndAccents = Generator.lettersAndAccents(n)
        Assertions.assertTrue(lettersAndAccents.all { it.isLetter() || ACCENTS.contains(it) })
    }

    @Test
    fun `letters and numbers`() {
        val lettersAndNumbers = Generator.lettersAndNumbers(n)
        Assertions.assertTrue(lettersAndNumbers.all { it.isLetterOrDigit() })
    }

    @Test
    fun `letter, numbers and accents`() {
        val letterAccentsAndNumbers = Generator.letterNumbersAndAccents(n)
        Assertions.assertTrue(letterAccentsAndNumbers.all { it.isLetterOrDigit() || ACCENTS.contains(it) })
    }

    @Test
    fun `letters, numbers and specials`() {
        val lettersNumbersAndCaracteres = Generator.lettersNumbersAndSpecials(n)
        Assertions.assertTrue(lettersNumbersAndCaracteres.all {
            it.isLetterOrDigit() || SPECIALS.contains(it)
        })
    }

    @Test
    fun `letters, numbers, accents and specials`() {
        Assertions.assertFalse(Generator.letterNumbersAccentsAndSpecials(n).any {
            unicodeChars.contains(it)
        })
    }

    @Test
    fun unicodes() {
        Assertions.assertFalse(unicodeChars.any {
            it.isLetterOrDigit() || SPECIALS.contains(it) || ACCENTS.contains(it)
        })
    }

    @RepeatedTest(5)
    fun `distribution, average and unique characters`() {
        val allMixed = allMixed(n).groupingBy { it }.eachCount().toList().sortedBy { it.second }

        allMixed.forEach { (chave, valor) ->
            println(
                if (chave.isDefined()) "O caractere '$chave' se repete $valor vezes, código Unicode ${
                    String.format(
                        "\\u%04x", chave.code
                    )
                }" else "\n--Não definido--\nO caractere '$chave' se repete $valor vezes, código Unicode ${
                    String.format(
                        "\\u%04x", chave.code
                    )
                }\n"
            )
        }

        val biggestDifference =
            allMixed.maxByOrNull { it.second }?.second?.minus(allMixed.minByOrNull { it.second }?.second ?: return)

        val average = allMixed.map { it.second }.average()
        val unique = allMixed.map { it.first }.size
        val undefined = allMixed.filter { !it.first.isDefined() }

        println(
            "\n--Dados--\nA média é $average com a maior diferença sendo $biggestDifference" +
                    "\nO número total de caracteres únicos foi de $unique\n${
                        if (undefined.isNotEmpty()) "número total de caracteres não definidos é ${undefined.size}"
                        else ""
                    }\nNúmero total de caracteres: ${allMixed.sumOf { it.second }}"
        )

        if (biggestDifference != null) {
            Assertions.assertTrue(
                biggestDifference <= average
            )
        }
    }
}
