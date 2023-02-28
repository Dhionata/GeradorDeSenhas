package br.com.logic

object Gerador {
    private val letras = listOf(
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
    private val numeros = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    private val especiais = listOf(
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

    private fun soLetras(): String = letras.random()

    private fun soNumeros(): Int = numeros.random()

    private fun soEspeciais(): String = especiais.random()

    @JvmStatic
    fun letras(n: Int): String = StringBuilder().apply { repeat(n) { append(soLetras()) } }.toString()

    @JvmStatic
    fun numeros(n: Int): String = StringBuilder().apply { repeat(n) { append(soNumeros()) } }.toString()

    @JvmStatic
    fun letrasENumeros(n: Int): String =
        StringBuilder().apply { repeat(n) { append(listOf(soLetras(), soNumeros()).random()) } }.toString()

    @JvmStatic
    fun tudoMisturado(n: Int): String =
        StringBuilder().apply { repeat(n) { append(listOf(soLetras(), soNumeros(), soEspeciais()).random()) } }
            .toString()

}
