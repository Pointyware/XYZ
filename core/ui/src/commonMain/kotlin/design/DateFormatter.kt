/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.ui.design

import kotlinx.datetime.Instant

interface DateFormatter {
    fun format(date: Instant): String
}
object SimpleDateFormatter : DateFormatter {
    override fun format(date: Instant): String {
        return date.toString()
    }
}
