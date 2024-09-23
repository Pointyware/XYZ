/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.ride.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import org.pointyware.xyz.feature.ride.entities.PaymentMethod

/**
 * Represents the state of the payment selection view.
 */
sealed interface PaymentSelectionViewState {
    data class PaymentSelected(val method: PaymentMethod?) : PaymentSelectionViewState
    data class SelectPayment(val methods: List<PaymentMethod>) : PaymentSelectionViewState
}

/**
 * Displays the currently selected payment (if any) and allows the user to select a
 * different payment method.
 */
@Composable
fun PaymentSelectionView(
    state: PaymentSelectionViewState,
    modifier: Modifier = Modifier,
    onSelectPayment: ()->Unit,
    onPaymentSelected: (PaymentMethod)->Unit
) {
    val contentDescription = remember(state) { when(state) {
        is PaymentSelectionViewState.SelectPayment -> {
            "Select Payment Method"
        }
        is PaymentSelectionViewState.PaymentSelected -> {
            "Payment Method"
        }
    }}
    Column(
        modifier = modifier.semantics {
            this.contentDescription = contentDescription
        }
    ) {
        AnimatedContent(targetState = state, contentKey = { it::class }) { targetState ->
            when (targetState) {
                is PaymentSelectionViewState.PaymentSelected -> {
                    val method = targetState.method
                    if (method != null) {
                        Text("Selected Payment Method: ${method.paymentProvider} *${method.lastFour}")
                    } else {
                        Text("No Payment Method Selected")
                    }
                    Button(onClick = onSelectPayment) {
                        Text("Select Payment Method")
                    }
                }
                is PaymentSelectionViewState.SelectPayment -> {
                    LazyColumn {
                        items(targetState.methods) { method ->
                            Button(onClick = { onPaymentSelected(method) }) {
                                Text("${method.paymentProvider} *${method.lastFour}")
                            }
                        }
                    }
                }
            }
        }
    }
}
