package org.pointyware.xyz.site.utils

/**
 * Transforms the input to conform with our rules for HTML pages.
 *
 * 1. An html file must end with .html
 * 2. All whitespaces are replaced with underscores
 */
fun sanitizeHtmlFileName(input: String): String =
    input.replace("\\s".toRegex(), "_").let {
        if (it.endsWith(".html")) {
            it
        } else {
            "$it.html"
        }
    }
