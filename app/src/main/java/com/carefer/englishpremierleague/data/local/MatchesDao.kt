package com.carefer.englishpremierleague.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.carefer.englishpremierleague.data.model.Matches

/* This is a Kotlin DAO interface for performing CRUD operations on a "matches_table" using LiveData
and suspend functions. */
@Dao
interface MatchesDao {

    /**
     * This function retrieves all matches from a database table and returns them as a LiveData object.
     */
    @Query("SELECT * FROM matches_table")
    fun getMatches() : LiveData<List<Matches>>

    /**
     * This Kotlin function inserts a Matches object into a database and replaces any existing data
     * with the same primary key.
     *
     * @param matches `matches` is an object of the `Matches` class that is being inserted into a
     * database using Room Persistence Library. The `@Insert` annotation is used to indicate that this
     * method is used for inserting data into the database. The `onConflict` parameter specifies what
     * should happen if there is a
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(matches: Matches) : Long

    /**
     * This is a suspend function that deletes a match.
     *
     * @param matches The "matches" parameter is an object of the "Matches" class that is being passed
     * to the "delete" function. This parameter likely contains information about a specific match that
     * needs to be deleted, such as the match ID or other identifying information. The function is
     * likely using this parameter to locate and
     */
    @Delete
    suspend fun delete(matches: Matches)

    /**
     * This Kotlin function deletes all matches from a database table.
     */
    @Query("DELETE FROM matches_table")
    suspend fun deleteAllMatches()
}