package com.carefer.englishpremierleague.ui.list

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.carefer.englishpremierleague.data.model.Matches
import com.carefer.englishpremierleague.R
import com.carefer.englishpremierleague.databinding.FragmentAllMatchesListBinding
import com.carefer.englishpremierleague.ui.adapter.ListItem
import com.carefer.englishpremierleague.ui.adapter.MatchesAdapter
import com.carefer.englishpremierleague.ui.adapter.ResultType
import com.carefer.englishpremierleague.ui.adapter.SectionHeader
import com.carefer.englishpremierleague.state.NetworkState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_all_matches_list.*
import kotlinx.android.synthetic.main.group_header_layout.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val TAG = "AllMatchesListFragment"

/* The `AllMatchesListFragment` class is a Kotlin fragment that displays a list of matches and allows
the user to navigate to the `MatchDetailsFragment` when an item is clicked. */
@AndroidEntryPoint
class AllMatchesListFragment : Fragment(R.layout.fragment_all_matches_list),
    MatchesAdapter.OnItemClickListener {

    /* The above code is declaring a private property `viewModel` of type `ShowAllMatchesListViewModel`
    and initializing it using the `viewModels()` function. This function is typically used in
    Android development with the Jetpack ViewModel library to create a ViewModel instance associated
    with a specific lifecycle owner (such as an Activity or Fragment). The `by` keyword is used to
    delegate the creation of the ViewModel instance to the `viewModels()` function. */
    private val viewModel: ShowALlMatchesListViewModel by viewModels()

    /* The above code is declaring a private variable `matchesAdapter` of type `MatchesAdapter` and
    marking it with the `lateinit` modifier, which means that it will be initialized later in the
    code. This variable is likely to be used to set up a RecyclerView adapter for displaying a list
    of matches in an Android app. */
    private lateinit var matchesAdapter: MatchesAdapter

    /* The above code is declaring a variable named `isLoading` with an initial value of `false`. The
    programming language used is Kotlin. */
    var isLoading = false

    /* `private lateinit var binding: FragmentAllMatchesListBinding` is declaring a private property
    called `binding` of type `FragmentAllMatchesListBinding`. This property is used to access the
    views in the layout file associated with the `AllMatchesListFragment`. The `lateinit` keyword is
    used to indicate that the property will be initialized later, before it is used. This is because
    the property is initialized in the `onCreateView` function, which is called after the
    `onViewCreated` function where the `binding` property is used. */
    private lateinit var binding: FragmentAllMatchesListBinding

    /**
     * This function inflates a layout for a fragment and returns its root view.
     *
     * @param inflater An object that can be used to inflate any views in the fragment. It takes a
     * layout resource ID and returns the corresponding View object.
     * @param container The container parameter is the parent view that the fragment's UI should be
     * attached to. It is a ViewGroup that is typically the layout that contains the fragment. If the
     * fragment is not attached to any parent view, this parameter can be null.
     * @param savedInstanceState savedInstanceState is a parameter of type Bundle that represents the
     * saved state of the fragment. It is used to restore the state of the fragment in case it is
     * destroyed and recreated by the system, for example, due to a configuration change like screen
     * rotation. The savedInstanceState bundle contains key-value pairs that represent the
     * @return The method is returning a View object, which is the root view of the inflated layout
     * specified by the FragmentAllMatchesListBinding object.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllMatchesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    /**
     * The `onViewCreated` function sets up UI elements, a RecyclerView, and observers to handle
     * responses from an API and update the UI accordingly in the `AllMatchesListFragment`.
     *
     * @param view The root view of the fragment associated with this code. It is passed as a parameter
     * to the `onViewCreated` function by the Android framework.
     * @param savedInstanceState `savedInstanceState` is a `Bundle` object that contains the saved
     * state of the fragment. It is used to restore the state of the fragment when it is recreated, for
     * example, when the device is rotated. The saved state can include information such as the current
     * scroll position of the `RecyclerView`
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* `setupUI()` is setting up a swipe refresh listener for the `SwipeRefreshLayout` in the
        layout file associated with the `AllMatchesListFragment`. When the user swipes down on the
        screen, the `onRefresh()` function is called, which triggers the
        `getAllMatchesListShowing()` function in the `viewModel` to fetch the latest data from the
        API. */
        setupUI()

        /* `setupRecyclerView()` is a function that sets up a `RecyclerView` with a `MatchesAdapter` in
        the `AllMatchesListFragment`. It initializes a new `MatchesAdapter` and sets it as the
        adapter for the `RecyclerView` in the layout file associated with the
        `AllMatchesListFragment`. It also sets the `hasFixedSize` property of the `RecyclerView` to
        `true`. */
        setupRecyclerView()

        /* The above code is likely a function call to a method named "setupObservers()" in Kotlin
        programming language. This method is expected to set up observers for some data or events in
        the application. The details of what the observers are monitoring and how they are set up
        are not provided in the given code snippet. */
        setupObservers()

    }

    /**
     * The function sets up a swipe refresh listener for a given UI element in Kotlin.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupUI() {
        //Swipe refresh listener
        val refreshListener = SwipeRefreshLayout.OnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.getAllMatchesListShowing()
        }
        binding.swipeRefreshLayout.setOnRefreshListener(refreshListener)
    }

    /**
     * This function sets up a RecyclerView with a MatchesAdapter in a Kotlin program.
     */
    private fun setupRecyclerView() {
        matchesAdapter = MatchesAdapter(this)
        binding.apply {
            rvAllMatchesList.apply {
                adapter = matchesAdapter
                setHasFixedSize(true)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    /**
     * This function sets up observers to handle responses from a ViewModel and updates the UI
     * accordingly.
     */
    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.firstVisibleSectionOfTheListHasToBeCurrentDayOrNext.collect { response ->
                    when (response) {
                        is NetworkState.Success -> {
                            hideProgressBar()
                            response.data.let {
                                matchesAdapter.submitList(it)
                            }
                        }

                        is NetworkState.Loading -> {
                            showProgressBar()
                        }

                        is NetworkState.Error -> {
                            hideProgressBar()
                            response.message?.let {
                                showErrorMessage(response.message)
                            }
                        }

                        else -> {}
                    }
                }

            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errorMessage.collect { value ->
                    if (value.isNotEmpty()) {
                        Toast.makeText(activity, value, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    /**
     * The function hides a progress bar, displays an error message as a toast, logs the error message,
     * and sets a boolean flag to indicate that the app is no longer loading.
     *
     * @param message The error message that will be displayed to the user and logged in the console.
     */
    private fun showErrorMessage(message: String) {
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = true
        message.let { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Error: $message")
        }
    }

    /**
     * This function shows a progress bar on the screen.
     */
    private fun showProgressBar() {
        isLoading = true
        paginationProgressBar.visibility = View.VISIBLE
    }

    /**
     * This function hides a progress bar and sets a boolean variable to false.
     */
    private fun hideProgressBar() {
        isLoading = false
        paginationProgressBar.visibility = View.INVISIBLE
    }

    /**
     * This function navigates to the MatchDetailsFragment when an item is clicked in the
     * AllMatchesListFragment.
     *
     * @param matches `matches` is an object of the `Matches` class, which contains information about a
     * particular match. This parameter is passed to the `onItemClick` function as an argument when an
     * item in a list of matches is clicked. The function then uses this information to navigate to the
     * `MatchDetailsFragment
     */
    override fun onItemClick(matches: Matches) {
        val action =
            AllMatchesListFragmentDirections.actionAllMatchesListFragmentToMatchDetailsFragment(
                matches
            )
        findNavController().navigate(action)
    }

}