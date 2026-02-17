package test.main

import java.lang.Integer.toHexString
import java.util.logging.Level.INFO
import java.util.logging.Logger
import main.Generator
import main.PasswordType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test

class GeneratorTest {
    private val n = 1000000
    private val logger = Logger.getLogger(this.javaClass.name)

    @Test
    fun `test letters only`() {
        val result = PasswordType.LETTERS.generate(n)
        Assertions.assertEquals(n, result.length)
        Assertions.assertTrue(result.all { it in Generator.letters })
    }

    @Test
    fun `test numbers only`() {
        val result = PasswordType.NUMBERS.generate(n)
        Assertions.assertEquals(n, result.length)
        Assertions.assertTrue(result.all { it in Generator.numbers })
    }

    @Test
    fun `test letters and numbers`() {
        val result = PasswordType.LETTERS_NUMBERS.generate(n)
        val combined = Generator.letters + Generator.numbers
        Assertions.assertTrue(result.all { it in combined })
    }

    @Test
    fun `test letters numbers and specials`() {
        val result = PasswordType.LETTERS_NUMBERS_SPECIALS.generate(n)
        val combined = Generator.letters + Generator.numbers + Generator.specials
        Assertions.assertTrue(result.all { it in combined })
    }

    @RepeatedTest(5)
    fun `distribution, average and unique characters`() {
        val result = PasswordType.ALL_MIXED.generate(n)
        val allMixed = result.groupingBy { it }.eachCount().toList().sortedBy { it.second }

        allMixed.forEach { (caractere, quantidade) ->
            val unicode = "\\U+${toHexString(caractere.code)}".uppercase()
            logger.log(INFO, "O caractere $caractere se repete $quantidade vezes, código Unicode $unicode\n")
        }

        val maxCount = allMixed.maxByOrNull { it.second }?.second ?: 0
        val minCount = allMixed.minByOrNull { it.second }?.second ?: 0
        val biggestDifference = maxCount - minCount

        val average = allMixed.map { it.second }.average()
        val unique = allMixed.size

        logger.log(
            INFO,
            "A média é $average com a maior diferença sendo $biggestDifference\nO número total de caracteres únicos foi de $unique\nNúmero total de " +
                    "caracteres: ${allMixed.sumOf { it.second }}"
        )

        val expectedAverage = n.toDouble() / PasswordType.ALL_MIXED.getPoolSize()
        Assertions.assertTrue(average <= expectedAverage + 1.0)
    }

    @Test
    fun `test empty length`() {
        val result = PasswordType.LETTERS.generate(0)
        Assertions.assertEquals("", result)
    }
}
