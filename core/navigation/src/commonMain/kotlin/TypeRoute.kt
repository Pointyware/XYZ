/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.navigation

import kotlin.reflect.KClass

/**
 * The final value built from a template used to direct a router to specific content.
 */
data class Path(
    val segment: String,
    val parent: Path? = null
) {
    val value: String by lazy {
        if (parent == null) {
            segment
        } else {
            "${parent.value}$pathSeparator${segment}"
        }
    }
}

fun emptyPath() = Path("")

const val pathSeparator = "/"
const val pathArgumentPlaceholder = "{id}"
const val pathParameterDefault = "id"

/**
 * V: value type
 * P: parent type
 */
data class DynamicRoute<V, P>(
    /**
     * The segment is the template that will be used to build a path.
     */
    val segment: String,
    /**
     * This the name of the argument that will hold the value in the segment.
     */
    val parameterName: String,
    /**
     * The route leading to this branch in navigation.
     */
    val parent: P
) {
    fun fixed(segment: String): StaticRoute<DynamicRoute<V, P>> {
        return StaticRoute(segment, this)
    }

    inline fun <reified N:Any> variable(
        template: String,
        parameterName: String = template.replace(pathArgumentPlaceholder, pathParameterDefault),
        klass: KClass<N> = N::class
    ): DynamicRoute<N, DynamicRoute<V, P>> {
        return DynamicRoute(template, parameterName,this)
    }

    fun provide(value: V, prefix: (P)->Path): Path {
        val replacedSegment = segment.replace(pathArgumentPlaceholder, value.toString())
        val prefixPath = prefix(parent)
        return Path(replacedSegment, prefixPath)
    }
}

/**
 * P: parent type
 */
data class StaticRoute<P>(
    val segment: String,
    val parent: P
) {
    fun skip(prefix: (P)->Path): Path {
        val parent = prefix(this.parent)
        return Path(segment, parent)
    }

    fun fixed(segment: String): StaticRoute<StaticRoute<P>> {
        return StaticRoute(segment, this)
    }

    inline fun <reified N:Any> variable(
        template: String,
        parameterName: String = template.replace(pathArgumentPlaceholder, pathParameterDefault),
        klass: KClass<N> = N::class
    ): DynamicRoute<N, StaticRoute<P>> {
        return DynamicRoute(template, parameterName,this)
    }
}
