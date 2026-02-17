package main

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.util.logging.Level.INFO
import java.util.logging.Logger

interface IPasswordService {
    fun generateAndCopy(lengthText: String, type: PasswordType): Result<Pair<String, String>>
}

class PasswordService(private val strengthEstimator: PasswordStrength = PasswordStrength()) : IPasswordService {
    private val logger = Logger.getLogger(this.javaClass.name)

    override fun generateAndCopy(lengthText: String, type: PasswordType): Result<Pair<String, String>> {
        val length = lengthText.toIntOrNull()
            ?: return Result.failure(IllegalArgumentException("Insira um número válido."))

        if (length <= 0) return Result.failure(IllegalArgumentException("Tamanho deve ser > 0."))
        if (length > 1000000) return Result.failure(IllegalArgumentException("Máximo 1.000.000."))

        return try {
            val password = type.generate(length)
            copyToClipboard(password)

            val strength = strengthEstimator.estimate(password, type)
            logger.log(INFO, "Senha ($type) gerada. Força: $strength")

            Result.success(password to strength)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun copyToClipboard(password: String) {
        val selection = StringSelection(password)
        Toolkit.getDefaultToolkit().systemClipboard.setContents(selection, selection)
    }
}
