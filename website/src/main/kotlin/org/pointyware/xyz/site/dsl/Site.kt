package org.pointyware.xyz.site.dsl

import kotlinx.html.HTML
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.write
import kotlinx.html.html
import org.pointyware.xyz.site.utils.sanitizeHtmlFileName
import java.io.File

/**
 * The main unit of our DSL API.
 */
interface BranchScope {
    val directory: File // TODO: change to Output Object which allows for outputting to arbitrary streams

    fun branch(
        segment: String,
        block: BranchScope.() -> Unit
    ) {
        val branchDirectory = File(directory, segment)
        if (!branchDirectory.exists()) {
            branchDirectory.mkdirs()
        }
        DirectoryScope(branchDirectory).block()
    }

    /**
     *
     */
    fun index(
        block: HTML.() -> Unit
    ) = page(name = "index.html", block = block)

    /**
     *
     */
    fun error(
        block: HTML.() -> Unit
    ) = page(name = "error.html", block = block)

    /**
     *
     */
    fun page(
        name: String,
        block: HTML.() -> Unit
    ) {
        val sanitizedFileName = sanitizeHtmlFileName(name)
        val pageFile = File(directory, sanitizedFileName)
        val doc = createHTMLDocument().html(block = block)
        pageFile.outputStream().bufferedWriter().use {
            it.write(document = doc)
        }
    }
}

/**
 *
 */
data class DirectoryScope(
    override val directory: File
): BranchScope

/**
 * Returns the root scope for our custom DSL API. The Kotlin HTML DSL allows us to build a single
 * HTML page, and our DSL wraps this API to allow us to build a hierarchy of endpoints.
 *
 * When the output is a directory, each terminal endpoint is printed out to a separate index.html
 * file along some directory path described by the hierarchy. When the output is System.out, since
 * all the pages are printed out to the same stream, they are delimited by descriptors that include
 * the path to the endpoint and the page name.
 *
 * What we want to achieve is the ability to determine the root of a folder hierarchy, and then
 * build up a hierarchy of endpoints, where each
 */
fun site(root: File, block: DirectoryScope.() -> Unit) {
    val site = DirectoryScope(root)
    site.block()
}
