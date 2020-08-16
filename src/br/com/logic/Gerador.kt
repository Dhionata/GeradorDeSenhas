package br.com.logic

class Gerador {
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
        "§"
    )

    fun soLetras(n: Int): String {
        var senha = ""
        repeat(n) {
            senha += letras[(0 until letras.size).random()]
        }
        return senha
    }

    fun soNumeros(n: Int): String {
        var senha = ""
        repeat(n) {
            senha += numeros[(0 until numeros.size).random()].toString()
        }
        return senha
    }

    fun soEspeciais(n: Int): String {
        var senha = ""
        repeat(n) {
            senha += especiais[(0 until numeros.size).random()]
        }
        return senha
    }

    fun letrasENumeros(n: Int): String{
        var senha = ""
        repeat(n){
            when((1..2).random()){
                1 -> {
                    senha += soLetras(1)
                }
                2 -> {
                    senha += soNumeros(1)
                }
            }
        }
        return senha
    }

    fun tudoMisturado(n: Int): String {
        var senha = ""
        repeat(n) {
            when ((1..3).random()) {
                1 -> {
                    senha += soLetras(1)
                }
                2 -> {
                    senha += soNumeros(1)
                }
                3 -> {
                    senha += soEspeciais(1)
                }
            }
        }
        return senha
    }
}
