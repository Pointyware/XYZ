package org.pointyware.xyz.site

/**
 *
 */
fun main(vararg args: String) {

    File("output").outputStream().use {
        appendHtml().html {
            head(

            ) {

            }
            body(

            ) {

            }
        }
    }
}
