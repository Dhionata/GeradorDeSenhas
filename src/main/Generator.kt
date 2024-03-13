package main

object Generator {
    internal val letters = (('a'..'z') + ('A'..'Z'))
    internal val numbers = ('0'..'9').toList()
    internal val accents = "àáâãäèéêëìíîïòóôõöùúûüýÿÁÀÂÃÄÉÈÊËÍÌÎÏÓÒÔÖÕÚÙÛÜÝ".toList()
    internal val specials = "!@²#%¨\"&*()-_+/.\$£³¢¬?<>,;:°çÇ~^{}ª[]º'|§´`\\".toList()
    internal val unicodeChars = CharRange(Char.MIN_VALUE, Char.MAX_VALUE).filterNot {
        val original = it.toString()
        val bytes = original.toByteArray(Charsets.UTF_8)
        val decoded = bytes.toString(Charsets.UTF_8)
        letters.contains(it) || numbers.contains(it) || specials.contains(it) || accents.contains(it) || it.isISOControl() || original != decoded || it.isWhitespace() || !it.isDefined() || it.isIdentifierIgnorable()
    }.toList()

    @JvmStatic
    private fun generateRandomString(n: Int, functions: List<List<Char>>): String {
        val allCharacters = functions.flatten().shuffled()
        return buildString {
            repeat(n) { append(allCharacters.random()) }
        }
    }

    @JvmStatic
    fun letters(n: Int): String = generateRandomString(n, listOf(letters))

    @JvmStatic
    fun numbers(n: Int): String = generateRandomString(n, listOf(numbers))

    @JvmStatic
    fun lettersAndAccents(n: Int): String = generateRandomString(n, listOf(letters, accents))

    @JvmStatic
    fun lettersAndNumbers(n: Int): String = generateRandomString(n, listOf(letters, numbers))

    @JvmStatic
    fun letterNumbersAndAccents(n: Int): String = generateRandomString(n, listOf(letters, numbers, accents))

    @JvmStatic
    fun lettersNumbersAndSpecials(n: Int): String = generateRandomString(n, listOf(letters, numbers, specials))

    fun letterNumbersAccentsAndSpecials(n: Int): String = generateRandomString(n, listOf(letters, numbers, accents, specials))

    @JvmStatic
    fun allMixed(n: Int): String = generateRandomString(n, listOf(letters, numbers, accents, specials, unicodeChars))
}
