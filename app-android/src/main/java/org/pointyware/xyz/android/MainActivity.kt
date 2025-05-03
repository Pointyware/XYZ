/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.compose.AndroidFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.xyz.shared.di.AppDependencies

class MainActivity : ComponentActivity() {
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
                    val mMap = googleMap

                    // Add a marker in Sydney and move the camera
                    val sydney = LatLng(-34.0, 151.0)
                    mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                }
            }
        }
    }
}
