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
class MatchesDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var matchesDatabase: MatchesDatabase
    private lateinit var matchesDao: MatchesDao

    @Before
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
    fun teardown() {
        matchesDatabase.close()
    }

    @Test
    fun getAllMatches() = runTest {
        val matchesList = matchesDao.getMatches().getOrAwaitValue()
        assertThat(matchesList).isNotEmpty()
    }

    @Test
    fun insertNewMatch() = runTest {
        val matchesToAdd =
            com.carefer.englishpremierleague.data.model.Matches(id = 2, status = "IN_PLAY")
        matchesDao.insert(matchesToAdd)

        val matchesList = matchesDao.getMatches().getOrAwaitValue()
        assertThat(matchesList).contains(matchesToAdd)
    }

    @Test
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
    fun deleteAllExistMatches() = runTest {
        val match = com.carefer.englishpremierleague.data.model.Matches(id = 2, status = "IN_PLAY")

        matchesDao.insert(match)
        matchesDao.deleteAllMatches()

        val matchesList = matchesDao.getMatches().getOrAwaitValue()
        assertThat(matchesList).isEmpty()
    }
}