package br.com.logic

fun main() {
    val gerador = Gerador()
    var entrada = 0
    do {
        try {
            entrada = readLine()!!.toInt()
            print("entrada : $entrada")
        } catch (e: Exception) {
            print("Deu ruim, tem que ser um número cara...\nErro : \n$e")
        }
    } while (entrada == 0)
    println(gerador.soLetras(entrada))
    println(gerador.soNumeros(entrada))
    println(gerador.soEspeciais(entrada))
}