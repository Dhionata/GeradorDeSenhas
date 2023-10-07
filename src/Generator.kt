object Generator {
    internal val letters = (('a'..'z') + ('A'..'Z')).joinToString("")
    internal val numbers = (0..9).joinToString("")
    internal const val ACCENTS = "àáâãäèéêëìíîïòóôõöùúûüýÿÁÀÂÃÄÉÈÊËÍÌÎÏÓÒÔÖÕÚÙÛÜÝ"
    internal const val SPECIALS = "!@²#%¨\"&*()-_+/.\$£³¢¬?<>,;:°çÇ~^{}ª[]º'|§´`\\"
    internal val unicodeChars = CharRange(
        Char.MIN_VALUE, Char.MAX_VALUE
    ).filterNot {
        it.isLetterOrDigit() || SPECIALS.contains(it) || ACCENTS.contains(it) || it.isWhitespace() || it.isISOControl()
                || !it.isDefined() || it.isIdentifierIgnorable() || it.isSurrogate()
    }.joinToString("")

    @JvmStatic
    fun generateRandomString(n: Int, functions: List<String>): String = buildString {
        val allCharacters = functions.joinToString("")
        repeat(n) { append(allCharacters.random()) }
    }

    @JvmStatic
    fun letters(n: Int) = generateRandomString(n, listOf(letters))

    @JvmStatic
    fun numbers(n: Int) = generateRandomString(n, listOf(numbers))

    @JvmStatic
    fun lettersAndAccents(n: Int) = generateRandomString(n, listOf(letters, ACCENTS))

    @JvmStatic
    fun lettersAndNumbers(n: Int) = generateRandomString(n, listOf(letters, numbers))

    @JvmStatic
    fun letterNumbersAndAccents(n: Int) = generateRandomString(n, listOf(letters, numbers, ACCENTS))

    @JvmStatic
    fun lettersNumbersAndSpecials(n: Int) = generateRandomString(n, listOf(letters, numbers, SPECIALS))

    fun letterNumbersAccentsAndSpecials(n: Int) = generateRandomString(n, listOf(letters, numbers, ACCENTS, SPECIALS))

    @JvmStatic
    fun allMixed(n: Int) = generateRandomString(n, listOf(letters, numbers, ACCENTS, SPECIALS, unicodeChars))
}
