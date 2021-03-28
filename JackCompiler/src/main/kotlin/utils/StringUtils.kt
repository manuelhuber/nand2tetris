package utils

fun String.isCode(): Boolean {
    return this.isNotEmpty() && !this.startsWith("//")
}

fun removeComments(code: List<String>): List<String> {
    return code.filter(String::isCode).map { line ->
        val indexOf = line.indexOf("//")
        if (indexOf == -1) {
            return@map line
        }
        line.substring(0, indexOf).trim()
    }
}
