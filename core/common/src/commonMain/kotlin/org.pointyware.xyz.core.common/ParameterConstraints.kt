/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.common

/**
 * Expresses a constraint on the length of a string.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class StringLength(val min: Int, val max: Int)

/**
 * Expresses a constraint on the range of an integer.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class IntRange(val min: Int, val max: Int)

/**
 * Expresses a regular expression constraint on a string.
 */
@Target(AnnotationTarget.PROPERTY)
annotation class Regex(val pattern: String)
