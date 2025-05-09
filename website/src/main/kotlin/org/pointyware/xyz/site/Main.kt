package org.pointyware.xyz.site

import org.pointyware.xyz.site.dsl.ProgramOutput.PrintOutput
import org.pointyware.xyz.site.dsl.site
import org.pointyware.xyz.site.home.homePage
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
    buildSite(inputs.output)
}
