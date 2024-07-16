/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.core.viewmodels

/**
 *
 */
data class MessagingUiState(
    val messages: List<MessageUiState> = emptyList(),
    val newMessage: String = "",
    val isSending: Boolean = false,
    val otherUser: BriefProfileUiState
)

data class MessageUiState(
    val message: String,
    val isFromUser: Boolean
)
