/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.painteddogs.android.ads

import android.content.Context
import com.google.android.gms.ads.MobileAds
import org.pointyware.xyz.core.ui.ads.AdsController

class AdMobController(
    private val context: Context
): AdsController {
    override suspend fun onAppStart() {
        MobileAds.initialize(context)
    }
}
