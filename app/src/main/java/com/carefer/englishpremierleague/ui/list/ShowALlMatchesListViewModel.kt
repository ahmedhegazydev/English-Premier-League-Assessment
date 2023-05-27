package com.carefer.englishpremierleague.ui.list

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.carefer.englishpremierleague.data.model.Matches
import com.carefer.englishpremierleague.data.model.MatchesResponse
import com.carefer.englishpremierleague.di.CoroutinesDispatcherProvider
import com.carefer.englishpremierleague.network.repository.MatchesRepository
import com.carefer.englishpremierleague.util.NetworkHelper
import com.carefer.englishpremierleague.state.NetworkState
import com.carefer.englishpremierleague.ui.adapter.ListItem
import com.carefer.englishpremierleague.ui.adapter.ResultType
import com.carefer.englishpremierleague.ui.adapter.SectionHeader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

private const val TAG = "MatchesListViewModel"

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
/* The `ShowAllMatchesListViewModel` class is a ViewModel that handles fetching and processing a list
of all matches, and provides observable state flows for the network state and error messages. */
class ShowALlMatchesListViewModel @Inject constructor(
    private val matchesRepository: MatchesRepository,
    private val networkHelper: NetworkHelper,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider


) : ViewModel() {

    /* This code defines a private mutable state flow `_errorMessage` with an initial value of an empty
    string. It also defines a public immutable state flow `errorMessage` with a getter method that
    returns `_errorMessage`. This allows other classes to observe changes to the value of
    `_errorMessage` without being able to modify it directly. */
    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String>
        get() = _errorMessage

    /* This code defines a private mutable state flow `_itemListStateFlow` with an initial value of an
    empty `NetworkState` object, and a public immutable state flow
    `firstVisibleSectionOfTheListHasToBeCurrentDayOrNext` with a getter method that returns
    `_itemListStateFlow`. The `@RequiresApi(Build.VERSION_CODES.O)` annotation is used to indicate
    that this code requires API level 26 or higher. This state flow is used to provide observable
    state updates for the first visible section of the list of matches, which is based on the
    current day or the next. The `StateFlow` class is used to provide a reactive stream of data that
    can be observed by other classes, and the `MutableStateFlow` class is used to allow the value of
    the state flow to be updated. */
    @RequiresApi(Build.VERSION_CODES.O)
    private val _itemListStateFlow =
        MutableStateFlow<NetworkState<List<ListItem>?>>(NetworkState.Empty())
    val firstVisibleSectionOfTheListHasToBeCurrentDayOrNext: StateFlow<NetworkState<List<ListItem>?>>
        @RequiresApi(Build.VERSION_CODES.O)
        get() = _itemListStateFlow


    @RequiresApi(Build.VERSION_CODES.O)
    /**
     * This function creates a list of items with section headers based on the date of matches, with
     * the first section header being the current day or the next.
     *
     * @param matchesList A list of Matches objects, which contain information about football matches
     * such as the date, time, teams playing, and scores.
     * @return a list of `ListItem`s, which are created based on the input list of `Matches`. The
     * `ListItem`s are sorted by date and grouped by day, with each group starting with a
     * `SectionHeader` and followed by one or more `ListItem`s representing matches. The `ListItem`s
     * can have a `ResultType` of either `RESULT` or `TIME`, depending on
     */
    private fun firstVisibleSectionOfTheListHasToBeCurrentDayOrNext(matchesList: List<Matches>): List<ListItem> {
        val itemList = mutableListOf<ListItem>()
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

        val sortedMatches = matchesList.sortedBy { it.utcDate }
        var currentSectionHeader: SectionHeader? = null

        for (match in sortedMatches) {
            val matchDateTime = LocalDateTime.parse(match.utcDate, dateFormatter)

            // Create a new section header if the date changes
            val matchDate = matchDateTime.toLocalDate()
            if (currentSectionHeader == null || currentSectionHeader.date != matchDate) {
                currentSectionHeader = SectionHeader(matchDate)
                itemList.add(ListItem.GroupHeader(currentSectionHeader))
            }

            // Create the list item for the match
            val listItem = if (matchDateTime.isBefore(LocalDateTime.now())) {
                // Match has already been played, show result
                ListItem.Item(match, ResultType.RESULT)
            } else {
                // Match is in the future, show time
                ListItem.Item(match, ResultType.TIME)
            }
            itemList.add(listItem)
        }

        return itemList
    }

    /* `init` is a special block in Kotlin that is executed when an instance of the class is created.
    In this case, `getAllMatchesListShowing()` function is called inside the `init` block, which in
    turn calls the `safeMatchesListShowingCall()` function to fetch the list of all matches. This
    ensures that the list of all matches is fetched as soon as an instance of the
    `MatchesDetailsViewModel` class is created. */
    init {
        getAllMatchesListShowing()
    }


    /* This code defines a private mutable state flow `_matchesResponse` with an initial value of an
    empty `NetworkState` object. It also defines a public immutable state flow `matchesResponse`
    with a getter method that returns `_matchesResponse`. This allows other classes to observe
    changes to the value of `_matchesResponse` without being able to modify it directly. The
    `matchesResponse` state flow is used to provide observable state updates for the network state
    of the matches response, which can be used to update the UI accordingly. */
    private val _matchesResponse =
        MutableStateFlow<NetworkState<MatchesResponse>>(NetworkState.Empty())
    val matchesResponse: StateFlow<NetworkState<MatchesResponse>>
        get() = _matchesResponse

    /**
     * This function retrieves a list of all matches and handles the response based on the network
     * state.
     */
    fun getAllMatchesListShowing() {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch(coroutinesDispatcherProvider.io) {
                _matchesResponse.value = NetworkState.Loading()
                when (val response = matchesRepository.getAllMatches()) {
                    is NetworkState.Success -> {
                        _matchesResponse.value = handleMatchesResponse(response)
                    }
                    is NetworkState.Error -> {
                        _matchesResponse.value =
                            NetworkState.Error(
                                response.message ?: "Error"
                            )
                    }
                    else -> {}
                }

            }
        } else {
            _errorMessage.value = "No internet available"
        }
    }

    /**
     * The function handles the response of a network call for matches and updates the item list state
     * flow with the first visible section of the list being the current day or next, and returns an
     * error message if no data is found.
     *
     * @param response NetworkState object containing the response data from the API call for matches.
     * It can be either a success or an error state.
     * @return a NetworkState object with an error message "No data found". However, the function also
     * updates the value of a Flow variable (_itemListStateFlow) with the result of the network call
     * (either a success state with the first visible section of the list or an error state).
     */
    private fun handleMatchesResponse(response: NetworkState<MatchesResponse>): NetworkState<MatchesResponse> {
        _matchesResponse.map { _ ->
            val firstVisibleSectionOfTheListHasToBeCurrentDayOrNext =
                response.data?.matches?.let { firstVisibleSectionOfTheListHasToBeCurrentDayOrNext(it) }
            NetworkState.Success(firstVisibleSectionOfTheListHasToBeCurrentDayOrNext)
        }.onEach { state ->
            _itemListStateFlow.value = state
        }.launchIn(viewModelScope)
        return NetworkState.Error("No data found")
    }

    /**
     * The function converts a date string in the format "yyyy-MM-dd'T'HH:mm:ss'Z'" to "MMM dd, yyyy
     * hh:mm a" format, and returns the converted date string or the original string if the conversion
     * fails.
     *
     * @param strCurrentDate The input parameter for the function `formatDate()`. It is a string
     * representing a date in the format "yyyy-MM-dd'T'HH:mm:ss'Z'".
     * @return a string that represents the converted date in the format "MMM dd, yyyy hh:mm a" (e.g.
     * "Jan 01, 2022 12:00 AM") if the input string is not empty and contains the letter "T". If the
     * input string is empty or does not contain "T", the function returns the original input string.
     * If an exception occurs during
     */
    fun formatDate(strCurrentDate: String): String {
        var convertedDate = ""
        try {
            if (strCurrentDate.isNotEmpty() && strCurrentDate.contains("T")) {
                val local = Locale("US")
                var format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", local)
                val newDate: Date? = format.parse(strCurrentDate)

                format = SimpleDateFormat("MMM dd, yyyy hh:mm a", local)
                newDate?.let {
                    convertedDate = format.format(it)
                }
            } else {
                convertedDate = strCurrentDate
            }
        } catch (e: Exception) {
            e.message?.let {
                Log.e(TAG, it)
            }
            convertedDate = strCurrentDate
        }
        return convertedDate
    }


}