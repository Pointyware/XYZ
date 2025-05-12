package org.pointyware.xyz.site.home

import kotlinx.html.ScriptType
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.footer
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.p
import kotlinx.html.script
import kotlinx.html.style
import org.pointyware.xyz.site.dsl.DirectoryScope
import org.pointyware.xyz.site.dsl.css

/**
 * Renders the home page of the site.
 */
fun DirectoryScope.homePage() {
    val centerClass = "center"
    val privacyPolicyLinkClass = "privacy-policy-link"
    val termsOfServiceLinkClass = "terms-of-service-link"
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
                    centerClass.cls {
                        this.textAlign = "center"
                    }
                    privacyPolicyLinkClass.cls {
                    }
                    termsOfServiceLinkClass.cls {
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
                classes = centerClass
            ) {
                h1 {
                    text("Welcome to the XYZ Site")
                }
            }

            footer {
                p {
                    text("© 2025 Pointyware LLC")
                }
                p {
                    text("This site is powered by Kotlin and kotlinx.html.")
                }
                p {
                    a(
                        href = "./privacy-policy",
                        classes = privacyPolicyLinkClass
                    ) {
                        text("Privacy Policy")
                    }
                }
                p {
                    a(
                        href = "./terms-of-service",
                        classes = termsOfServiceLinkClass
                    ) {
                        text("Terms of Service")
                    }
                }
            }
        }
    }
}
