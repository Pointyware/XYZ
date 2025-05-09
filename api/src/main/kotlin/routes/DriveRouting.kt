/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.api.routes

import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import org.koin.mp.KoinPlatform.getKoin

/**
 * Routes drive endpoint requests to the appropriate controller.
 */
fun Routing.drive() {
    val koin = getKoin()
    post("/drive/start") {}
    put("/drive/status") {}
    post("/drive/accept") {}
    post("/drive/stop") {}
}
