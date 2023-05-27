package com.carefer.englishpremierleague.ui.details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.carefer.englishpremierleague.R
import com.carefer.englishpremierleague.databinding.FragmentMatchDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
/* The MatchDetailsFragment class sets up the UI for a match details screen, accesses and sets values
for various views in a layout using data from a given football match, and handles saving the match
to a ViewModel. */
class MatchDetailsFragment : Fragment(R.layout.fragment_match_details) {

    /* `private val viewModel: MatchDetailsViewModel by viewModels()` is declaring a property
    `viewModel` that is initialized lazily using the `viewModels()` delegate. This property is of
    type `MatchDetailsViewModel`, which is a ViewModel class that contains the business logic for
    the `MatchDetailsFragment`. The `viewModels()` delegate retrieves or creates the ViewModel
    associated with the fragment's view and provides type-safe access to it. This allows the
    fragment to easily access and use the ViewModel to handle data and business logic related to the
    fragment's UI. */
    private val viewModel: MatchDetailsViewModel by viewModels()

    /* `private val args by navArgs<MatchDetailsFragmentArgs>()` is declaring a property `args` that is
    initialized lazily using the `navArgs()` delegate. This property is of type
    `MatchDetailsFragmentArgs`, which is a generated class that contains the arguments passed to the
    `MatchDetailsFragment` fragment. The `navArgs()` delegate retrieves these arguments from the
    fragment's arguments bundle and provides type-safe access to them. This allows the fragment to
    easily retrieve and use the arguments passed to it from the navigation component. */
    private val args by navArgs<MatchDetailsFragmentArgs>()

    /* `private lateinit var binding: FragmentMatchDetailsBinding` is declaring a property `binding` of
    type `FragmentMatchDetailsBinding`. This property is used to access the views in the layout of
    the `MatchDetailsFragment`. The `lateinit` keyword is used to indicate that the property will be
    initialized later, in the `onCreateView()` method, after the layout is inflated. This allows the
    property to be declared as non-null, even though it is not initialized in the constructor. */
    private lateinit var binding: FragmentMatchDetailsBinding

    /**
     * This function inflates the layout for a fragment and returns the root view.
     *
     * @param inflater The LayoutInflater is a class that is used to instantiate layout XML file into
     * its corresponding View objects. It takes an XML file as input and returns a View object. In this
     * code snippet, the LayoutInflater is used to inflate the layout for the MatchDetailsFragment.
     * @param container The `container` parameter is the parent view that the fragment's UI should be
     * attached to. It is a `ViewGroup` that contains the fragment's layout. If the fragment is not
     * attached to any parent view, this parameter will be null.
     * @param savedInstanceState savedInstanceState is a parameter of type Bundle that represents the
     * saved state of the fragment. It is used to restore the state of the fragment in case it is
     * destroyed and recreated by the system, for example, due to a configuration change like screen
     * rotation. The savedInstanceState bundle contains key-value pairs that represent the
     * @return The method is returning a View object, which is the root view of the inflated layout
     * specified by the FragmentMatchDetailsBinding object.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMatchDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * This is a function in Kotlin that sets up the UI for a match details screen and handles saving
     * the match to a ViewModel.
     *
     * @param view The view parameter is the root view of the fragment's layout, which is inflated in
     * the onCreateView() method. It is passed to the onViewCreated() method as a parameter.
     * @param savedInstanceState savedInstanceState is a Bundle object that contains the data that was
     * saved in the onSaveInstanceState() method. This data can be used to restore the state of the
     * fragment when it is recreated.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* `accessViews()` is a function that accesses and sets values for various views in a layout
        using data from a given football match, and also sets a click listener for a floating action
        button to save the match using a view model. It uses the `args` property to retrieve the
        match data passed to the fragment, and then sets the text and color of various views in the
        layout based on the match data. Finally, it sets a click listener for the floating action
        button that calls the `saveMatches()` method of the `MatchDetailsViewModel` to save the
        match. */
        accessViews()

        /* `observeSavedMatchEvent()` is a function that observes a LiveData event in a ViewModel and
        displays a message using Snackbar when the event is triggered. It uses `collect()` to
        observe the `matchEvent` LiveData in the `MatchDetailsViewModel`. When the event is
        triggered, it checks the type of the event and displays a message using Snackbar if it is a
        `ShowMatchSavedMessage` event. This function is called in the `onViewCreated()` method to
        set up the observation of the event. */
        observeSavedMatchEvent()
    }

    /**
     * This function observes a LiveData event in a ViewModel and displays a message using Snackbar
     * when the event is triggered.
     */
    private fun observeSavedMatchEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.matchEvent.collect { event ->
                    when (event) {
                        is MatchDetailsViewModel.MatchEvent.ShowMatchSavedMessage -> {
                            Snackbar.make(requireView(), event.message, Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    /**
     * The function accesses views and sets their values based on data from a match object, and also
     * sets a click listener on a FloatingActionButton to save the match.
     */
    private fun accessViews() {
        binding.apply {
            val match = args.match
            match.let {
                val homeTeamName = it.homeTeam?.name ?: ""
                val awayTeamName = it.awayTeam?.name ?: ""
                val description = getString(R.string.match_description, homeTeamName, awayTeamName)
                tvDescription.text = description
                tvTitle.text = it.competition?.name
                tvPublishedAt.text = it.formattedPublishedAt
                tvSource.text = it.competition?.name

                gameStatus.apply {
                    text = it.status
                    setTextColor(
                        root.context.getColor(
                            when (text) {
                                "FINISHED" -> R.color.green
                                else -> R.color.black
                            }
                        )
                    )
                }
            }

            fab.setOnClickListener {
                viewModel.saveMatches(match)
            }
        }
    }
}