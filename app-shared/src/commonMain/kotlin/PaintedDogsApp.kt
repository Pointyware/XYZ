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
import navigation.loginRouting
import org.pointyware.xyz.core.navigation.LocationRoot
import org.pointyware.xyz.core.ui.design.XyzTheme
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

    XyzTheme(
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
                        Text(currentLocation.value.toString() ?: "XYZ")
                    },
                    actions = {
                    },
                )
            },
            floatingActionButton = {
            },
            floatingActionButtonPosition = FabPosition.End,
//            bottomBar = {
//                NavigationBar(
////                    containerColor =
////                    contentColor =
//                ) {
//                    IconButton(
//                        modifier = Modifier.weight(1f),
//                        onClick = { navController.navigateTo(route("funds")) }
//                    ) {
//                        Icon(Icons.Default.Build, contentDescription = "Create Fund")
//                    }
//                    IconButton(
//                        modifier = Modifier.weight(1f),
//                        onClick = { navController.navigateTo(route("rides")) }
//                    ) {
//                        Icon(Icons.Default.Call, contentDescription = "Rides")
//                    }
//                    IconButton(
//                        modifier = Modifier.weight(1f),
//                        onClick = { navController.navigateTo(route("social")) }
//                    ) {
//                        Icon(Icons.Default.Person, contentDescription = "Social")
//                    }
//                }
//            }
        ) { paddingValues ->
            LocationRoot(
                navController = navController,
                modifier = Modifier.padding(paddingValues),
            ) {
                loginRouting()
            }
        }
    }
}

fun logout() {
    println("Logging out")
    println("Logged out")
}
