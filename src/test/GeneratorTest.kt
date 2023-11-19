package test

import Generator
import Generator.accents
import Generator.allMixed
import Generator.specials
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
        Assertions.assertTrue(lettersAndAccents.all { it.isLetter() || accents.contains(it) })
    }

    @Test
    fun `letters and numbers`() {
        val lettersAndNumbers = Generator.lettersAndNumbers(n)
        Assertions.assertTrue(lettersAndNumbers.all { it.isLetterOrDigit() })
    }

    @Test
    fun `letter, numbers and accents`() {
        val letterAccentsAndNumbers = Generator.letterNumbersAndAccents(n)
        Assertions.assertTrue(letterAccentsAndNumbers.all { it.isLetterOrDigit() || accents.contains(it) })
    }

    @Test
    fun `letters, numbers and specials`() {
        val lettersNumbersAndCaracteres = Generator.lettersNumbersAndSpecials(n)
        Assertions.assertTrue(lettersNumbersAndCaracteres.all {
            it.isLetterOrDigit() || specials.contains(it)
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
            it.isLetterOrDigit() || specials.contains(it) || accents.contains(it)
        })
    }

    @RepeatedTest(5)
    fun `distribution, average and unique characters`() {
        val allMixed = allMixed(n).groupingBy { it }.eachCount().toList().sortedBy { it.second }

        allMixed.forEach { (caractere, quantidade) ->
            val unicode = String.format("\\u%04x", caractere.code)
            val prefixo = if (caractere.isDefined()) "" else "\n--Não definido--\n"
            println("$prefixo O caractere '$caractere' se repete $quantidade vezes, código Unicode $unicode\n")
        }

        val biggestDifference =
            allMixed.maxBy { it.second }.second.minus(allMixed.minBy { it.second }.second)

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

        Assertions.assertTrue(
            biggestDifference <= average
        )
    }
}
