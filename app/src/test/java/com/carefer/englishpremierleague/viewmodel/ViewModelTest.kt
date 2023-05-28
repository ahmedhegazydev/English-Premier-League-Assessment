
package com.carefer.englishpremierleague.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.carefer.englishpremierleague.web.FakeDataUtil
import com.carefer.englishpremierleague.MainCoroutineRule
import com.carefer.englishpremierleague.network.repository.MatchesRepository
import com.carefer.englishpremierleague.provideFakeCoroutinesDispatcherProvider
import com.carefer.englishpremierleague.runBlockingTest
import com.carefer.englishpremierleague.state.NetworkState
import com.carefer.englishpremierleague.ui.list.ShowALlMatchesListViewModel
import com.carefer.englishpremierleague.util.NetworkHelper
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
/* The ViewModelTest class contains unit tests for a Kotlin ViewModel that retrieves and displays a
list of matches. */
class ViewModelTest {
    // Executes tasks in the Architecture Components in the same thread
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Mock
    /* `private lateinit var networkHelper: NetworkHelper` is declaring a private property
    `networkHelper` of type `NetworkHelper` that will be initialized later in the `setUp()`
    function. This property is used to hold an instance of the `NetworkHelper` class, which is used
    to check if the device is connected to the internet. The `lateinit` keyword is used to indicate
    that the property will be initialized later, after the object is created. */
    private lateinit var networkHelper: NetworkHelper

    @Mock
    private lateinit var matchesRepository: MatchesRepository

    /* `private val testDispatcher = coroutineRule.testDispatcher` is initializing a test dispatcher
    that will be used to run coroutines in unit tests. It is part of the setup process for the unit
    tests and is used to ensure that coroutines are run on the main thread during testing. The
    `coroutineRule` is an instance of `MainCoroutineRule`, which is a JUnit rule that swaps the main
    dispatcher with a test dispatcher. The `testDispatcher` is then used to provide a fake
    `CoroutineDispatcher` to the `coroutinesDispatcherProvider` in the view model, which allows for
    the testing of coroutines in a controlled environment. */
    private val testDispatcher = coroutineRule.testDispatcher

    /* `private lateinit var viewModel: ShowALlMatchesListViewModel` is declaring a private property
    `viewModel` of type `ShowALlMatchesListViewModel` that will be initialized later in the
    `setUp()` function. This property is used to hold an instance of the view model being tested in
    the unit tests. The `lateinit` keyword is used to indicate that the property will be initialized
    later, after the object is created. */
    private lateinit var viewModel: ShowALlMatchesListViewModel

    @Before
    /**
     * This function sets up a test environment for a Kotlin function that uses Mockito and initializes
     * a view model.
     */
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = ShowALlMatchesListViewModel(
            matchesRepository = matchesRepository,
            networkHelper = networkHelper,
            coroutinesDispatcherProvider = provideFakeCoroutinesDispatcherProvider(testDispatcher)
        )
    }

    @Test
    /**
     * This is a unit test function that checks if the view model returns a loading state when calling
     * for results.
     */
    fun `when calling for results then return loading state`() {
        coroutineRule.runBlockingTest {
            `when`(networkHelper.isNetworkConnected())
                .thenReturn(true)
            `when`(matchesRepository.getAllMatches())
                .thenReturn(NetworkState.Loading())

            //When
            viewModel.getAllMatchesListShowing()

            //Then
            assertThat(viewModel.matchesResponse.value).isNotNull()
            assertThat(viewModel.matchesResponse.value.data).isNull()
            assertThat(viewModel.matchesResponse.value.message).isNull()
        }
    }

    @Test
    /**
     * This is a unit test function written in Kotlin that tests if the getAllMatchesListShowing()
     * function in a ViewModel returns non-empty matches when network is connected and fake data is
     * provided by the repository.
     */
    fun `when calling for results then return matches results`() {
        coroutineRule.runBlockingTest {
            `when`(networkHelper.isNetworkConnected())
                .thenReturn(true)

            // Stub repository with fake favorites
            `when`(matchesRepository.getAllMatches())
                .thenAnswer { (FakeDataUtil.getFakeMatchesResponse()) }

            //When
            viewModel.getAllMatchesListShowing()

            //then
            assertThat(viewModel.matchesResponse.value).isNotNull()
            val matches = viewModel.matchesResponse.value.data?.matches
            assertThat(matches?.isNotEmpty())
        }
    }

    @Test
    /**
     * This function tests that an error message is returned when calling for results in a Kotlin
     * coroutine.
     */
    fun `when calling for results then return error`() {
        coroutineRule.runBlockingTest {
            `when`(networkHelper.isNetworkConnected())
                .thenReturn(true)
            // Stub repository with fake favorites
            `when`(matchesRepository.getAllMatches())
                .thenAnswer { NetworkState.Error("Error occurred", null) }

            //When
            viewModel.getAllMatchesListShowing()

            //then
            val response = viewModel.matchesResponse.value
            assertThat(response.message).isNotNull()
            assertThat(response.message).isEqualTo("Error occurred")
        }
    }

    @Test
    /**
     * The function tests the formatting of a date string with "T" in it and returns the formatted date
     * in a specific format.
     */
    fun `test format date with T`() {
        val result = viewModel.formatDate("2021-09-29T13:01:31Z")
        assertThat(result).isNotNull()
        assertThat(result).isEqualTo("Sep 29, 2021 01:01 PM")
    }

    @Test
    /**
     * The function tests the formatting of a date string without the "T" character in Kotlin.
     */
    fun `test format date without T`() {
        val result = viewModel.formatDate("2021-09-29 3:01:31 PM")
        assertThat(result).isNotNull()
        assertThat(result).isEqualTo("2021-09-29 3:01:31 PM")
    }

    @After
    /**
     * The function clears inline mocks in the Mockito framework.
     */
    fun release() {
        Mockito.framework().clearInlineMocks()
    }
}