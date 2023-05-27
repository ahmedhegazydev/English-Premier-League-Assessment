/*
 * *
 *  * Created by Rafsan Ahmad on 9/27/21, 5:30 PM
 *  * Copyright (c) 2021 . All rights reserved.
 *
 */

package com.carefer.englishpremierleague.network.api

import com.carefer.englishpremierleague.data.model.MatchesResponse
import retrofit2.Response

/* The `interface ApiHelper` is defining a contract for classes that implement it to provide a function
`getALlMatchesList()` that suspends and returns a response containing a list of all matches. This
interface is likely used in the context of making API calls to retrieve data related to matches in a
sports league. */
interface ApiHelper {
    /**
     * This function suspends and returns a response containing a list of all matches.
     */
    suspend fun getALlMatchesList(): Response<MatchesResponse>
}