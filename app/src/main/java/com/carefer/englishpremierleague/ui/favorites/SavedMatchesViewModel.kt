package com.carefer.englishpremierleague.ui.favorites

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
/* The SavedMatchesViewModel class is a ViewModel that handles retrieving and deleting saved matches
from a repository, and sends events to show an undo message when a match is deleted. */
class SavedMatchesViewModel @Inject constructor(
    private val matchesRepository: MatchesRepository
) : ViewModel() {

    /* `private val savedMatchesEventChannel = Channel<SavedMatchesEvent>()` is creating a channel of
    type `SavedMatchesEvent`. This channel is used to send events from the ViewModel to other parts
    of the code. The `Channel` class is a part of Kotlin coroutines and provides a way to send and
    receive events between coroutines. In this case, the ViewModel sends events to the channel, and
    other parts of the code can receive these events by collecting the flow created from the channel
    using `savedMatchesEventChannel.receiveAsFlow()`. */
    private val savedMatchesEventChannel = Channel<SavedMatchesEvent>()
    /* `val savedMatchesEvent = savedMatchesEventChannel.receiveAsFlow()` is creating a flow from the
    `savedMatchesEventChannel` channel. This flow can be collected by other parts of the code to
    receive events sent by the ViewModel. The `receiveAsFlow()` function is a convenient way to
    create a flow from a channel in Kotlin coroutines. */
    val savedMatchesEvent = savedMatchesEventChannel.receiveAsFlow()

    /**
     * This Kotlin function returns all saved matches from a matches repository.
     */
    fun getAllMatches() = matchesRepository.getAllSavedMatches()

    /**
     * The function deletes a match and sends an event to show an undo message.
     *
     * @param matches The parameter "matches" is an object of type "Matches". It is being passed as an
     * argument to the function "onMatchSwiped". The function is using this object to delete the
     * matches from the repository and then sending an event to show an undo message for the deleted
     * matches.
     */
    fun onMatchSwiped(matches: Matches) {
        viewModelScope.launch {
            matchesRepository.deleteMatches(matches)
            savedMatchesEventChannel.send(SavedMatchesEvent.ShowUndoDeleteMatchMessage(matches))
        }
    }

    /**
     * The function inserts a match into the matches repository when the "undo delete" button is
     * clicked.
     *
     * @param matches The parameter "matches" is an object of type "Matches". It is being passed as an
     * argument to the function "onUndoDeleteClick". The function is using the "viewModelScope.launch"
     * coroutine to insert the "matches" object into the "matchesRepository".
     */
    fun onUndoDeleteClick(matches: Matches) {
        viewModelScope.launch {
            matchesRepository.insertMatch(matches)
        }
    }

    /* The class SavedMatchesEvent is a sealed class with a data class ShowUndoDeleteMatchMessage that
    takes a Matches object as a parameter. */
    sealed class SavedMatchesEvent{
        data class ShowUndoDeleteMatchMessage(val matches: Matches): SavedMatchesEvent()
    }
}