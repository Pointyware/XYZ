package org.pointyware.xyz.site

import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.write
import kotlinx.html.html
import java.io.OutputStream

/**
 * How to use:
 * -out <filepath> | the default is System.out
 *
 */
fun main(vararg args: String) {
    // Declare Program defaults explicitly for parity with documentation 👍
    var inputs = ProgramInputs(
        output = System.out
    )

    // Process Arguments
    inputs = args.iterator().consumeArgs(inputs)

    // Render Single Document For Now - Developing Site DSL ATM
    val htmlPage = createHTMLDocument().html {

    }

    inputs.output.bufferedWriter().use {
        it.write(document = htmlPage)
    }
}

data class ProgramInputs(
    val output: OutputStream
)

data class CommandOption(
    val name: String,
    val shortName: String,
    val description: String,
    val defaultValue: String = ""
) {
    companion object {
        val Output = CommandOption(
            name = "out",
            shortName = "o",
            description = "",
            defaultValue = "",
        )
    }
}

private fun Iterator<String>.consumeArgs(inputs: ProgramInputs): ProgramInputs {
    if (hasNext()) {
        val arg = next()
        when {
            arg.startsWith("--") -> {
                val longOption = arg.substring(2)
                // TODO: match against names
                CommandOption.Output.name
            }
            arg.startsWith("-") -> {
                val shortOption = arg.substring(1)
                CommandOption.Output.shortName
            }
        }
    } else {
        inputs
    }
}
