package test

import main.Generator
import main.Generator.accents
import main.Generator.allMixed
import main.Generator.letters
import main.Generator.numbers
import main.Generator.specials
import main.Generator.unicodeChars
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import java.lang.Integer.toHexString

class GeneratorTest {
    private val n = 1000000

    @Test
    fun letters() {
        val letters = letters(n)
        Assertions.assertTrue(letters.all { it.isLetter() })
    }

    @Test
    fun numbers() {
        val numbers = numbers(n)
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
            letters.contains(it) || numbers.contains(it) || specials.contains(it) || accents.contains(it)
        })
    }

    @RepeatedTest(5)
    fun `distribution, average and unique characters`() {
        val allMixed = allMixed(n).groupingBy { it }.eachCount().toList().sortedBy { it.second }

        allMixed.forEach { (caractere, quantidade) ->
            val unicode = "\\U+${toHexString(caractere.code)}".uppercase()
            println("O caractere $caractere se repete $quantidade vezes, código Unicode $unicode\n")
        }

        val biggestDifference = allMixed.maxBy { it.second }.second.minus(allMixed.minBy { it.second }.second)

        val average = allMixed.map { it.second }.average()
        val unique = allMixed.map { it.first }.size

        println("\n--Dados--\nA média é $average com a maior diferença sendo $biggestDifference\nO número total de caracteres únicos foi de $unique\nNúmero total de caracteres: ${allMixed.sumOf { it.second }}")

        Assertions.assertTrue(average <= 17)
    }
}
