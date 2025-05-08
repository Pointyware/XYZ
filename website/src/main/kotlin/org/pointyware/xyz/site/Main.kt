package org.pointyware.xyz.site

import kotlinx.html.body
import kotlinx.html.head
import org.pointyware.xyz.site.ProgramOutput.PrintOutput
import org.pointyware.xyz.site.dsl.site
import java.io.File

/**
 * How to use:
 * -out <filepath> | the default is System.out
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
            index {
                head {

                }
                body {

                }
            }
        }

        branch("terms-of-service") {
            index {
                head {

                }
                body {

                }
            }
        }
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
    var latestInputs = inputs
    var unrecognizedArgs = mutableListOf<String>()
    while (hasNext()) { // Continue to consume command options until all args are consumed
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
            else -> {
                unrecognizedArgs += arg
            }
        }
    }
    if (unrecognizedArgs.isNotEmpty()) {
        println("Unrecognized arguments: ${unrecognizedArgs.joinToString(", ")}")
    }
    return latestInputs
}
