package org.pointyware.xyz.site.home

import kotlinx.html.ScriptType
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.p
import kotlinx.html.script
import kotlinx.html.style
import kotlinx.html.unsafe
import org.pointyware.xyz.site.dsl.DirectoryScope
import org.pointyware.xyz.site.dsl.css

/**
 * Renders the home page of the site.
 */
fun DirectoryScope.homePage() {
    index {
        // Consider: https://github.com/allangomes/kotlinwind.css/tree/dev in place of kotlinx.css
        head {
            style {
                css {
                    body {
                        backgroundColor = "#f0f0f0"
                        fontFamily = "Arial, sans-serif"
                        margin = "0"
                        padding = "20px"
                    }
                    h1 {
                        color = "#333"
                    }
                    p {
                        color = "#666"
                    }
                }
            }
            script(
                type = ScriptType.textJavaScript,
            ) {
//                unsafe {
//                    raw("""
//                                console.log("Hello, World!");
//                            """.trimIndent())
//                }
            }
        }
        body {
            div(
                classes = ""
            ) {

            }
            p(
                classes = ""
            ) {

            }
        }
    }
}
