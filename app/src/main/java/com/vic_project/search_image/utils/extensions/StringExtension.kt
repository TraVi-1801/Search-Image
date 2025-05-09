package com.vic_project.search_image.utils.extensions

object StringExtension {
    private const val holderForNull = "N/A"
    private const val otherForNull = "-"

    fun String?.orNullWithHolder(): String {
        this ?: return holderForNull
        return this
    }

    fun Any?.orNullWithOther(): String {
        this ?: return otherForNull
        return this.toString()
    }

    fun Double?.toMoney(): String {
        if (this == null) return "-"
        val formatted = when {
            this >= 1_000_000_000 -> this / 1_000_000_000 to "tỷ"
            this >= 1_000_000 -> this / 1_000_000 to "triệu"
            this >= 1_000 -> this / 1_000 to "nghìn"
            else -> return this.toString()
        }

        val value = formatted.first
        val unit = formatted.second

        return if (value % 1 == 0.0) {
            "${value.toInt()} $unit"
        } else {
            String.format("%.1f %s", value, unit)
        }
    }

    fun String?.toNoHtml(): String {
        this ?: return holderForNull
        return this.replace(Regex("<[^>]*>"), "")
            .replace("&nbsp;", " ")
            .replace("\\s+".toRegex(), " ")
            .trim()
    }

    fun randomUUID(): String {
        return java.util.UUID.randomUUID().toString()
    }
}