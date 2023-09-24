package test

import Generator
import Generator.allMixed
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GeneratorTest {
	private val n = 1000000

	@Test
	fun letters() {
		val letters = Generator.letters(n)
		Assertions.assertEquals(true, letters.all { it.isLetter() })
	}

	@Test
	fun numbers() {
		val numbers = Generator.numbers(n)
		Assertions.assertEquals(true, numbers.all { it.isDigit() })
	}

	@Test
	fun `letters and accents`() {
		val lettersAndAccents = Generator.lettersAndAccents(n)
		Assertions.assertEquals(
			true, lettersAndAccents.matches("^[a-zA-zçÇ${Generator.ACCENTS}]+\$".toRegex())
		)
	}

	@Test
	fun `letters and numbers`() {
		val lettersAndNumbers = Generator.lettersAndNumbers(n)
		Assertions.assertEquals(true, lettersAndNumbers.all { it.isLetterOrDigit() })
	}

	@Test
	fun `letter, accents and numbers`() {
		val letterAccentsAndNumbers = Generator.letterAccentsAndNumbers(n)
		Assertions.assertEquals(
			true,
			letterAccentsAndNumbers.all { it.isLetterOrDigit() || Generator.ACCENTS.contains(it) })
	}

	@Test
	fun `letters, numbers and caracteres`() {
		val lettersNumbersAndCaracteres = Generator.lettersNumbersAndCaracteres(n)
		Assertions.assertEquals(true, lettersNumbersAndCaracteres.all {
			it.isLetterOrDigit() || Generator.SPECIALS.contains(it)
		})
	}

	@Test
	fun unicoded() {
		Assertions.assertEquals(false, Generator.unicodeChars.any {
			it.isLetterOrDigit() || Generator.SPECIALS.contains(it) || Generator.ACCENTS.contains(it) || it.isISOControl()
		})
	}

	@Test
	fun `test of generated Unicode Chars`() {
		repeat(10) {
			val passwordUnicode = Generator.generateRandomString(n, listOf(Generator.unicodeChars.joinToString("")))
			val any = passwordUnicode.any {
				it.isLetterOrDigit() || it.isISOControl() || Generator.ACCENTS.contains(it) ||
						Generator.SPECIALS.contains(it)
			}
			Assertions.assertEquals(false, any)
		}
	}

	@Test
	fun `numbers of any caracter`() {
		val allMixed = allMixed(n).groupingBy { it }.eachCount().toList().sortedBy { (_, value) -> value }.toMap()
		allMixed.forEach { (chave, valor) ->
			println("O caractere '$chave' se repete $valor vezes")
		}
	}
}
