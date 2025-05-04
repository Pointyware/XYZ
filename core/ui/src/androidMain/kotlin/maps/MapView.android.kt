package org.pointyware.xyz.core.ui.maps

import androidx.compose.runtime.Composable
import androidx.fragment.compose.AndroidFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@Composable
actual fun MapView() {
    AndroidFragment<SupportMapFragment> { mapFragment ->
        mapFragment.getMapAsync { googleMap ->
            // Add a marker at the turn-off point of I-35 - 36°06'56.0"N 97°20'42.1"W
            val sydney = LatLng(36.11556, -97.34503)
            googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Atlantic Ocean"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 11f))
        }
    }
}
