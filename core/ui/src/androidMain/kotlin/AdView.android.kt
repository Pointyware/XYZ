/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

actual fun testAdViewState(): AdViewState =
    AdViewState("ca-app-pub-3940256099942544/9214589741")

/**
 *
 */
@SuppressLint("MissingPermission")
@Composable
actual fun AdView(
    state: AdViewState,
    modifier: Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = {
            AdView(it).apply {
                adUnitId = state.unit
                setAdSize(AdSize.BANNER)

                val request = AdRequest.Builder().build()
                loadAd(request)
            }
        },
        update = {

        },
        onReset = {

        },
        onRelease = {

        }
    )
}
