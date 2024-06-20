package org.pointyware.xyz.core.navigation

/**
 * Represents a space in an app or content graph.
 */
interface Space<out L: Any?> {
    operator fun contains(location: @UnsafeVariance L): Boolean
}

/**
 * Represents a mutable space in an app or content graph.
 */
interface MutableSpace<K: Any?> : Space<K> {
    fun add(location: K)
    fun remove(location: K)
}

class AggregateSpace<K: Any?> : MutableSpace<K> {

    private val spaces = mutableSetOf<K>()

    override fun contains(location: K): Boolean {
        return spaces.contains(location)
    }

    override fun add(location: K) {
        spaces.add(location)
    }

    override fun remove(location: K) {
        spaces.remove(location)
    }
}
