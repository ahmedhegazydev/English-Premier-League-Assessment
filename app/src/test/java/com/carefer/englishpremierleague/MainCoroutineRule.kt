package com.carefer.englishpremierleague

import com.carefer.englishpremierleague.di.CoroutinesDispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Sets the main coroutines dispatcher to a [TestCoroutineScope] for unit testing. A
 * [TestCoroutineScope] provides control over the execution of coroutines.
 *
 */
@ExperimentalCoroutinesApi
class MainCoroutineRule(
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher() {

    /**
     * This function sets the main dispatcher to a test dispatcher in Kotlin.
     *
     * @param description The `description` parameter is an instance of the `Description` class, which
     * provides information about the test being run, such as the test class name, method name, and
     * annotations. The `starting` method is called before the test is executed, and this parameter
     * allows you to access and modify information
     */
    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    /**
     * This function resets the main dispatcher and cleans up test coroutines after a test has finished
     * in Kotlin.
     *
     * @param description `Description` is a class in JUnit that represents a description of a test. It
     * contains information about the test method, such as its name, class, and annotations. The
     * `finished` method is called by JUnit after a test has finished running, and the `description`
     * parameter is the
     */
    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}

/* This code defines an extension function `runBlockingTest` for `MainCoroutineRule` class. The
function takes a suspend lambda `block` as a parameter and runs it using the `testDispatcher` of the
`MainCoroutineRule` instance. The `runBlockingTest` function is marked with
`@ExperimentalCoroutinesApi` annotation, which indicates that it uses experimental APIs from the
Kotlin coroutines library. This function is used to run a suspend function in a blocking manner
during unit testing. */
@ExperimentalCoroutinesApi
fun MainCoroutineRule.runBlockingTest(block: suspend () -> Unit) =
    this.testDispatcher.runBlockingTest {
        block()
    }

/* This code defines a function `provideFakeCoroutinesDispatcherProvider` that returns a
`CoroutinesDispatcherProvider` object. The function takes an optional `TestCoroutineDispatcher`
parameter called `dispatcher`. If `dispatcher` is not null, it is used as the dispatcher for all
three types of coroutines (IO, computation, and main). If `dispatcher` is null, a new
`TestCoroutineDispatcher` is created and used as the dispatcher for all three types of coroutines.
This function is marked with `@ExperimentalCoroutinesApi` annotation, which indicates that it uses
experimental APIs from the Kotlin coroutines library. */
@ExperimentalCoroutinesApi
fun provideFakeCoroutinesDispatcherProvider(
    dispatcher: TestCoroutineDispatcher?
): CoroutinesDispatcherProvider {
    /* `val sharedTestCoroutineDispatcher = TestCoroutineDispatcher()` creates a new instance of
    `TestCoroutineDispatcher` which is used as the default dispatcher for all three types of
    coroutines (IO, computation, and main) if a specific dispatcher is not provided. This allows for
    consistent and controlled testing of coroutines in a unit testing environment. */
    val sharedTestCoroutineDispatcher = TestCoroutineDispatcher()
    return CoroutinesDispatcherProvider(
        dispatcher ?: sharedTestCoroutineDispatcher,
        dispatcher ?: sharedTestCoroutineDispatcher,
        dispatcher ?: sharedTestCoroutineDispatcher
    )
}