/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.shared

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import navigation.loginRoute
import navigation.loginRouting
import org.pointyware.xyz.core.navigation.LocationRoot
import org.pointyware.xyz.core.navigation.NamedLocation
import org.pointyware.xyz.core.ui.design.XyzTheme
import org.pointyware.xyz.drive.navigation.driveRouting
import org.pointyware.xyz.feature.login.navigation.profileRouting
import org.pointyware.xyz.feature.login.navigation.roleSelectionRoute
import org.pointyware.xyz.feature.login.navigation.userProfileRoute
import org.pointyware.xyz.feature.ride.navigation.rideRouting
import org.pointyware.xyz.shared.di.AppDependencies

/**
 * The main entry point for the XYZ app.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XyzApp(
    dependencies: AppDependencies,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
) {
    val navController = dependencies.getNavigationDependencies().getNavController()

    val titleMap = mapOf(
        loginRoute to "Login",
        roleSelectionRoute to "New Account",
        userProfileRoute to "Profile",
    )
    XyzTheme(
        uiDependencies = dependencies.getUiDependencies(),
        isDark = isDarkTheme
    ) {
        val currentLocation = navController.currentLocation.collectAsState()
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
                        val stack = navController.backList.collectAsState()
                        if (stack.value.isNotEmpty()) {
                            IconButton(onClick = { navController.goBack() }) {
                                Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Profile")
                            }
                        }
                    },
                    title = {
                        val location = currentLocation.value
                        val name = when(location) {
                            is NamedLocation -> {
                                location.name
                            }
                            else -> {
                                titleMap[location] ?: location.toString()
                            }
                        }
                        Text(text = name)
                    },
                    actions = {
                    },
                )
            },
            floatingActionButton = {
            },
            floatingActionButtonPosition = FabPosition.End,
        ) { paddingValues ->
            LocationRoot(
                navController = navController,
                modifier = Modifier.padding(paddingValues),
            ) {
                loginRouting(dependencies.getLoginDependencies(), dependencies.getNavigationDependencies())
                profileRouting(dependencies.getProfileDependencies(), dependencies.getNavigationDependencies())

                driveRouting(dependencies.getDriveDependencies(), dependencies.getNavigationDependencies())
                rideRouting(dependencies.getRideDependencies(), dependencies.getNavigationDependencies())
            }
        }
    }
}
