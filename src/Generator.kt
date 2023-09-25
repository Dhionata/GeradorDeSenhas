object Generator {
    private const val LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private const val NUMBERS = "0123456789"
    const val ACCENTS = "àáâãäèéêëìíîïòóôõöùúûüýÿÁÀÂÃÄÉÈÊËÍÌÎÏÓÒÔÖÕÚÙÛÜÝ"
    const val SPECIALS = "!@²#%¨\"&*()-_+/.\$£³¢¬?<>,;:°çÇ~^{}ª[]º'|§´`\\"
    val unicodeChars =
        CharRange('\u0000', '\uFFFF').filterNot { it.isLetterOrDigit() }.filterNot { SPECIALS.contains(it) }
            .filterNot { ACCENTS.contains(it) }.filterNot { it.isISOControl() }

    @JvmStatic
    fun generateRandomString(n: Int, functions: List<String>): String = buildString {
        val allCharacters = functions.joinToString("")
        repeat(n) { append(allCharacters.random()) }
    }

    @JvmStatic
    fun letters(n: Int): String = generateRandomString(n, listOf(LETTERS))

    @JvmStatic
    fun numbers(n: Int): String = generateRandomString(n, listOf(NUMBERS))

    @JvmStatic
    fun lettersAndAccents(n: Int): String = generateRandomString(n, listOf(LETTERS, ACCENTS))

    @JvmStatic
    fun lettersAndNumbers(n: Int): String = generateRandomString(n, listOf(LETTERS, NUMBERS))

    @JvmStatic
    fun letterAccentsAndNumbers(n: Int): String = generateRandomString(n, listOf(LETTERS, NUMBERS, ACCENTS))

    @JvmStatic
    fun lettersNumbersAndCaracteres(n: Int): String = generateRandomString(n, listOf(LETTERS, NUMBERS, SPECIALS))

    @JvmStatic
    fun allMixed(n: Int): String = generateRandomString(
        n, listOf(
            LETTERS, NUMBERS, ACCENTS, SPECIALS, unicodeChars.joinToString("")
        )
    )
}
