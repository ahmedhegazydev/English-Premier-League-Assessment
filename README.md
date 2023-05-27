# Matches App

The App is using the [Football Api](https://www.football-data.org/documentation/quickstart) to
listing matches.

- Kotlin
- Hilt-Dagger
- Retrofit
- Room
- Coroutines
- LiveData
- ViewModel
- ViewBinding
- Jetpack Navigation
- Glide

### Project Architecture

This app uses **MVVM (Model View View-Model)** architecture.
MVVM provides better separation of concern, easier testing, lifecycle awareness, etc.

## Clean Architecture

Clean architecture promotes separation of concerns, making the code loosely coupled. This results in
a more testable and flexible code. This approach divides the project into 3 modules: presentation,
data, and domain.

* __Presentation__: Layer with the Android Framework, the MVVM pattern and the DI module. Depends on
  the domain to access the use cases and on di, to inject dependencies.
* __Domain__: Layer with the business logic. Contains the use cases, in charge of calling the
  correct repository or data member.
* __Data__: Layer with the responsibility of selecting the proper data source for the domain layer.
  It contains the implementations of the repositories declared in the domain layer. It may, for
  example, check if the data in a database is up to date, and retrieve it from service if it’s not.

## Functionality

The app's functionality includes:

1. Fetch All Available Matches data from https://www.football-data.org/ & show them in `RecylerView`
   with sections by the Match Date.
2. When an item is selected from `RecyclerView` it will load the match details in
   a `MatchDetailsFragment`.
3. From Details view , a new match can be added to Favorite matches - which will store the match in
   the Room database.
4. From favorite matches section users can view all their saved matches, they can also swipe
   left/right to delete the match from local database.

### UI

The UI consists of two parts

1. `View` - Activity screen, Host the navigation component fragments.
2. `Fragment` - Contains three fragments:

   a) `AllMatchesListFragment` - Show for today's matches.

   b) `FavoriteFragment` - Show saved matches.

   c) `DetailsFragment` - Show match details with floating action button for loading & saving matches.

### Model

Model is generated from `JSON` data into a Kotlin data class.
In addition entity class has been added for room database along with `Type converter` for
saving/retrieving custom object data.

### ViewModel

`MatchDetailsViewModel.kt`
`SavedMatchesViewModel.kt`
`ShowALlMatchesListViewModel.kt`

Used for fetching today's matches & update states. Also send out the status of the network call like
Loading, Success, Error using `sealed` class.

### Dependency Injection

The app uses `Dagger-hilt` as a dependency injection library.

The `ApplicationModule.kt` class provides  `Singleton` reference for `Retrofit`, `OkHttpClient`
, `Repository` etc.

### Network

The network layer is composed of Repository, ApiService.
`NewsApi` - Is an interface containing the suspend functions for retrofit API call.

`MatchesRepository` - Holds the definition of the remote/local repository call.

## Building

In-order to successfully run & test the application you will need an `api key`.

Go to - **https://www.football-data.org/**  for obtaining `Api Key`

Now Go to - `app/src/main/java/utils/Constants.kt`

And replace

`const val API_KEY = "YOUR_API_KEY"`

You can open the project in Android studio and press run.

Gradle plugin used in the project will require `Java 11.0` to run.

you can set the gradle jdk in `Preferences->Build Tools->Gradle->Gradle JDK`

## Tech Stack

1. [Android appcompat](https://developer.android.com/jetpack/androidx/releases/appcompat)
   , [KTX](https://developer.android.com/kotlin/ktx)
   , [Constraint layout](https://developer.android.com/reference/androidx/constraintlayout/widget/ConstraintLayout)
   , [Material Support](https://material.io/develop/android/docs/getting-started).
2. [Android View Binding](https://developer.android.com/topic/libraries/view-binding)
3. [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency
   injection.
4. [Retrofit](https://square.github.io/retrofit/) for REST API communication
5. [Coroutine](https://developer.android.com/kotlin/coroutines) for Network call
6. [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle)
   , [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
7. [StateFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/)
8. [Room](https://developer.android.com/jetpack/androidx/releases/room) for local database.
9. [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started)
   for supporting navigation through the app.
10. [Glide](https://github.com/bumptech/glide) for image loading.
11. [Swipe Refresh Layout](https://developer.android.com/jetpack/androidx/releases/swiperefreshlayout)
    for pull-to-refresh `RecyclerView`.
12. [EndlessRecyclerOnScrollListener](https://gist.github.com/rafsanahmad/00214d0f2879884513f8e086754a22e7)
    for Recylerview Infinite Scroll.
13. [Mockito](https://developer.android.com/training/testing/local-tests)
    & [Junit](https://developer.android.com/training/testing/local-tests) for Unit testing.
14. [Robolectric](http://robolectric.org/) for Instrumentation testing.
15. [Truth](https://truth.dev/) for Assertion in testing.
16. [Espresso](https://developer.android.com/training/testing/espresso) for UI testing.

## Testing

Unit testing has been added for `AllMatchesListFragment` & `NewsRepository`.

### `ViewModelTest.kt`

Test the viewmodel of the app using `CoroutineRule` & `Stateflow value`.

The test cases comprise of testing different states like Loading, Success, Error with fake data for
testing Matches Response.

### `MatchesRepositoryTest.kt`

Test the Repository of the app using `Robolectric`.

The test comprises of testing the functionality of Favorite Matches Room Database like Insertion,
Remove, Get saved matches etc.

[Mock Webserver](https://github.com/square/okhttp/tree/master/mockwebserver) is used to test the
Network api response in case of successful data, empty, failed case.

### Project Github Repo

[Repo Here]()