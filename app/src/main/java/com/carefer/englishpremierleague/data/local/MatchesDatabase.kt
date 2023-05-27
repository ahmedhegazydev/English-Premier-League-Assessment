package com.carefer.englishpremierleague.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.carefer.englishpremierleague.data.model.Matches
import javax.inject.Inject

/* This is a Kotlin class that defines a Room database for storing Matches entities and includes a
callback for database operations. */
@Database(
    entities = [
        Matches::class,
    ], version = 8,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MatchesDatabase : RoomDatabase() {

    /**
     * This function returns an instance of the MatchesDao interface.
     */
    abstract fun getMatchesDao(): MatchesDao

    /* This is a Kotlin class that extends RoomDatabase.Callback and is annotated with @Inject. */
    class Callback @Inject constructor() : RoomDatabase.Callback()
}