/*
 * *
 *  * Created by Rafsan Ahmad on 1/5/22, 11:57 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *
 */

package com.carefer.englishpremierleague.state

/* This is a sealed class in Kotlin that represents the different states of a network request. It has a
generic type parameter `T` that represents the type of data that the network request returns. The
class has four subclasses: `Empty`, `Loading`, `Success`, and `Error`. */
sealed class NetworkState<T>(val data: T? = null, val message: String? = null) {
    /* The class `Empty` is a generic subclass of `NetworkState` in Kotlin. */
    class Empty<T> : NetworkState<T>()
    /* The class Loading is a subclass of NetworkState that represents a loading state for a generic
    type T. */
    class Loading<T> : NetworkState<T>()
    /* The class represents a successful network state with data of type T. */
    class Success<T>(data: T) : NetworkState<T>(data, null)

    @Suppress("UNUSED_PARAMETER")
    /* The class Error represents a network state with an error message and optional data. */
    class Error<T>(message: String, data: T? = null) : NetworkState<T>(null, message)
}