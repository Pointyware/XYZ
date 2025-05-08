package org.pointyware.xyz.site.utils

import org.pointyware.xyz.site.dsl.ProgramOutput

/**
 * Defines the available parameters for controlling program behavior.
 * This model exists primarily as a helper object for processing arguments progressively.
 * @see CommandOption
 */
data class ProgramInputs(
    val output: ProgramOutput
)
