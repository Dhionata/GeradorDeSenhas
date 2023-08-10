package test

import Generator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GeneratorTest {
    private val n = 1000000

    @Test
    fun letters() {
        val letters = Generator.letters(n)
        letters.forEach {
            if (!it.isLetter()) {
                Exception("ins't letter: $it")
            }
        }
        Assertions.assertEquals(true, letters.matches("^[a-zA-ZçÇ]+\$".toRegex()))
    }

    @Test
    fun numbers() {
        val numbers = Generator.numbers(n)
        numbers.forEach {
            if (!it.isDigit()) {
                Exception("ins't digit: $it")
            }
        }
        Assertions.assertEquals(true, numbers.matches("^[0-9]+\$".toRegex()))
    }

    @Test
    fun lettersAndAccents() {
        val lettersAndAccents = Generator.lettersAndAccents(n)
        lettersAndAccents.forEach {
            if (!it.isLetter() && !lettersAndAccents.contains("^[çÇ${Generator.ACCENTS}]+$".toRegex())) {
                Exception("ins't a letter or accent: $it")
            }
        }
        Assertions.assertEquals(
            true, lettersAndAccents.matches("^[a-zA-zçÇ${Generator.ACCENTS}]+\$".toRegex())
        )
    }

    @Test
    fun lettersAndNumbers() {
        val lettersAndNumbers = Generator.lettersAndNumbers(n)
        lettersAndNumbers.forEach {
            if (!it.isLetterOrDigit()) Exception("ins't letter or digit: $it")
        }
        Assertions.assertEquals(true, lettersAndNumbers.matches("^[a-zA-ZçÇ0-9]+\$".toRegex()))
    }

    @Test
    fun letterAccentsAndNumbers() {
        val letterAccentsAndNumbers = Generator.letterAccentsAndNumbers(n)
        letterAccentsAndNumbers.forEach {
            if (!it.isLetterOrDigit() && letterAccentsAndNumbers.contains("^[${Generator.ACCENTS}]+$".toRegex())) {
                Exception("ins't letter, accent or number: $it")
            }
        }
        Assertions.assertEquals(
            true, letterAccentsAndNumbers.matches("^[a-zA-ZçÇ0-9${Generator.ACCENTS}]+\$".toRegex())
        )
    }

    @Test
    fun lettersNumbersAndCaracteres() {
        val lettersNumbersAndCaracteres = Generator.lettersNumbersAndCaracteres(n)
        lettersNumbersAndCaracteres.forEach {
            if (!it.isLetterOrDigit() && lettersNumbersAndCaracteres.contains("^[${Generator.SPECIALS}]]+$".toRegex())) {
                Exception("ins't letter, number and special caracter: $it")
            }
        }
        Assertions.assertEquals(
            true, lettersNumbersAndCaracteres.matches("^[a-zA-ZçÇ0-9${Generator.SPECIALS}]]+\$".toRegex())
        )
    }

    @Test
    fun unicoded() {
        Generator.unicodeChars.forEach {
            if (it.isLetterOrDigit() || it.toString().contains(
                    "^[a-zA-ZçÇ0-9${Generator.SPECIALS}${Generator.ACCENTS}]]+\$".toRegex()
                )
            ) {
                Exception("It's wrong! $it")
            }
        }

        fun generateUnicodeChars(n: Int) = Generator.generateRandomString(n, listOf(Generator.unicodeChars::random))

        repeat(10) {
            val passwordUnicode = generateUnicodeChars(n)
            passwordUnicode.forEach {
                if (it.isLetterOrDigit() || passwordUnicode.contains(
                        "^[${Generator.SPECIALS}${Generator.ACCENTS}]]+$".toRegex()
                    )
                ) {
                    Exception("\nsomething is wrong here!\n $it\n\n")
                }
                if (Generator.SPECIALS.contains(it) || Generator.ACCENTS.contains(it)) {
                    Exception("I found the same: $it")
                }
            }
            Assertions.assertEquals(
                false,
                passwordUnicode.contains("^[a-zA-Z0-9${Generator.ACCENTS}${Generator.SPECIALS}]]+$".toRegex())
            )
        }
    }

}