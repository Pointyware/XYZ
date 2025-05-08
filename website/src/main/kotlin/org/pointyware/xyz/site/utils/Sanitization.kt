package org.pointyware.xyz.site.utils

/**
 * Transforms the input to conform with our rules for HTML pages.
 */
fun sanitizeHtmlFileName(input: String): String =
    input.replace("\s".toRegex(), "_").let {
        if (it.endsWith(".html")) {
            it
        } else {
            "$it.html"
        }
    }
