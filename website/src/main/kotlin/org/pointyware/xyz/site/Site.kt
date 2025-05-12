package org.pointyware.xyz.site

import org.pointyware.xyz.site.dsl.ProgramOutput
import org.pointyware.xyz.site.dsl.site
import org.pointyware.xyz.site.home.homePage

/**
 * The main entry point for the site generation.
 */
fun buildSite(
    output: ProgramOutput
) {
    // no setup needed, just call the site function
    site(output) {
        homePage()
        branch("privacy-policy") {
            index(resourceFile = "docs/privacy-policy.html")
        }

        branch("terms-of-service") {
            index(resourceFile = "docs/terms-of-service.html")
        }
        file("CNAME", "CNAME")
    }
}
