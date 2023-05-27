/*
 * *
 *  * Created by Rafsan Ahmad on 9/27/21, 5:30 PM
 *  * Copyright (c) 2021 . All rights reserved.
 *
 */

package com.carefer.englishpremierleague.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario.launch
import com.carefer.englishpremierleague.web.FakeDataUtil
import com.carefer.englishpremierleague.MainCoroutineRule
import com.carefer.englishpremierleague.data.model.Matches
import com.carefer.englishpremierleague.data.model.MatchesResponse
import com.carefer.englishpremierleague.di.CoroutinesDispatcherProvider
import com.carefer.englishpremierleague.network.repository.MatchesRepository
import com.carefer.englishpremierleague.provideFakeCoroutinesDispatcherProvider
import com.carefer.englishpremierleague.runBlockingTest
import com.carefer.englishpremierleague.state.NetworkState
import com.carefer.englishpremierleague.ui.list.ShowALlMatchesListViewModel
import com.carefer.englishpremierleague.util.NetworkHelper
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ViewModelTest {
    // Executes tasks in the Architecture Components in the same thread
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var networkHelper: NetworkHelper

    @Mock
    private lateinit var matchesRepository: MatchesRepository

    private val testDispatcher = coroutineRule.testDispatcher

    private lateinit var viewModel: ShowALlMatchesListViewModel

    @Before
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