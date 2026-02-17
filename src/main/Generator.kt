package main

import java.security.SecureRandom

object Generator {
    internal val letters = (('a'..'z') + ('A'..'Z')).toList()
    internal val numbers = ('0'..'9').toList()
    internal val accents = "àáâãäèéêëìíîïòóôõöùúûüýÿÁÀÂÃÄÉÈÊËÍÌÎÏÓÒÔÖÕÚÙÛÜÝ".toList()
    internal val specials = "!@²#%¨\"&*()-_+/.\$£³¢¬?<> ,;:°çÇ~^{}ª[]º'|§´`\\".toList()

    internal val unicodeChars by lazy {
        CharRange(Char.MIN_VALUE, Char.MAX_VALUE).filterNot { char ->
            val original = char.toString()
            val decoded = original.toByteArray(Charsets.UTF_8).toString(Charsets.UTF_8)

            letters.contains(char) ||
                    numbers.contains(char) ||
                    specials.contains(char) ||
                    accents.contains(char) ||
                    char.isISOControl() ||
                    original != decoded ||
                    char.isWhitespace() ||
                    !char.isDefined() ||
                    char.isIdentifierIgnorable()
        }.toList()
    }

    private val random = SecureRandom()

    fun generate(length: Int, charPool: List<Char>): String {
        if (length <= 0 || charPool.isEmpty()) return ""
        return buildString(length) {
            repeat(length) {
                append(charPool[random.nextInt(charPool.size)])
            }
        }
    }
}

enum class PasswordType(val description: String) {
    LETTERS("Letras") {
        override fun getChars() = Generator.letters
    },
    NUMBERS("Números") {
        override fun getChars() = Generator.numbers
    },
    LETTERS_ACCENTS("Letras e Acentos") {
        override fun getChars() = Generator.letters + Generator.accents
    },
    LETTERS_NUMBERS("Letras e Números") {
        override fun getChars() = Generator.letters + Generator.numbers
    },
    LETTERS_NUMBERS_ACCENTS("Letras, Números e Acentos") {
        override fun getChars() = Generator.letters + Generator.numbers + Generator.accents
    },
    LETTERS_NUMBERS_SPECIALS("Letras, Números e Especiais") {
        override fun getChars() = Generator.letters + Generator.numbers + Generator.specials
    },
    LETTERS_NUMBERS_ACCENTS_SPECIALS("Letras, Números, Acentos e Especiais") {
        override fun getChars() = Generator.letters + Generator.numbers + Generator.accents + Generator.specials
    },
    ALL_MIXED("Tudo Misturado (Incluindo Unicode)") {
        override fun getChars() = Generator.letters + Generator.numbers + Generator.accents + Generator.specials + Generator.unicodeChars
    };

    abstract fun getChars(): List<Char>

    fun generate(length: Int): String {
        return Generator.generate(length, getChars())
    }

    fun getPoolSize(): Int = getChars().size
}
