object Generator {
    private const val LETTERS = "abcçdefghijklmnopqrstuvwxyzABCÇDEFGHIJKLMNOPQRSTUVWXYZ"
    private const val NUMBERS = "0123456789"
    private const val ACCENTS = "àáâãäèéêëìíîïòóôõöùúûüýÿ"
    private val unicodeChars = CharRange('\u0100', '\uFFFF').filter { !it.isLetterOrDigit() }
    private val specials = "!@²#%¨$&*()-_+/.\$£³¢¬?<>,;:°çÇ~^{}ª[]º'|".toCharArray()

    private fun onlyLetters(): Char = LETTERS.random()
    private fun onlyNumbers(): Char = NUMBERS.random()
    private fun onlyAccents(): Char = ACCENTS.random()
    private fun onlyUnicodeChars(): Char = unicodeChars.random()
    private fun onlySpecials(): Char = specials.random()

    @JvmStatic
    fun letters(n: Int): String = buildString { repeat(n) { append(onlyLetters()) } }

    @JvmStatic
    fun numbers(n: Int): String = buildString { repeat(n) { append(onlyNumbers()) } }

    @JvmStatic
    fun lettersAndAccents(n: Int): String = buildString {
        repeat(n) { append(listOf(onlyLetters(), onlyAccents()).random()) }
    }

    @JvmStatic
    fun lettersAndNumbers(n: Int): String =
        buildString { repeat(n) { append(listOf(onlyLetters(), onlyNumbers()).random()) } }


    @JvmStatic
    fun letterAccentsAndNumbers(n: Int): String = buildString {
        repeat(n) {
            append(listOf(onlyLetters(), onlyAccents(), onlyNumbers()).random())
        }
    }

    @JvmStatic
    fun lettersNumbersAndCaracteres(n: Int): String = buildString {
        repeat(n) {
            append(listOf(onlyLetters(), onlyNumbers(), onlySpecials()).random())
        }
    }

    @JvmStatic
    fun allMixed(n: Int): String = buildString {
        repeat(n) {
            append(
                listOf(onlyLetters(), onlyNumbers(), onlyAccents(), onlyUnicodeChars(), onlySpecials()).random()
            )
        }
    }

}