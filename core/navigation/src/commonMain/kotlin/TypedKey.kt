package org.pointyware.xyz.core.navigation

data class TypedKey<T>(
    val key: String
)

fun <T> String.toTypedKey(): TypedKey<T> {
    return TypedKey(this)
}
