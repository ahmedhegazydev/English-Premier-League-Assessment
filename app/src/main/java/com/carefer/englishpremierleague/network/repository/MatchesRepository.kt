package com.carefer.englishpremierleague.network.repository

import com.carefer.englishpremierleague.data.local.MatchesDao
import com.carefer.englishpremierleague.data.model.Matches
import com.carefer.englishpremierleague.data.model.MatchesResponse
import com.carefer.englishpremierleague.network.api.ApiHelper
import com.carefer.englishpremierleague.state.NetworkState
import javax.inject.Inject
import javax.inject.Singleton

/* The MatchesRepository class is a Kotlin implementation of INewsRepository that retrieves and stores
match data from remote and local data sources. */
@Singleton
class MatchesRepository @Inject constructor(
    private val remoteDataSource: ApiHelper,
    private val localDataSource: MatchesDao
) : IMatchesRepository {

    /**
     * This is a suspend function that retrieves a list of matches from a remote data source and
     * returns a NetworkState object indicating whether the operation was successful or not.
     *
     * @return A `NetworkState` object containing either a `Success` state with the `MatchesResponse`
     * data if the API call was successful, or an `Error` state with an error message if there was an
     * error.
     */
    override suspend fun getAllMatches(
    ): NetworkState<MatchesResponse> {
        return try {
            val response = remoteDataSource.getALlMatchesList()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                NetworkState.Success(result)
            } else {
                NetworkState.Error("An error occurred")
            }
        } catch (e: Exception) {
            NetworkState.Error("Error occurred ${e.localizedMessage}")
        }
    }

    /**
     * This function inserts a match into the local data source.
     *
     * @param matches The parameter "matches" is an object of type "Matches" that contains information
     * about a match. This parameter is being passed to the function "insertMatch" which is a suspend
     * function that inserts the match information into the local data source.
     */
    override suspend fun insertMatch(matches: Matches) = localDataSource.insert(matches)

    /**
     * This function returns all saved matches from the local data source.
     */
    override fun getAllSavedMatches() = localDataSource.getMatches()

    /**
     * This Kotlin function deletes matches from a local data source.
     *
     * @param matches The parameter "matches" is an object of type "Matches". It is being passed as an
     * argument to the function "deleteMatches". The function is likely deleting the "matches" object
     * from the local data source. The use of "suspend" keyword suggests that this function is a
     * coroutine and can be
     */
    override suspend fun deleteMatches(matches: Matches) = localDataSource.delete(matches)

    /**
     * This Kotlin function deletes all matches from the local data source.
     */
    override suspend fun deleteAllMatches() = localDataSource.deleteAllMatches()
}


