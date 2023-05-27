package com.carefer.englishpremierleague.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.carefer.englishpremierleague.ui.adapter.MatchesAdapter
import com.carefer.englishpremierleague.ui.adapter.ListItem
import com.carefer.englishpremierleague.ui.adapter.ResultType
import com.carefer.englishpremierleague.data.model.Matches
import com.carefer.englishpremierleague.R
import com.carefer.englishpremierleague.databinding.FragmentMatchDetailsBinding
import com.carefer.englishpremierleague.databinding.FragmentSavedMatchesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/* The `SavedMatchesFragment` class displays a list of saved matches and allows the user to delete them
or navigate to the details of a selected match. */
@AndroidEntryPoint
class SavedMatchesFragment : Fragment(R.layout.fragment_saved_matches),
    MatchesAdapter.OnItemClickListener {

    /* `private val viewModel: SavedMatchesViewModel by viewModels()` is creating an instance of the
    `SavedMatchesViewModel` class using the `viewModels()` function provided by the
    `androidx.fragment.app` library. This instance is scoped to the `SavedMatchesFragment` and can
    be used to access the data and business logic of the `SavedMatchesViewModel`. The `by` keyword
    is used for property delegation, which means that the `viewModel` property is delegated to the
    `viewModels()` function to handle its creation and lifecycle management. */
    private val viewModel: SavedMatchesViewModel by viewModels()

    /* `private lateinit var matchesAdapter: MatchesAdapter` is declaring a private property
    `matchesAdapter` of type `MatchesAdapter` that is initialized later in the code. It is used to
    set up a RecyclerView with a MatchesAdapter and to update the adapter with a list of saved
    matches. The `lateinit` keyword is used to indicate that the property will be initialized later
    in the code and to avoid a null pointer exception when accessing it before initialization. */
    private lateinit var matchesAdapter: MatchesAdapter

    /* `private lateinit var binding: FragmentSavedMatchesBinding` is declaring a private property
    `binding` of type `FragmentSavedMatchesBinding`. It is used to access the views in the
    corresponding layout file for the saved matches fragment using view binding. The `lateinit`
    keyword is used to indicate that the property will be initialized later in the code and to avoid
    a null pointer exception when accessing it before initialization. */
    private lateinit var binding: FragmentSavedMatchesBinding

    /**
     * This function inflates the layout for a fragment and returns its root view.
     *
     * @param inflater An object that can be used to inflate any views in the fragment. It takes a
     * layout resource ID and a ViewGroup container as input and returns the inflated view hierarchy.
     * @param container The `container` parameter is the parent view that the fragment's UI should be
     * attached to, or `null` if there is no parent view. It is typically used in conjunction with the
     * `attachToRoot` parameter of the `inflate` method to determine whether the inflated view should
     * be attached to
     * @param savedInstanceState The `savedInstanceState` parameter is a `Bundle` object that contains
     * the saved state of the fragment. This can be used to restore the state of the fragment when it
     * is recreated, for example, after a configuration change like a screen rotation.
     * @return The method is returning a View object, which is the root view of the inflated layout
     * specified by the FragmentSavedMatchesBinding object.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedMatchesBinding.inflate(inflater, container, false)
        return binding.root
    }


    /**
     * This function sets up a RecyclerView with a MatchesAdapter and ItemTouchHelper, observes a
     * LiveData list of matches, and handles events related to deleting and undoing deleted matches.
     *
     * @param view The root view of the fragment's layout.
     * @param savedInstanceState `savedInstanceState` is a parameter of the `onViewCreated` method in
     * Android that represents the saved state of the fragment. It is a `Bundle` object that contains
     * data that was saved by the fragment in a previous state, such as when the fragment was destroyed
     * and recreated due to a configuration
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* `setupRecyclerView()` is a function that sets up a RecyclerView with a MatchesAdapter and
        adds a swipe-to-delete functionality using ItemTouchHelper. It initializes the
        `matchesAdapter` property with a new instance of `MatchesAdapter` and sets it as the adapter
        for the `rvSavedMatches` RecyclerView. It also sets the `ItemTouchHelper` for the
        RecyclerView to enable swipe-to-delete functionality. The `onSwiped` function of the
        `ItemTouchHelper` triggers a ViewModel function with the corresponding data when an item is
        swiped. */
        setupRecyclerView()

        /* `observeSavedMatches()` is a function that observes a LiveData list of saved matches and
        updates the `matchesAdapter` with the list of matches. It creates a mutable list of
        `ListItem` objects and adds each saved match to the list as a `ListItem.Item` object with a
        `ResultType.RESULT` type. It then submits the list to the `matchesAdapter` using the
        `submitList()` function. */
        observeSavedMatches()

        /* `observeMatchDeletedEvent()` is a function that observes a LiveData event in a ViewModel and
        displays a Snackbar message with an undo option when the event is triggered. It uses
        `collect` to observe the `savedArticleEvent` LiveData in the `SavedMatchesViewModel`. When
        the event is triggered, it shows a Snackbar message with the text "Matches Deleted!" and an
        undo button. If the user clicks the undo button, it triggers a ViewModel function to undo
        the deletion of the match. */
        observeMatchDeletedEvent()
    }

    /**
     * This function observes saved matches and updates the matches adapter with the list of matches.
     */
    private fun observeSavedMatches() {
        viewModel.getAllMatches().observe(viewLifecycleOwner) {

            val itemList = mutableListOf<ListItem>()

            it.forEach { match ->
                itemList.add(
                    ListItem.Item(match, ResultType.RESULT)
                )
            }

            matchesAdapter.submitList(itemList)
        }
    }

    /**
     * This function sets up a RecyclerView with a MatchesAdapter and adds a swipe-to-delete
     * functionality using ItemTouchHelper.
     */
    private fun setupRecyclerView() {
        matchesAdapter = MatchesAdapter(this)
        binding.apply {
            rvSavedMatches.apply {
                adapter = matchesAdapter
                setHasFixedSize(true)
            }

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
            ) {
                /**
                 * The onMove function returns false when a view holder is moved in a RecyclerView.
                 *
                 * @param recyclerView The RecyclerView instance on which the item is being moved.
                 * @param viewHolder The viewHolder parameter in the onMove() method is the
                 * RecyclerView.ViewHolder object of the item being moved. It represents the current
                 * position of the item being dragged.
                 * @param target The `target` parameter in the `onMove` method of a
                 * `RecyclerView.ItemTouchHelper.Callback` is the `ViewHolder` that is being dragged or
                 * moved to a new position. It represents the item that the user is currently
                 * interacting with and trying to move to a new position within the `RecyclerView
                 * @return The `onMove` function is returning a boolean value of `false`.
                 */
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                /**
                 * This function handles swiping on a RecyclerView item and triggers a ViewModel
                 * function with the corresponding data.
                 *
                 * @param viewHolder `viewHolder` is an instance of `RecyclerView.ViewHolder` class
                 * which represents the view of the item that was swiped in a `RecyclerView`. It
                 * contains information about the view and its position in the adapter.
                 * @param direction The `direction` parameter in the `onSwiped` function represents the
                 * direction in which the user swiped the item in the `RecyclerView`. It can have one
                 * of the following values:
                 */
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val matches =
                        (matchesAdapter.currentList[viewHolder.adapterPosition] as ListItem.Item).item

                    viewModel.onMatchSwiped(matches)
                }
            }).attachToRecyclerView(rvSavedMatches)
        }
    }

    /**
     * This function observes a LiveData event in a ViewModel and displays a Snackbar message with an
     * undo option when the event is triggered.
     */
    private fun observeMatchDeletedEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.savedArticleEvent.collect { event ->
                    when (event) {
                        is SavedMatchesViewModel.SavedMatchesEvent.ShowUndoDeleteMatchMessage -> {
                            Snackbar.make(requireView(), "Matches Deleted!", Snackbar.LENGTH_LONG)
                                .setAction("UNDO") {
                                    viewModel.onUndoDeleteClick(event.matches)
                                }.show()
                        }
                    }
                }
            }
        }
    }

    /**
     * This function navigates to the MatchDetailsFragment when an item is clicked in the
     * SavedMatchesFragment.
     *
     * @param matches `matches` is an object of the `Matches` class, which is passed as a parameter to
     * the `onItemClick` function. It is used to navigate to the `MatchDetailsFragment` with the
     * selected `Matches` object.
     */
    override fun onItemClick(matches: Matches) {
        val action =
            SavedMatchesFragmentDirections.actionSavedMatchesFragmentToMatchDetailsFragment(matches)
        findNavController().navigate(action)
    }
}