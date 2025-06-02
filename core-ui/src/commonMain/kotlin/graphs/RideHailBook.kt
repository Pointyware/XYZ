/*
 * Copyright (c) 2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui.graphs

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

/**
 * Draws the ride hail book chart.
 *
 * A typical order book works like in this example: https://en.wikipedia.org/wiki/Order_book#/media/File:Order_book_depth_chart.gif
 * The x-axis is the unit price, and the y-axis is the cumulative order depth.
 * Bids (buyers) appear on the left, and asks (sellers) appear on the right.
 *
 * In this case, we have a ride hail book, which has hails on the left, and rides on the right,
 * both sorted by the order rate.
 * The x-axis is different in that each position on the x-axis represents a different ride hail or ride,
 * and the y-axis is the individual order rate.
 *
 * Unlike a typical order book chart, the vertical axis does not display price volume.
 *
 * @param bids The list of bids (hails) to be displayed on the left side of the chart. These should
 * be sorted in descending order.
 * @param asks The list of asks (rides) to be displayed on the right side of the chart. These
 * should be sorted in ascending order.
 * @param bidWidth The width of the bid bars.
 */
fun DrawScope.drawRideHailBook(
    bids: List<Float>,
    asks: List<Float>,
    bidWidth: Float,
    axisWidth: Float,
    priceCeiling: Float = 10f,
    bidColor: Color = Color.Green,
    askColor: Color = Color.Red,
    axisColor: Color = Color.Gray,
) {
    val width = size.width
    val height = size.height
    val centerX = width / 2f

    // draw the center vertical axis
    drawLine(
        color = axisColor,
        start = Offset(centerX, 0f),
        end = Offset(centerX, height),
        strokeWidth = axisWidth
    )

    // draw the horizontal axis
    drawLine(
        color = axisColor,
        start = Offset(0f, height - axisWidth / 2),
        end = Offset(width, height - axisWidth / 2),
        strokeWidth = axisWidth
    )

    // draw the horizontal axis labels


    // draw the bids from center out left until the end of the chart
    for (i in bids.indices) {
        val x = centerX - (i + 1) * bidWidth + bidWidth / 2 - axisWidth / 2
        val y = height * (1 - bids[i] / priceCeiling)
        drawLine(
            color = bidColor,
            start = Offset(x, height - axisWidth / 2),
            end = Offset(x, y),
            strokeWidth = bidWidth
        )
    }

    // draw the asks from center out right until the end of the chart
    for (i in asks.indices) {
        val x = centerX + (i + 1) * bidWidth - bidWidth / 2 + axisWidth / 2
        val y = height * (1 - asks[i] / priceCeiling)
        drawLine(
            color = askColor,
            start = Offset(x, height - axisWidth / 2),
            end = Offset(x, y),
            strokeWidth = bidWidth
        )
    }
}
