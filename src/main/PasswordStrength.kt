package main

import java.math.BigInteger
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class PasswordStrength {

    fun estimate(password: String, type: PasswordType): String {
        if (password.isEmpty()) return "0"

        // Agora usamos o tamanho real da lista definida no Generator
        val poolSize = type.getPoolSize()
        val length = password.length

        val combinations = BigInteger.valueOf(poolSize.toLong()).pow(length)

        // 100 Bilhões de tentativas/seg
        val attemptsPerSecond = BigInteger("100000000000")
        val secondsToCrack = combinations.divide(attemptsPerSecond)

        return formatTime(secondsToCrack)
    }

    private fun formatTime(seconds: BigInteger): String {
        val minute = BigInteger.valueOf(60)
        val hour = minute * BigInteger.valueOf(60)
        val day = hour * BigInteger.valueOf(24)
        val year = day * BigInteger.valueOf(365)

        return when {
            seconds < BigInteger.ONE -> "Instantâneo"
            seconds < minute -> "$seconds segundos"
            seconds < hour -> "${seconds / minute} minutos"
            seconds < day -> "${seconds / hour} horas"
            seconds < year -> "${seconds / day} dias"
            else -> {
                val years = seconds / year
                if (years < BigInteger.valueOf(1_000_000_000)) {
                    "${formatNumber(years)} anos"
                } else {
                    formatScientific(years) + " anos"
                }
            }
        }
    }

    private fun formatNumber(number: BigInteger): String {
        val localeBR = Locale.forLanguageTag("pt-BR")
        val symbols = DecimalFormatSymbols(localeBR)
        symbols.groupingSeparator = '.'
        return DecimalFormat("#,###", symbols).format(number)
    }

    private fun formatScientific(number: BigInteger): String {
        val s = number.toString()
        return "${s[0]}.${s.substring(1, 4)}e+${s.length - 1}"
    }
}
