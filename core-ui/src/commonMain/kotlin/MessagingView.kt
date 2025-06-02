/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.pointyware.xyz.core.viewmodels.MessageUiState
import org.pointyware.xyz.core.viewmodels.MessagingUiState

interface MessagingResources {
    val userMessageColor: Color
    val otherUserMessageColor: Color
}
internal data class MessagingResourcesImpl(
    override val userMessageColor: Color,
    override val otherUserMessageColor: Color
): MessagingResources
fun MessagingResources(
    userMessageColor: Color = Color.Blue,
    otherUserMessageColor: Color = Color.Green
): MessagingResources = MessagingResourcesImpl(userMessageColor, otherUserMessageColor)
val messagingResources = compositionLocalOf<MessagingResources> { throw IllegalStateException("No MessagingResources provided") }

/**
 * Displays messages between a rider and driver.
 */
@Composable
fun MessagingView(
    state: MessagingUiState,
    modifier: Modifier = Modifier,
    onMessageChange: (String)->Unit,
    onSendMessage: ()->Unit,
) {
    Column(
        modifier = modifier
    ) {
        BriefProfileRowItem(
            state = state.otherUser,
            modifier = Modifier
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            items(state.messages) { message ->
                MessageRowItem(
                    state = message,
                    modifier = Modifier
                )
            }
        }
        MessageInputRowItem(
            input = state.newMessage,
            modifier = Modifier,
            onInputChange = onMessageChange,
            onSend = onSendMessage
        )
    }
}

@Composable
fun MessageRowItem(
    state: MessageUiState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(
                color =
                if (state.isFromUser) { messagingResources.current.userMessageColor }
                else { messagingResources.current.otherUserMessageColor }
            )
    ) {
        Text(text = state.message)
    }
}

@Composable
fun MessageInputRowItem(
    input: String,
    modifier: Modifier = Modifier,
    onInputChange: (String)->Unit,
    onSend: ()->Unit,
) {
    Row(
        modifier = modifier
    ) {
        TextField(
            value = input,
            modifier = Modifier.weight(1f),
            onValueChange = onInputChange
        )
        Button(onClick = onSend) {
            Text(text = "Send")
        }
    }
}
