package com.carefer.englishpremierleague.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.carefer.englishpremierleague.util.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
/* This is a test class for the MatchesDao, which tests its methods for getting, inserting, and
deleting matches from a MatchesDatabase. */
class MatchesDaoTest {

    @get:Rule
    /* `var instantTaskExecutorRule = InstantTaskExecutorRule()` is creating an instance of
    `InstantTaskExecutorRule`, which is a JUnit rule that configures `LiveData` to execute each task
    synchronously on the calling thread. This is necessary for testing `LiveData` objects that are
    observed on the main thread, as the main thread is not available during unit tests. */
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    /* `private lateinit var matchesDatabase: MatchesDatabase` is declaring a private property
    `matchesDatabase` of type `MatchesDatabase` and marking it with the `lateinit` keyword, which
    means that the property will be initialized later. This property is used to hold an instance of
    the `MatchesDatabase` class, which is a Room database that stores `Matches` objects. */
    private lateinit var matchesDatabase: MatchesDatabase
    /* `private lateinit var matchesDao: MatchesDao` is declaring a private property `matchesDao` of
    type `MatchesDao` and marking it with the `lateinit` keyword, which means that the property will
    be initialized later. This property is used to hold an instance of the `MatchesDao` interface,
    which is a Data Access Object (DAO) that provides methods for accessing and manipulating data in
    the `MatchesDatabase` Room database. */
    private lateinit var matchesDao: MatchesDao

    @Before
    /**
     * The function sets up an in-memory database for matches and adds a default value to it.
     */
    fun setup() {
        matchesDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MatchesDatabase::class.java
        ).allowMainThreadQueries().build()

        matchesDao = matchesDatabase.getMatchesDao()

        //Add default value
        val matchesToAdd =
            com.carefer.englishpremierleague.data.model.Matches(id = 1, status = "IN_PLAY")
        runTest {
            matchesDao.insert(matchesToAdd)
        }
    }

    @After
    /**
     * The function `teardown()` closes the `matchesDatabase`.
     */
    fun teardown() {
        matchesDatabase.close()
    }

    @Test
    /**
     * This function tests that the list of matches obtained from the matchesDao is not empty.
     */
    fun getAllMatches() = runTest {
        val matchesList = matchesDao.getMatches().getOrAwaitValue()
        assertThat(matchesList).isNotEmpty()
    }

    @Test
    /**
     * The function inserts a new match into a database and checks if the match was successfully added.
     */
    fun insertNewMatch() = runTest {
        val matchesToAdd =
            com.carefer.englishpremierleague.data.model.Matches(id = 2, status = "IN_PLAY")
        matchesDao.insert(matchesToAdd)

        val matchesList = matchesDao.getMatches().getOrAwaitValue()
        assertThat(matchesList).contains(matchesToAdd)
    }

    @Test
    /**
     * The function tests the deletion of existing matches from a database using Kotlin.
     */
    fun deleteExistMatches() = runTest {
        val match1 =
            com.carefer.englishpremierleague.data.model.Matches(id = 1, status = "IN_PLAY")
        val match2 = com.carefer.englishpremierleague.data.model.Matches(id = 3, status = "PAUSED")
        matchesDao.insert(match1)
        matchesDao.insert(match2)
        matchesDao.delete(match1)

        val matchesList = matchesDao.getMatches().getOrAwaitValue()
        assertThat(matchesList).doesNotContain(match1)
    }

    @Test
    /**
     * This function tests the deletion of all matches from a database.
     */
    fun deleteAllExistMatches() = runTest {
        val match = com.carefer.englishpremierleague.data.model.Matches(id = 2, status = "IN_PLAY")

        matchesDao.insert(match)
        matchesDao.deleteAllMatches()

        val matchesList = matchesDao.getMatches().getOrAwaitValue()
        assertThat(matchesList).isEmpty()
    }
}