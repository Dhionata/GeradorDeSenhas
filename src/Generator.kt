object Generator {
    private val letters = listOf(
            "A",
            "B",
            "C",
            "D",
            "E",
            "F",
            "G",
            "H",
            "I",
            "J",
            "K",
            "L",
            "M",
            "N",
            "O",
            "P",
            "Q",
            "R",
            "S",
            "T",
            "U",
            "V",
            "W",
            "X",
            "Y",
            "Z",
            "a",
            "b",
            "c",
            "d",
            "e",
            "f",
            "g",
            "h",
            "i",
            "j",
            "k",
            "l",
            "m",
            "n",
            "o",
            "p",
            "q",
            "r",
            "s",
            "t",
            "u",
            "v",
            "w",
            "x",
            "y",
            "z"
    )
    private val numbers = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    private val specials = listOf(
            "!",
            "@",
            "²",
            "#",
            "%",
            "¨",
            "$",
            "&",
            "*",
            "(",
            ")",
            "-",
            "_",
            "+",
            "/",
            ".",
            "£",
            "³",
            "¢",
            "¬",
            "?",
            "<",
            ">",
            ";",
            ":",
            "°",
            "ç",
            "Ç",
            "~",
            "^",
            "{",
            "}",
            "ª",
            "[",
            "]",
            "º",
            "'",
            "=",
            "§",
            "|"
    )

    private fun onlyLetters(): String = letters.random()

    private fun onlyNumbers(): Int = numbers.random()

    private fun onlySpecials(): String = specials.random()

    @JvmStatic
    fun letters(n: Int): String = StringBuilder().apply { repeat(n) { append(onlyLetters()) } }.toString()

    @JvmStatic
    fun numbers(n: Int): String = StringBuilder().apply { repeat(n) { append(onlyNumbers()) } }.toString()

    @JvmStatic
    fun lettersAndNumbers(n: Int): String =
            StringBuilder().apply { repeat(n) { append(listOf(onlyLetters(), onlyNumbers()).random()) } }.toString()

    @JvmStatic
    fun allMixed(n: Int): String =
            StringBuilder().apply {
                repeat(n) { append(listOf(onlyLetters(), onlyNumbers(), onlySpecials()).random()) }
            }.toString()
}
