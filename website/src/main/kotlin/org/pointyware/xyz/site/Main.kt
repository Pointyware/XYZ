package org.pointyware.xyz.site

import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.write
import kotlinx.html.html
import java.io.File
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

    inputs.output.site {
        branch("privacy-policy") {

        }

        branch("terms-of-service") {

        }
    }
    inputs.output.bufferedWriter().use {
        it.write(document = htmlPage)
    }
}

data class ProgramInputs(
    val output: ProgramOutput
)

enum class CommandOption(
    val longName: String,
    val shortName: String,
    val description: String,
    val defaultValue: String = "",
    val onMatch: (Iterator<String>, ProgramInputs)->ProgramInputs
) {
    Output(
        longName = "out",
        shortName = "o",
        description = "",
        defaultValue = "",
        onMatch = { args, inputs ->
            if (args.hasNext()) {
                inputs.copy(output = ProgramOutput.FileOutput(File(args.next())))
            } else {
                throw IllegalArgumentException("Missing argument for ${Output.longName}")
            }
        }
    )
}

private fun Iterator<String>.consumeArgs(inputs: ProgramInputs): ProgramInputs {
    if (hasNext()) {
        val arg = next()
        when {
            arg.startsWith("--") -> {
                val longOption = arg.substring(2)
                CommandOption.entries.forEach {
                    if (it.longName == longOption) {

                        return@forEach
                    }
                }
            }
            arg.startsWith("-") -> {
                val shortOption = arg.substring(1)
                CommandOption.entries.forEach {
                    if (it.shortName == shortOption) {

                        return@forEach
                    }
                }
            }
        }
    } else {
        inputs
    }
}
