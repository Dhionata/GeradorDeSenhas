object Generator {
	private const val LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
	private const val NUMBERS = "0123456789"
	const val ACCENTS = "àáâãäèéêëìíîïòóôõöùúûüýÿÁÀÂÃÄÉÈÊËÍÌÎÏÓÒÔÖÕÚÙÛÜÝ"
	const val SPECIALS = "!@²#%¨\"$&*()-_+/.\$£³¢¬?<>,;:°çÇ~^{}ª[]º'|§´`"
	val unicodeChars =
		CharRange('\u0000', '\uFFFF').filterNot { it.isLetterOrDigit() }.filterNot { SPECIALS.contains(it) }
			.filterNot { ACCENTS.contains(it) }

	@JvmStatic
	fun generateRandomString(n: Int, functions: List<Char>): String = buildString {
		repeat(n) { append(functions.random()) }
	}

	@JvmStatic
	fun letters(n: Int): String = generateRandomString(n, listOf(LETTERS.random()))

	@JvmStatic
	fun numbers(n: Int): String = generateRandomString(n, listOf(NUMBERS.random()))

	@JvmStatic
	fun lettersAndAccents(n: Int): String = generateRandomString(n, listOf(LETTERS.random(), ACCENTS.random()))

	@JvmStatic
	fun lettersAndNumbers(n: Int): String = generateRandomString(n, listOf(LETTERS.random(), NUMBERS.random()))

	@JvmStatic
	fun letterAccentsAndNumbers(n: Int): String =
		generateRandomString(n, listOf(LETTERS.random(), NUMBERS.random(), ACCENTS.random()))

	@JvmStatic
	fun lettersNumbersAndCaracteres(n: Int): String =
		generateRandomString(n, listOf(LETTERS.random(), NUMBERS.random(), SPECIALS.random()))

	@JvmStatic
	fun allMixed(n: Int): String = generateRandomString(
		n, listOf(LETTERS.random(), NUMBERS.random(), ACCENTS.random(), SPECIALS.random(), unicodeChars.random())
	)
}
