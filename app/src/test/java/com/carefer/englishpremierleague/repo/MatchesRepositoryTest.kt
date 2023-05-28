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
/* This is a test class for the MatchesRepository class in Kotlin, which tests various functions such
as inserting and removing data from a database, fetching data from an API, and handling different
response scenarios. */
class MatchesRepositoryTest : MockWebServerBaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    /* `private lateinit var matchesRepository: MatchesRepository` is declaring a private property
    `matchesRepository` of type `MatchesRepository` and marking it as `lateinit`, which means that
    it will be initialized later in the code. */
    private lateinit var matchesRepository: MatchesRepository
    /* `private lateinit var matchesDatabase: MatchesDatabase` is declaring a private property
    `matchesDatabase` of type `MatchesDatabase` and marking it as `lateinit`, which means that it
    will be initialized later in the code. It is used to create an in-memory database for testing
    purposes. */
    private lateinit var matchesDatabase: MatchesDatabase
    /* `private lateinit var matchesDao: MatchesDao` is declaring a private property `matchesDao` of
    type `MatchesDao` and marking it as `lateinit`, which means that it will be initialized later in
    the code. It is used to access the data access object (DAO) for the `Matches` entity in the
    local database. The DAO provides methods for performing CRUD (create, read, update, delete)
    operations on the `Matches` table in the database. */
    private lateinit var matchesDao: MatchesDao
    /* `private lateinit var apiHelper: ApiHelper` is declaring a private property `apiHelper` of type
    `ApiHelper` and marking it as `lateinit`, which means that it will be initialized later in the
    code. It is used to access the API methods through the `ApiHelper` interface. The `ApiHelper`
    interface provides a layer of abstraction between the repository and the API service, allowing
    for easier testing and maintenance. */
    private lateinit var apiHelper: ApiHelper
    /* `private lateinit var matchesApi: MatchesApi` is declaring a private property `matchesApi` of
    type `MatchesApi` and marking it as `lateinit`, which means that it will be initialized later in
    the code. It is used to access the API methods through the `MatchesApi` interface. The
    `MatchesApi` interface provides a set of methods for fetching data from the API service. */
    private lateinit var matchesApi: MatchesApi
    /* `private lateinit var apiHelperImpl: ApiHelperImpl` is declaring a private property
    `apiHelperImpl` of type `ApiHelperImpl` and marking it as `lateinit`, which means that it will
    be initialized later in the code. It is used to create an instance of the `ApiHelperImpl` class,
    which implements the `ApiHelper` interface and provides methods for accessing the API service.
    This instance is then passed to the `MatchesRepository` constructor to create a repository
    object that can interact with the API service. */
    private lateinit var apiHelperImpl: ApiHelperImpl
    /* `private lateinit var responseObserver: Observer<List<Matches>>` is declaring a private property
    `responseObserver` of type `Observer<List<Matches>>` and marking it as `lateinit`, which means
    that it will be initialized later in the code. It is used to observe changes to the list of
    saved matches in the local database and trigger updates to the UI. The `Observer` interface is
    part of the Android Architecture Components and provides a way to react to changes in LiveData
    objects. In this case, the `responseObserver` is used to observe changes to the `LiveData`
    object returned by the `getAllSavedMatches()` method in the `MatchesRepository` class. When the
    list of saved matches changes, the `responseObserver` is notified and can update the UI
    accordingly. */
    private lateinit var responseObserver: Observer<List<Matches>>

    /**
     * This function returns a boolean value indicating whether the mock server is enabled or not.
     */
    override fun isMockServerEnabled(): Boolean = true

    @Before
    /**
     * This function sets up the necessary components for testing a MatchesRepository in Kotlin.
     */
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
    /**
     * The function tests the insertion of a favorite match into a database and checks if it is
     * successfully saved.
     */
    fun testFavoriteMatchesInsertionInDb() {
        coroutineRule.runBlockingTest {
            matchesRepository.insertMatch(FakeDataUtil.getFakeMatch())
            val favMatches = matchesRepository.getAllSavedMatches()
            favMatches.observeForever(responseObserver)
            assertThat(favMatches.value?.isNotEmpty()).isTrue()
        }
    }

    @Test
    /**
     * The function tests the removal of all matches from the database and checks if the list of saved
     * matches is empty.
     */
    fun testRemoveFromDb() {
        coroutineRule.runBlockingTest {
            matchesRepository.deleteAllMatches()
            val favMatches = matchesRepository.getAllSavedMatches()
            favMatches.observeForever(responseObserver)
            assertThat(favMatches.value?.isEmpty()).isTrue()
        }
    }

    @Test
    /* `fun testFavoriteMatches() {` is a test function that tests the insertion of a fake match into
    the local database and checks if it is successfully saved. It uses the
    `FakeDataUtil.getFakeMatch()` method to generate a fake match object and inserts it into the
    database using the `matchesRepository.insertMatch()` method. It then retrieves the list of saved
    matches from the database using the `matchesRepository.getAllSavedMatches()` method and observes
    changes to the list using the `responseObserver` object. Finally, it checks if the list of saved
    matches contains the inserted match by comparing their IDs. */
    fun testFavoriteMatches() {
        coroutineRule.runBlockingTest {
            val fakeMatch = FakeDataUtil.getFakeMatch()
            matchesRepository.insertMatch(fakeMatch)
            val favoriteMatches = matchesRepository.getAllSavedMatches()
            favoriteMatches.observeForever(responseObserver)
            assertThat(favoriteMatches.value?.get(0)?.id == fakeMatch.id).isTrue()
        }
    }

    @Test
    /**
     * This function tests if a list of matches is returned when a response with HTTP_OK status code is
     * received.
     */
    fun `given response ok when fetching results then return a list with elements`() {
        runBlocking {
            mockHttpResponse("matches_response.json", HttpURLConnection.HTTP_OK)
            val apiResponse = matchesRepository.getAllMatches()

            assertThat(apiResponse).isNotNull()
            assertThat(apiResponse.data?.matches).hasSize(1)
        }
    }

    @Test
    /**
     * This function tests that when fetching empty results, the API response returns an empty list.
     */
    fun `given response ok when fetching empty results then return an empty list`() {
        runBlocking {
            mockHttpResponse("matches_response_empty_list.json", HttpURLConnection.HTTP_OK)
            val apiResponse = matchesRepository.getAllMatches()
            assertThat(apiResponse).isNotNull()
            assertThat(apiResponse.data?.matches).hasSize(0)
        }
    }

    @Test
    /**
     * This function tests that when there is a response failure while fetching results, an exception
     * is returned.
     */
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
    /**
     * The function "release" closes a database and removes an observer for saved matches.
     */
    fun release() {
        matchesDatabase.close()
        matchesRepository.getAllSavedMatches().removeObserver(responseObserver)
    }
}