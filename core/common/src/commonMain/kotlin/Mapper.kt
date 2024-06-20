package org.pointyware.xyz.core.common

/**
 *
 */
interface Mapper<I, O> {
    fun map(input: I): O
}
