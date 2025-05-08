package org.pointyware.xyz.site

import java.io.File
import java.io.PrintStream

/**
 * Defines the available program outputs.
 * To control with program arguments, see [CommandOption.Output].
 */
sealed interface ProgramOutput {
    fun branch(segment: String): ProgramOutput
    data class FileOutput(val file: File): ProgramOutput {
        override fun branch(segment: String): ProgramOutput {
            val branchFile = File(file, segment)
            if (!branchFile.exists()) {
                branchFile.mkdirs()
            }
            return FileOutput(branchFile)
        }
    }
    data class PrintOutput(val path: String, val stream: PrintStream): ProgramOutput {
        override fun branch(segment: String): ProgramOutput {
            return PrintOutput(path = "$path/$segment", stream = stream)
        }
    }
}
