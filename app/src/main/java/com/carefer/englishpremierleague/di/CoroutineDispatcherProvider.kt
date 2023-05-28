package com.carefer.englishpremierleague.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import javax.inject.Inject

/**
 * Provide coroutines context.
 */
data class CoroutinesDispatcherProvider(
    val main: CoroutineDispatcher,
    val computation: CoroutineDispatcher,
    val io: CoroutineDispatcher
) {

    /* `@Inject constructor() : this(Main, Default, IO)` is a secondary constructor that is used to
    provide default values for the `CoroutinesDispatcherProvider` class. It is used in dependency
    injection to provide an instance of `CoroutinesDispatcherProvider` with default values for the
    `main`, `computation`, and `io` dispatchers. This allows for easier testing and flexibility in
    changing the dispatchers used in different parts of the application. */
    @Inject
    constructor() : this(Main, Default, IO)
}