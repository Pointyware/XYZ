package org.pointyware.xyz.core.navigation



interface TypedKey<in K: Any?, out V: Any?> {
    val id: @UnsafeVariance K
}
data class ValueTypedKey<K: Any?, V: Any?>(override val id: K) : TypedKey<K, V>
fun <K: Any?, V: Any?> K.asTypedKey(): TypedKey<K, V> {
    return ValueTypedKey(this)
}

val someKey = 1.asTypedKey<_, String>()

interface Arguments {
    operator fun <K: Any?> get(key: TypedKey<K, *>): K?
    operator fun <K: Any?> contains(key: TypedKey<K, *>): Boolean
}
interface MutableArguments : Arguments {
    operator fun <K: Any?> set(key: TypedKey<K, *>, value: K)
}
