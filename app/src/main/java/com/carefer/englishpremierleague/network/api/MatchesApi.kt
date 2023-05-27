package com.carefer.englishpremierleague.network.api

import com.carefer.englishpremierleague.data.model.MatchesResponse
import com.carefer.englishpremierleague.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

/* This is an interface for making API calls to retrieve all matches using a specific API key. */
interface MatchesApi {

    /**
     * This function retrieves all matches using an API key as a header.
     */
    @Headers("X-Auth-Token: $API_KEY")
    @GET("matches")
    suspend fun getAllMatches(
    ): Response<MatchesResponse>

}