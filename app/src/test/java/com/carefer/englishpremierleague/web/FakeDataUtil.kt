package com.carefer.englishpremierleague.web

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.carefer.englishpremierleague.data.model.*
import com.carefer.englishpremierleague.state.NetworkState

/* The `FakeDataUtil` object contains functions that generate fake data for testing purposes. It
includes functions to generate a successful network state with a fake `MatchesResponse` object, a
`LiveData` object containing a list of fake matches, a list of fake matches with various details,
and a fake match object with various properties initialized. These functions can be used to test the
functionality of the application without relying on real data. */
object FakeDataUtil {

    /**
     * The function returns a successful network state with a fake MatchesResponse object.
     *
     * @return A `NetworkState` object containing a `MatchesResponse` object wrapped in a `Success`
     * state.
     */
    fun getFakeMatchesResponse(): NetworkState<MatchesResponse> {
        val matches = getFakeMatches()
        val matchResponse = MatchesResponse(
            matches = matches,
            filters = Filters(),
            resultSet = ResultSet(),
            competition = Competition(),
        )
        return NetworkState.Success(matchResponse)
    }

    /**
     * This function returns a LiveData object containing a list of fake matches.
     *
     * @return a LiveData object that contains a list of fake matches.
     */
    fun getFakeMatchesLiveData(): LiveData<List<Matches>> {
        val list = MutableLiveData<List<Matches>>()
        val result: LiveData<List<Matches>> = list
        list.postValue(getFakeMatches())
        return result
    }

    /**
     * The function returns a list of fake matches with various details.
     *
     * @return The function `getFakeMatches()` returns a MutableList of Matches objects.
     */
    fun getFakeMatches(): MutableList<Matches> {
        val matchesLIst: MutableList<Matches> = arrayListOf()
        matchesLIst.add(
            Matches(
                area = Area(),
                competition = Competition(),
                season = Season(),
                id = 1,
                utcDate = "2023-05-26T22:22:22Z",
                status = "Completed",
                minute = "90",
                injuryTime = 3,
                attendance = "50000",
                venue = "Stadium ABC",
                matchday = 1,
                stage = "Group Stage",
                group = "Group A",
                lastUpdated = "2023-05-26 15:30:00",
                homeTeam = HomeTeam(),
                awayTeam = AwayTeam(),
                score = Score(),
                goals = mutableListOf(),
                penalties = mutableListOf(),
                bookings = mutableListOf(),
                substitutions = mutableListOf(),
                odds = Odds(),
                referees = mutableListOf()
            ),
        )
        matchesLIst.add(
            Matches(
                area = Area(),
                competition = Competition(),
                season = Season(),
                id = 2,
                utcDate = "2023-05-26T22:22:22Z",
                status = "Scheduled",
                minute = null,
                injuryTime = null,
                attendance = null,
                venue = "Stadium XYZ",
                matchday = 2,
                stage = "Group Stage",
                group = "Group B",
                lastUpdated = null,
                homeTeam = HomeTeam(),
                awayTeam = AwayTeam(),
                score = Score(),
                goals = mutableListOf(),
                penalties = mutableListOf(),
                bookings = mutableListOf(),
                substitutions = mutableListOf(),
                odds = Odds(),
                referees = mutableListOf()
            )
        )
        return matchesLIst
    }

    /**
     * The function returns a fake match object with various properties initialized.
     *
     * @return The function `getFakeMatch()` returns an instance of the `Matches` class with some
     * default values set for its properties.
     */
    fun getFakeMatch(): Matches {
        val matches = Matches(
            area = Area(),
            competition = Competition(),
            season = Season(),
            id = 2,
            utcDate = "2023-05-27",
            status = "Scheduled",
            minute = null,
            injuryTime = null,
            attendance = null,
            venue = "Stadium XYZ",
            matchday = 2,
            stage = "Group Stage",
            group = "Group B",
            lastUpdated = null,
            homeTeam = HomeTeam(),
            awayTeam = AwayTeam(),
            score = Score(),
            goals = mutableListOf(),
            penalties = mutableListOf(),
            bookings = mutableListOf(),
            substitutions = mutableListOf(),
            odds = Odds(),
            referees = mutableListOf()
        )
        return matches
    }
}