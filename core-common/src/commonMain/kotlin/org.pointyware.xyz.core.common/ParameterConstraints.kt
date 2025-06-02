/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
 */

package org.pointyware.xyz.core.common

/**
 * Expresses a constraint on the length of a string.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class StringLength(val min: Int = 0, val max: Int = Int.MAX_VALUE)

/**
 * Expresses a constraint on the range of an integer.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class IntRange(val min: Int = Int.MIN_VALUE, val max: Int = Int.MAX_VALUE)

/**
 * Expresses a regular expression constraint on a string.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class Regex(val pattern: String = "^.*$")
