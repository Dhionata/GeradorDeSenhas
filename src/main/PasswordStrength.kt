package main

import java.math.BigInteger

class PasswordStrength {
    fun estimatePasswordStrength(password: String): String {
        val length = password.length
        val uniqueChars = password.toSet().size

        // Calcula o espaço de busca usando BigInteger
        val totalCombinations = BigInteger.valueOf(uniqueChars.toLong()).pow(length)

        // Assume uma taxa de tentativas por segundo
        val attemptsPerSecond = BigInteger.valueOf(10_000_000_000) // 1 bilhão de tentativas por segundo

        // Calcula o tempo em segundos
        val secondsToCrack = totalCombinations.divide(attemptsPerSecond)

        // Convertendo o tempo em uma forma mais legível
        val timeToCrack = formatTime(secondsToCrack)

        return timeToCrack
    }

    private fun formatTime(seconds: BigInteger): String {
        val secondsInMinute = BigInteger.valueOf(60)
        val secondsInHour = secondsInMinute.multiply(BigInteger.valueOf(60))
        val secondsInDay = secondsInHour.multiply(BigInteger.valueOf(24))
        val secondsInYear = secondsInDay.multiply(BigInteger.valueOf(365))
        val secondsInCentury = secondsInYear.multiply(BigInteger.valueOf(100))
        val secondsInMillennium = secondsInYear.multiply(BigInteger.valueOf(1000))

        return when {
            seconds < secondsInMinute -> "$seconds segundos"
            seconds < secondsInHour -> "${seconds.divide(secondsInMinute)} minutos"
            seconds < secondsInDay -> "${seconds.divide(secondsInHour)} horas"
            seconds < secondsInYear -> "${seconds.divide(secondsInDay)} dias"
            seconds < secondsInCentury -> "${seconds.divide(secondsInYear)} anos"
            seconds < secondsInMillennium -> "${seconds.divide(secondsInCentury)} séculos"
            else -> "${seconds.divide(secondsInMillennium)} milênios"
        }
    }
}
