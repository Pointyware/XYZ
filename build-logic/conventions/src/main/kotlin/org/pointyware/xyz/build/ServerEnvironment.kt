package org.pointyware.xyz.build

import org.gradle.api.Project

/**
 *  Defines all known interpretations of server environments and offers a final disambiguation.
 *  The defined enumerations are the canonical interpretations and document the common names
 *  used for each canonical concept.
 */
enum class ServerEnvironment {
    /**
     * This is the environment running directly on a developer's machine, completely isolated from
     * all other developers.
     *
     * Sometimes referred to as "dev" since it is the developer's development environment, but
     * "local" is used here to differentiate from a shared development environment, which we call
     * [Shared].
     */
    Local,

    /**
     * This is the environment where developers push their code for testing by other
     * developers or testers. It is usually a shared environment that is not
     * intended for production use.
     *
     * Sometimes called "local" since these environments are usually on-premises to minimize cost
     * and latency. Also known as "development" by teams who use a shared development branch to
     * collaborate between merges to the main branch.
     */
    Shared,

    /**
     * This is the environment where the code is tested before it is pushed to
     * production. It is usually a staging environment that closely resembles
     * the production environment.
     *
     * Sometimes referred to as "Dev" for those who call [Shared] "local". Also known as "Test" or "QA".
     */
    Staging,

    /**
     * This is the environment where the code is deployed for production use.
     * It is usually a live environment that is accessible to end users.
     */
    Production;
}

fun Project.local(action: ()->Unit) {
    if (status == ServerEnvironment.Local) {
        action()
    }
}
fun Project.shared(action: ()->Unit) {
    if (status == ServerEnvironment.Shared) {
        action()
    }
}
fun Project.staging(action: ()->Unit) {
    if (status == ServerEnvironment.Staging) {
        action()
    }
}
fun Project.production(action: ()->Unit) {
    if (status == ServerEnvironment.Production) {
        action()
    }
}
