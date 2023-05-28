package com.carefer.englishpremierleague.network.api

import com.carefer.englishpremierleague.data.model.MatchesResponse
import retrofit2.Response
import javax.inject.Inject

/* The `ApiHelperImpl` class retrieves a list of all matches using an API call and returns a response
object containing the matches data. */
class ApiHelperImpl @Inject constructor(private val matchesApi: MatchesApi) : ApiHelper {

    /**
     * This function retrieves a list of all matches using an API call and returns a response object
     * containing the matches data.
     */
    override suspend fun getALlMatchesList(): Response<MatchesResponse> =
        matchesApi.getAllMatches()

}