/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update


/**
 * Gives the ability to navigate amongst locations within a space, back to previous locations, and forward to locations that have been navigated back from.
 *
 * @param K The type of the keys that identify locations in the space.
 * @param A The type of the arguments that can be passed when navigating to a location.
 */
interface StackNavigationController<K, A> {

    /**
     * The space that the controller is navigating within.
     */
    val space: Space<K>

    /**
     * Navigate to a new location in the space, passing the given arguments.
     *
     * TODO: add navOptions/arguments to modify stack frame behavior
     */
    fun navigateTo(location: @UnsafeVariance K, arguments: A? = null)

    /**
     * The current location in the space.
     */
    val currentLocation: StateFlow<K>

    /**
     * A list of locations that the user has navigated through.
     */
    val backList: StateFlow<List<K>>

    /**
     * Go back to the previous location in the [backList].
     */
    fun goBack()

    /**
     * A list of locations that the user has navigated back from. This gets reset when the user navigates forward to a new location.
     */
    val forwardList: StateFlow<List<K>>
    /**
     * Go forward to the next location in [forwardList].
     */
    fun goForward()

    data class Frame<K, A>(
        val location: K,
        val arguments: A?,
        // options
    )

    val currentFrame: StateFlow<Frame<K, A>>

}

class StackNavigationControllerImpl<K: Any?, A: Any?>(
    initialLocation: K,
    private val stateScope: CoroutineScope = CoroutineScope(Dispatchers.Main),
): StackNavigationController<K, A> {

    internal data class State<K, A>(
        val backList: List<StackNavigationController.Frame<K, A>>,
        val forwardList: List<StackNavigationController.Frame<K, A>>,
        val frame: StackNavigationController.Frame<K, A>,
    )
    private val mutableState = MutableStateFlow(State(emptyList(), emptyList(), StackNavigationController.Frame<K, A>(initialLocation, null)))

    private val _space = AggregateSpace<K>()
    override val space: Space<K>
        get() = _space

    override val currentLocation: StateFlow<K>
        get() = mutableState.value.frame.location.let { currentValue ->
            mutableState.map { it.frame.location }
                .stateIn(stateScope, SharingStarted.WhileSubscribed(), currentValue)
        }


    override val backList: StateFlow<List<K>>
        get() = mutableState.value.let { currentValue ->
            mutableState.map { it.backList.map { it.location } }
                .stateIn(stateScope, SharingStarted.WhileSubscribed(), currentValue.backList.map { it.location })
        }

    override fun goBack() {
        mutableState.update { currentState ->
            val previous = currentState.backList.lastOrNull()
            previous?.let {
                currentState.copy(
                    backList = currentState.backList.dropLast(1),
                    forwardList = currentState.forwardList + currentState.frame,
                    frame = previous
                )
            } ?: throw IllegalStateException("Back stack is empty")
        }
    }

    override val forwardList: StateFlow<List<K>>
        get() = mutableState.value.let { currentValue ->
            mutableState.map { it.forwardList.map { it.location } }
                .stateIn(stateScope, SharingStarted.WhileSubscribed(), currentValue.forwardList.map { it.location })
        }

    override fun goForward() {
        mutableState.update { currentState ->
            val next = currentState.forwardList.lastOrNull()
            next?.let {
                currentState.copy(
                    backList = currentState.backList + currentState.frame,
                    forwardList = currentState.forwardList.dropLast(1),
                    frame = next
                )
            } ?: throw IllegalStateException("Back stack is empty")
        }
    }

    override val currentFrame: StateFlow<StackNavigationController.Frame<K, A>>
        get() = mutableState.value.frame.let { currentValue ->
            mutableState.map { it.frame }
                .stateIn(stateScope, SharingStarted.WhileSubscribed(), currentValue)
        }

    override fun navigateTo(location: K, arguments: A?) {
        mutableState.update {
            it.copy(
                backList = it.backList + it.frame,
                forwardList = emptyList(),
                frame = StackNavigationController.Frame(location, arguments),
            )
        }
    }
}

fun <K, A> StackNavigationController(
    initialLocation: K,
): StackNavigationController<K, A> {
    return StackNavigationControllerImpl(initialLocation)
}

typealias XyzNavController = StackNavigationController<Any, Any?>
typealias XyzRootScope = LocationRootScope<Any, Any?>
