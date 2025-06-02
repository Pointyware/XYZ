/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.android.ads

import android.content.Context
import com.google.android.gms.ads.MobileAds
import org.pointyware.xyz.ui.ads.AdsController

class AdMobController(
    private val context: Context
): AdsController {
    override suspend fun onAppStart() {
        // TODO: when moving to EU, do not initialize ads until after consent
        MobileAds.initialize(context)
    }
}
