package org.pointyware.xyz.site.dsl

import kotlinx.html.STYLE
import kotlinx.html.Unsafe
import kotlinx.html.unsafe

/**
 * Configures a style tag for cascading stylesheets and then
 */
fun STYLE.css(
    block: CssScope.() -> Unit,
) {
    unsafe {
        CssScope(this).block()
    }
}

/**
 * Defines the root scope of our Css DSL.
 */
class CssScope(
    private val output: Unsafe
) {
    fun body(styles: StyleScope.() -> Unit) {
        tag("body", styles)
    }

    fun p(styles: StyleScope.() -> Unit) {
        tag("p", styles)
    }

    fun h1(styles: StyleScope.() -> Unit) {
        tag("h1", styles)
    }

    fun tag(name: String, styles: StyleScope.() -> Unit) {
        val styleScope = StyleScope()
        styleScope.styles()
        output.raw("$name {")
        output.raw(styleScope.styleBlock())
        output.raw("}")
    }
}

class StyleScope() {
    var color: String? = null
    var backgroundColor: String? = null
    var fontSize: String? = null
    var fontFamily: String? = null
    var margin: String? = null
    var padding: String? = null
    var border: String? = null
    var display: String? = null

    fun styleBlock(): String {
        return listOfNotNull(
            color?.let { "color: $it;" },
            backgroundColor?.let { "background-color: $it;" },
            fontSize?.let { "font-size: $it;" },
            fontFamily?.let { "font-family: $it;" },
            margin?.let { "margin: $it;" },
            padding?.let { "padding: $it;" },
            border?.let { "border: $it;" },
            display?.let { "display: $it;" }
        ).joinToString("\n")
    }
}
