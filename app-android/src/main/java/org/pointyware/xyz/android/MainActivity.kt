/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import androidx.fragment.compose.AndroidFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.shared.di.AppDependencies

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val koin = getKoin()
        val appDependencies = koin.get<AppDependencies>()

        setContent {
//            XyzApp(
//                dependencies = appDependencies,
//                isDarkTheme = isSystemInDarkTheme()
//            )

            AndroidFragment<SupportMapFragment> { mapFragment ->
                mapFragment.getMapAsync { googleMap ->
                    // Add a marker in the middle of the Atlantic Ocean
                    val sydney = LatLng(34.4069, -40.8707)
                    googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Atlantic Ocean"))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                }
            }
        }
    }
}
