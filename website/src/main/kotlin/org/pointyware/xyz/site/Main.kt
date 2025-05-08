package org.pointyware.xyz.site

import kotlinx.html.ScriptType
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.p
import kotlinx.html.script
import kotlinx.html.style
import kotlinx.html.unsafe
import org.pointyware.xyz.site.dsl.ProgramOutput.PrintOutput
import org.pointyware.xyz.site.dsl.site
import org.pointyware.xyz.site.utils.ProgramInputs
import org.pointyware.xyz.site.utils.consumeArgs

/**
 * How to use:
 * -out <filepath> | the default is System.out with a virtual root at "/"
 *
 */
fun main(vararg args: String) {
    // Declare Program defaults explicitly for parity with documentation 👍
    var inputs = ProgramInputs(
        output = PrintOutput("/", System.out)
    )

    // Process Arguments
    inputs = args.iterator().consumeArgs(inputs)

    // Render Site
    site(inputs.output) {
        branch("privacy-policy") {
            index(resourceFile = "docs/privacy-policy.html")
        }
        branch("") {
            index {
                // Consider: https://github.com/allangomes/kotlinwind.css/tree/dev in place of kotlinx.css
                head {
                    style {
                        unsafe {
                            raw("""
                                body {
                                    background-color: #f0f0f0;
                                    font-family: Arial, sans-serif;
                                    margin: 0;
                                    padding: 20px;
                                }
                                h1 {
                                    color: #333;
                                }
                                p {
                                    color: #666;
                                }
                            """.trimIndent())
                        }
                    }
                    script(
                        type = ScriptType.textJavaScript,
                    ) {
                        unsafe {
                            raw("""
                                console.log("Hello, World!");
                            """.trimIndent())
                        }
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

        branch("terms-of-service") {
            index(resourceFile = "docs/terms-of-service.html")
        }
    }
}
