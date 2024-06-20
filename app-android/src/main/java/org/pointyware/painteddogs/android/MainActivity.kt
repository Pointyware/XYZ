package org.pointyware.xyz.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.remember
import org.pointyware.xyz.shared.XyzApp
import org.pointyware.xyz.shared.di.getDependencies

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val appDependencies = remember { getDependencies() }
            XyzApp(
                dependencies = appDependencies,
                isDarkTheme = isSystemInDarkTheme()
            )
        }
    }
}
