/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.core.common

/**
 * Expresses a constraint on the length of a string.
 */
annotation class StringLength(val min: Int, val max: Int)

/**
 * Expresses a constraint on the range of an integer.
 */
annotation class IntRange(val min: Int, val max: Int)

/**
 * Expresses a regular expression constraint on a string.
 */
annotation class Regex(val pattern: String)
