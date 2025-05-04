

package org.pointyware.xyz.feature.login.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import org.pointyware.xyz.core.entities.profile.Role
import org.pointyware.xyz.core.ui.design.XyzTheme
import org.pointyware.xyz.core.ui.di.EmptyTestUiDependencies
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 *
 */
@OptIn(ExperimentalTestApi::class)
class RoleSelectionViewTest {

    @BeforeTest
    fun setUp() {
    }

    @Test
    fun `RoleSelectionView displays welcome message`() = runComposeUiTest {
        // Given, the RoleSelectionView is displayed
        var selectedRole by mutableStateOf<Role?>(null)
        setContent {
            XyzTheme(
                uiDependencies = EmptyTestUiDependencies()
            ) {
                RoleSelectionView(
                    onConfirm = { selectedRole = it
                    println("selectedRole: $selectedRole")}
                )
            }
        }

        // Then, the welcome message is displayed
        onNodeWithText("Welcome!").assertExists()
    }

    @Test
    fun `wait for user confirmation when selecting driver role`() = runComposeUiTest {
        // Given, the RoleSelectionView is displayed
        var selectedRole by mutableStateOf<Role?>(null)
        setContent {
            XyzTheme(
                uiDependencies = EmptyTestUiDependencies()
            ) {
                RoleSelectionView(
                    onConfirm = { selectedRole = it
                        println("selectedRole: $selectedRole")}
                )
            }
        }

        // When the user selects the Driver role
        onNodeWithText("Driver")
            .assertExists()
            .performClick()
        // Then onConfirm is not yet called
        assertEquals(null, selectedRole, "Role should be null")
        // When the user confirms the selection
        onNodeWithText("Confirm")
            .assertExists()
            .performClick()
        // Then onConfirm is called with the Driver role
        assertEquals(Role.Driver, selectedRole, "Role should be Driver")
    }

    @Test
    fun `wait for user confirmation when selecting rider role`() = runComposeUiTest {
        // Given, the RoleSelectionView is displayed
        var selectedRole by mutableStateOf<Role?>(null)
        setContent {
            XyzTheme(
                uiDependencies = EmptyTestUiDependencies()
            ) {
                RoleSelectionView(
                    onConfirm = { selectedRole = it
                        println("selectedRole: $selectedRole")}
                )
            }
        }

        // When the user selects the Rider role
        onNodeWithText("Rider")
            .assertExists()
            .performClick()
        // Then onConfirm is not yet called
        assertEquals(null, selectedRole, "Role should be null")
        // When the user confirms the selection
        onNodeWithText("Confirm")
            .assertExists()
            .performClick()
        // Then onConfirm is called with the Rider role
        assertEquals(Role.Rider, selectedRole, "Role should be Rider")
    }
}
