package com.carefer.englishpremierleague.di

import android.app.Application
import androidx.room.Room
import com.carefer.englishpremierleague.data.local.MatchesDatabase
import com.carefer.englishpremierleague.data.local.MatchesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/* This code is a module in Dagger Hilt that provides dependencies for the MatchesDatabase and
MatchesDao classes. It uses the `@Provides` annotation to indicate that the methods provide
dependencies for injection. The `@Singleton` annotation ensures that only one instance of
MatchesDatabase is created and used throughout the application. The `@InstallIn` annotation
specifies that the module should be installed in the `SingletonComponent`, which is the top-level
component in Dagger Hilt. */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /* This code is providing a method to provide an instance of the `MatchesDatabase` class. The
    `@Provides` annotation indicates that this method is used to provide a dependency for injection.
    The method takes an instance of `Application` and a `MatchesDatabase.Callback` as parameters and
    returns an instance of `MatchesDatabase` by calling the `databaseBuilder()` method on the `Room`
    class. This allows other classes to inject an instance of `MatchesDatabase` using dependency
    injection. The `@Singleton` annotation ensures that only one instance of `MatchesDatabase` is
    created and used throughout the application. */
    @Provides
    @Singleton
    fun provideDatabase(
        application: Application,
        callback: MatchesDatabase.Callback
    ): MatchesDatabase {
        return Room.databaseBuilder(application, MatchesDatabase::class.java, "matches_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    /* This code is providing a method to provide an instance of the `MatchesDao` interface. The
    `@Provides` annotation indicates that this method is used to provide a dependency for injection.
    The method takes an instance of `MatchesDatabase` as a parameter and returns an instance of
    `MatchesDao` by calling the `getMatchesDao()` method on the `MatchesDatabase` instance. This
    allows other classes to inject an instance of `MatchesDao` using dependency injection. */
    @Provides
    fun provideMatchesDao(db: MatchesDatabase): MatchesDao {
        return db.getMatchesDao()
    }

}