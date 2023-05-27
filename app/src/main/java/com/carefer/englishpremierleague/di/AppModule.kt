package com.carefer.englishpremierleague.di

import android.content.Context
import com.carefer.englishpremierleague.EnglishPremierLeagueApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

/* This code is defining a Dagger Hilt module named `AppModule` that provides a singleton instance of a
`CoroutineScope` with the `@ApplicationScope` qualifier. The `@Provides` annotation indicates that
this function is used to provide a dependency for injection. The `@Singleton` annotation ensures
that only one instance of the `CoroutineScope` is created and shared throughout the application. The
`CoroutineScope(SupervisorJob())` creates a new `CoroutineScope` with a `SupervisorJob`, which
allows child coroutines to continue running even if one of them fails. The
`@InstallIn(SingletonComponent::class)` annotation specifies that this module should be installed in
the `SingletonComponent`, which is the top-level component in the Hilt dependency injection system. */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): EnglishPremierLeagueApplication {
        return app as EnglishPremierLeagueApplication
    }


    /* This code is providing a singleton instance of a CoroutineScope with the `@ApplicationScope`
    qualifier. The `@Provides` annotation indicates that this function is used to provide a
    dependency for injection. The `@Singleton` annotation ensures that only one instance of the
    CoroutineScope is created and shared throughout the application. The
    `CoroutineScope(SupervisorJob())` creates a new CoroutineScope with a SupervisorJob, which
    allows child coroutines to continue running even if one of them fails. */
    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }
}

/* `@Retention(AnnotationRetention.RUNTIME)` is an annotation that specifies the retention policy for
the `ApplicationScope` annotation. In this case, it indicates that the annotation should be retained
at runtime, which means that it will be available for reflection at runtime. */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope

