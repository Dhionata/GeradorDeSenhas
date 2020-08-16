package br.com.logic

fun entrada(): Int {
    val entrada = 0
    do {
        try {
            readLine()!!.toInt()
            println("entrada : $entrada")
        } catch (e: Exception) {
            print("Deu ruim, tem que ser um número cara...\nErro : \n$e")
        }
    } while (entrada == 0)
    return entrada
}