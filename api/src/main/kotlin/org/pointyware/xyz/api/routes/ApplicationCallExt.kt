/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import io.ktor.server.response.respondText


/**
 * Passes the result along as a 200 OK response if successful, or a 500 Internal Server Error if not.
 */
suspend inline fun <reified T : Any> ApplicationCall.respondResult(result: Result<T>) {
    result
        .onSuccess { respond(it) }
        .onFailure { error -> respondText(error.message?.let { error.toString() + it } ?: "Unknown error", status = HttpStatusCode.InternalServerError) }
    respondText("Unreachable", status = HttpStatusCode.InternalServerError)
}
