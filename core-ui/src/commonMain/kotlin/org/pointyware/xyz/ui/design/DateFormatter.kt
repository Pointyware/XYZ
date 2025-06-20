/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.ui.design

import kotlinx.datetime.Instant

interface DateFormatter {
    fun format(date: Instant): String
    fun format(date: Instant?, default: String): String
}
object SimpleDateFormatter : DateFormatter {
    override fun format(date: Instant): String {
        return date.toString()
    }

    override fun format(date: Instant?, default: String): String {
        return date?.let { format(it) } ?: default
    }
}
