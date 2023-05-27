package com.carefer.englishpremierleague.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carefer.englishpremierleague.data.model.Matches
import com.carefer.englishpremierleague.network.repository.MatchesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
/* This code is defining a ViewModel class called `MatchDetailsViewModel` that is used to handle events
related to a sports match. The class is annotated with `@HiltViewModel`, which is a Hilt annotation
that allows the class to be injected with dependencies using Hilt. The class has a constructor that
takes a `MatchesRepository` object as a parameter, which is injected using Hilt's dependency
injection. The `MatchDetailsViewModel` class extends the `ViewModel` class from the Android
Architecture Components library, which provides a lifecycle-aware way to store and manage UI-related
data. */
class MatchDetailsViewModel @Inject constructor(
    private val matchesRepository: MatchesRepository
) : ViewModel() {

    /* `private val matchEventChannel = Channel<MatchEvent>()` is creating a `Channel` object that is
    used to send and receive events related to a sports match. The `MatchEvent` is a sealed class
    that defines the different types of events that can be sent through the channel. The
    `matchEventChannel` is a private property of the `MatchDetailsViewModel` class and is used to
    send messages to the `matchEvent` flow. The `matchEvent` flow is a public property of the
    `MatchDetailsViewModel` class that can be observed by other classes to receive events related to
    a sports match. */
    private val matchEventChannel = Channel<MatchEvent>()

    /* `val matchEvent = matchEventChannel.receiveAsFlow()` is creating a flow from the
    `matchEventChannel` channel. This flow can be observed by other classes to receive events
    related to a sports match. Whenever a new event is sent through the `matchEventChannel`, it will
    be emitted through the `matchEvent` flow. The `receiveAsFlow()` function is used to convert the
    `matchEventChannel` channel into a flow. */
    val matchEvent = matchEventChannel.receiveAsFlow()

    /**
     * This function saves a match and sends a message indicating that the match has been saved.
     *
     * @param match The parameter `match` is an object of type `Matches` that contains information
     * about a particular match. This object is being passed as an argument to the `insertMatch`
     * function of the `matchesRepository` object, which is responsible for saving the match data to a
     * database. The function is being
     */
    fun saveMatches(match: Matches) {
        viewModelScope.launch {
            matchesRepository.insertMatch(match)
            matchEventChannel.send(MatchEvent.ShowMatchSavedMessage("Match Saved."))
        }
    }

    /* The MatchEvent class is a sealed class in Kotlin that has a single data class called
    ShowMatchSavedMessage which contains a message string. */
    sealed class MatchEvent {
        data class ShowMatchSavedMessage(val message: String) : MatchEvent()
    }
}