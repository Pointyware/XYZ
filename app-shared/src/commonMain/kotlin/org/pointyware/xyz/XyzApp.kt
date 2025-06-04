/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import navigation.loginRoute
import navigation.loginRouting
import org.pointyware.xyz.ui.design.XyzTheme
import org.pointyware.xyz.drive.navigation.driveRouting
import org.pointyware.xyz.feature.login.navigation.profileRouting
import org.pointyware.xyz.feature.ride.navigation.rideRouting
import org.pointyware.xyz.navigation.payments

/**
 * The main entry point for the XYZ app.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XyzApp(
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    XyzTheme(
        isDark = isDarkTheme
    ) {
        val currentEntry = navController.currentBackStackEntryAsState()
        Scaffold(
            modifier = modifier,
            topBar = {
                CenterAlignedTopAppBar(
//                    colors = TopAppBarColors(
//                        containerColor =
//                        navigationIconContentColor =
//                        titleContentColor =
//                        actionIconContentColor =
//                        scrolledContainerColor =
//                    ),
                    navigationIcon = {
                        val stack = navController.currentBackStack.collectAsState()
                        if (stack.value.isNotEmpty()) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Profile")
                            }
                        }
                    },
                    title = {
                        val location = currentEntry.value
                        Text(text = location?.destination?.route ?: "")
                    },
                    actions = {
                    },
                )
            },
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = loginRoute,
                modifier = Modifier.padding(paddingValues),
            ) {
                loginRouting(navController)
                profileRouting(navController)

                driveRouting(navController)
                rideRouting(navController)

                payments(navController)
            }
        }
    }
}
