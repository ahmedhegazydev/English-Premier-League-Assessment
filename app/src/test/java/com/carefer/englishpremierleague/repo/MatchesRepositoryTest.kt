package com.carefer.englishpremierleague.repo

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.carefer.englishpremierleague.web.FakeDataUtil
import com.carefer.englishpremierleague.MainCoroutineRule
import com.carefer.englishpremierleague.web.MockWebServerBaseTest
import com.carefer.englishpremierleague.data.local.MatchesDao
import com.carefer.englishpremierleague.data.local.MatchesDatabase
import com.carefer.englishpremierleague.data.model.Matches
import com.carefer.englishpremierleague.network.api.MatchesApi
import com.carefer.englishpremierleague.network.api.ApiHelper
import com.carefer.englishpremierleague.network.api.ApiHelperImpl
import com.carefer.englishpremierleague.network.repository.MatchesRepository
import com.carefer.englishpremierleague.runBlockingTest
import com.google.common.truth.Truth.assertThat
import com.carefer.englishpremierleague.state.NetworkState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class MatchesRepositoryTest : MockWebServerBaseTest() {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private lateinit var matchesRepository: MatchesRepository
    private lateinit var matchesDatabase: MatchesDatabase
    private lateinit var matchesDao: MatchesDao
    private lateinit var apiHelper: ApiHelper
    private lateinit var matchesApi: MatchesApi
    private lateinit var apiHelperImpl: ApiHelperImpl
    private lateinit var responseObserver: Observer<List<Matches>>

    override fun isMockServerEnabled(): Boolean = true

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        matchesDatabase = Room.inMemoryDatabaseBuilder(
            context, MatchesDatabase::class.java
        ).allowMainThreadQueries().build()
        matchesDao = matchesDatabase.getMatchesDao()
        matchesApi = provideTestApiService()
        apiHelperImpl = ApiHelperImpl(matchesApi)
        apiHelper = apiHelperImpl
        matchesRepository = MatchesRepository(apiHelper, matchesDao)
        responseObserver = Observer { }
    }

    @Test
    fun testFavoriteNewsInsertionInDb() {
        coroutineRule.runBlockingTest {
            matchesRepository.insertMatch(FakeDataUtil.getFakeMatch())
            val favNews = matchesRepository.getAllSavedMatches()
            favNews.observeForever(responseObserver)
            assertThat(favNews.value?.isNotEmpty()).isTrue()
        }
    }

    @Test
    fun testRemoveFromDb() {
        coroutineRule.runBlockingTest {
            matchesRepository.deleteAllMatches()
            val favNews = matchesRepository.getAllSavedMatches()
            favNews.observeForever(responseObserver)
            assertThat(favNews.value?.isEmpty()).isTrue()
        }
    }

    @Test
    fun testFavoriteNews() {
        coroutineRule.runBlockingTest {
            val fakeArticle = FakeDataUtil.getFakeMatch()
            matchesRepository.insertMatch(fakeArticle)
            val favoriteArticle = matchesRepository.getAllSavedMatches()
            favoriteArticle.observeForever(responseObserver)
            assertThat(favoriteArticle.value?.get(0)?.id == fakeArticle.id).isTrue()
        }
    }

    @Test
    fun `given response ok when fetching results then return a list with elements`() {
        runBlocking {
            mockHttpResponse("matches_response.json", HttpURLConnection.HTTP_OK)
            val apiResponse = matchesRepository.getAllMatches()

            assertThat(apiResponse).isNotNull()
            assertThat(apiResponse.data?.matches).hasSize(1)
        }
    }

    @Test
    fun `given response ok when fetching empty results then return an empty list`() {
        runBlocking {
            mockHttpResponse("matches_response_empty_list.json", HttpURLConnection.HTTP_OK)
            val apiResponse = matchesRepository.getAllMatches()
            assertThat(apiResponse).isNotNull()
            assertThat(apiResponse.data?.matches).hasSize(0)
        }
    }

    @Test
    fun `given response failure when fetching results then return exception`() {
        runBlocking {
            mockHttpResponse(502)
            val apiResponse = matchesRepository.getAllMatches()

            Assert.assertNotNull(apiResponse)
            val expectedValue = NetworkState.Error("An error occurred", null)
            assertThat(expectedValue.message).isEqualTo(apiResponse.message)
        }
    }

    @After
    fun release() {
        matchesDatabase.close()
        matchesRepository.getAllSavedMatches().removeObserver(responseObserver)
    }
}