package br.com.logic

fun main() {
    val gerador = Gerador()
    val entrada = entrada()

    println("Gerador de letras:\n${gerador.soLetras(entrada)}")
    println("Gerador de números:\n${gerador.soNumeros(entrada)}")
    println("Gerador de especiais:\n${gerador.soEspeciais(entrada)}")
    println("Gerador de letras e números:\n${gerador.letrasENumeros(entrada)}")
    println("Gerador aleatório:\n${gerador.tudoMisturado(entrada)}")
}