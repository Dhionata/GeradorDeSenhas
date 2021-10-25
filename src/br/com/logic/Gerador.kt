package br.com.logic

object Gerador {
    private val letras = arrayListOf(
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
    private val numeros = arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    private val especiais = arrayListOf(
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

    private fun soLetras(): String = letras[(0 until letras.size).random()]

    private fun soNumeros(): Int = numeros[(0 until numeros.size).random()]

    private fun soEspeciais(): String = especiais[(0 until especiais.size).random()]

    @JvmStatic
    fun letras(n: Int): String {
        var senha = ""
        repeat(n) {
            senha += soLetras()
        }
        return senha
    }

    @JvmStatic
    fun numeros(n: Int): String {
        var senha = ""
        repeat(n) {
            senha += soNumeros()
        }
        return senha
    }

    @JvmStatic
    fun letrasENumeros(n: Int): String {
        var senha = ""
        repeat(n) {
            when ((1..2).random()) {
                1 -> {
                    senha += soLetras()
                }
                2 -> {
                    senha += soNumeros()
                }
            }
        }
        return senha
    }

    @JvmStatic
    fun tudoMisturado(n: Int): String {
        var senha = ""
        repeat(n) {
            when ((1..3).random()) {
                1 -> {
                    senha += soLetras()
                }
                2 -> {
                    senha += soNumeros()
                }
                3 -> {
                    senha += soEspeciais()
                }
            }
        }
        return senha
    }
}
