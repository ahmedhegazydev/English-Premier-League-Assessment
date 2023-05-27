package com.carefer.englishpremierleague.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.carefer.englishpremierleague.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_matches.*

@AndroidEntryPoint
/* The `MatchesActivity` class sets up the bottom navigation view with the navigation controller for
the matches fragment in an Android app. */
class MatchesActivity : AppCompatActivity() {

    /**
     * This function sets up the bottom navigation view with the navigation controller for the matches
     * fragment.
     *
     * @param savedInstanceState `savedInstanceState` is a parameter of the `onCreate` method in
     * Android that represents the saved state of the activity. It is a Bundle object that contains
     * data that was saved by the activity before it was destroyed, such as the state of user interface
     * elements or other important data. This parameter is
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matches)

        bottomNavigationView.setupWithNavController(matchesNavHostFragment.findNavController())
    }

}