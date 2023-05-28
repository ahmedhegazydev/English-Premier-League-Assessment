package com.carefer.englishpremierleague.network.repository

import androidx.lifecycle.LiveData
import com.carefer.englishpremierleague.data.model.Matches
import com.carefer.englishpremierleague.data.model.MatchesResponse
import com.carefer.englishpremierleague.state.NetworkState

/* The IMatchesRepository interface defines functions for getting, inserting, retrieving, and deleting
sports matches data. */
interface IMatchesRepository {
    /**
     * This is a suspend function that returns a NetworkState object containing a MatchesResponse.
     */
    suspend fun getAllMatches(): NetworkState<MatchesResponse>

    /**
     * The function inserts a match into a database and returns the ID of the inserted match.
     *
     * @param matches The parameter "matches" is of type "Matches", which is likely a data class or a
     * model class representing a single match in a sports game or competition. This function is likely
     * used to insert a new match into a database or some other data storage system, and it returns a
     * Long value representing the
     */
    suspend fun insertMatch(matches: Matches): Long

    /**
     * This function returns a LiveData object containing a list of saved matches.
     */
    fun getAllSavedMatches(): LiveData<List<Matches>>

    /**
     * This is a suspend function that deletes matches.
     *
     * @param matches The parameter "matches" is of type "Matches", which is likely a data class or a
     * model class that represents a collection of matches. The function "deleteMatches" is a suspend
     * function, which means it can be called from a coroutine and can perform long-running operations
     * without blocking the main thread.
     */
    suspend fun deleteMatches(matches: Matches)

    /**
     * This is a Kotlin function that suspends the execution of the program and deletes all matches.
     */
    suspend fun deleteAllMatches()
}
