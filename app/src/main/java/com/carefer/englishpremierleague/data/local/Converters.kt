package com.carefer.englishpremierleague.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.carefer.englishpremierleague.data.model.*

/* The Converters class contains methods for converting various data types to and from JSON using the
Gson library, and is used for database operations in Kotlin. */
class Converters {

    /* `private val gson = Gson()` is creating an instance of the Gson class, which is a library used
    for converting Java objects to JSON and vice versa. This instance is used in the various methods
    annotated with `@TypeConverter` to convert objects to and from JSON strings for database
    operations in Kotlin. */
    private val gson = Gson()

    /* This is a method annotated with `@TypeConverter` that converts a JSON string to an object of
    type `Area` using the Gson library. It takes a JSON string as input and returns an `Area`
    object. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun fromArea(json: String): Area {
        return gson.fromJson(json, Area::class.java)
    }

    /* This is a method annotated with `@TypeConverter` that converts an object of type `Area` to a
    JSON string using the Gson library. It takes an `Area` object as input and returns a JSON
    string. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun toArea(field: Area): String {
        return gson.toJson(field)
    }

    /* This is a method annotated with `@TypeConverter` that converts a JSON string to an object of
    type `Competition` using the Gson library. It takes a JSON string as input and returns a
    `Competition` object. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun fromCompetition(json: String): Competition {
        return gson.fromJson(json, Competition::class.java)
    }

    /* This is a method annotated with `@TypeConverter` that converts an object of type `Competition`
    to a JSON string using the Gson library. It takes a `Competition` object as input and returns a
    JSON string. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun toCompetition(field: Competition): String {
        return gson.toJson(field)
    }


    /* This is a method annotated with `@TypeConverter` that converts a JSON string to an object of
    type `Season` using the Gson library. It takes a JSON string as input and returns a `Season`
    object. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun fromSeason(json: String): Season {
    /* This is a method annotated with `@TypeConverter` that converts an object of type `Season` to a
    JSON string using the Gson library. It takes a `Season` object as input and returns a JSON
    string. This method is used for database operations in Kotlin. */
        return gson.fromJson(json, Season::class.java)
    }

    /* This is a method annotated with `@TypeConverter` that converts an object of type `Season` to a
    JSON string using the Gson library. It takes a `Season` object as input and returns a JSON
    string. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun toSeason(field: Season): String {
        return gson.toJson(field)
    }

    /* This is a method annotated with `@TypeConverter` that converts a JSON string to an object of
    type `HomeTeam` using the Gson library. It takes a JSON string as input and returns a `HomeTeam`
    object. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun fromHomeTeam(json: String): HomeTeam {
        return gson.fromJson(json, HomeTeam::class.java)
    }

    /* This is a method annotated with `@TypeConverter` that converts an object of type `HomeTeam` to a
    JSON string using the Gson library. It takes a `HomeTeam` object as input and returns a JSON
    string. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun toHomeTeam(field: HomeTeam): String {
        return gson.toJson(field)
    }

    /* This is a method annotated with `@TypeConverter` that converts a JSON string to an object of
    type `AwayTeam` using the Gson library. It takes a JSON string as input and returns an
    `AwayTeam` object. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun fromAwayTeam(json: String): AwayTeam {
        return gson.fromJson(json, AwayTeam::class.java)
    }

    /* This is a method annotated with `@TypeConverter` that converts an object of type `AwayTeam` to a
    JSON string using the Gson library. It takes an `AwayTeam` object as input and returns a JSON
    string. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun toAwayTeam(field: AwayTeam): String {
        return gson.toJson(field)
    }


    /* This is a method annotated with `@TypeConverter` that converts a JSON string to an object of
    type `Score` using the Gson library. It takes a JSON string as input and returns a `Score`
    object. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun fromScore(json: String): Score {
        return gson.fromJson(json, Score::class.java)
    }

    /* This is a method annotated with `@TypeConverter` that converts an object of type `Score` to a
    JSON string using the Gson library. It takes a `Score` object as input and returns a JSON
    string. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun toScore(field: Score): String {
        return gson.toJson(field)
    }

    /* This is a method annotated with `@TypeConverter` that converts a JSON string to an object of
    type `Goals` using the Gson library. It takes a JSON string as input and returns a `Goals`
    object. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun fromGoals(json: String): Goals {
        return gson.fromJson(json, Goals::class.java)
    }

    /* This is a method annotated with `@TypeConverter` that converts an object of type `Goals` to a
    JSON string using the Gson library. It takes a `Goals` object as input and returns a JSON
    string. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun toGoals(field: Goals): String {
        return gson.toJson(field)
    }

    /* This is a method annotated with `@TypeConverter` that converts a JSON string to an object of
    type `Penalties` using the Gson library. It takes a JSON string as input and returns a
    `Penalties` object. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun fromPenalties(json: String): Penalties {
        return gson.fromJson(json, Penalties::class.java)
    }

    /* This is a method annotated with `@TypeConverter` that converts an object of type `Penalties` to
    a JSON string using the Gson library. It takes a `Penalties` object as input and returns a JSON
    string. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun toPenalties(field: Penalties): String {
        return gson.toJson(field)
    }

    /* This is a method annotated with `@TypeConverter` that converts a JSON string to an object of
    type `Odds` using the Gson library. It takes a JSON string as input and returns an `Odds`
    object. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun fromOdds(json: String): Odds {
        return gson.fromJson(json, Odds::class.java)
    }

    /* This is a method annotated with `@TypeConverter` that converts an object of type `Odds` to a
    JSON string using the Gson library. It takes an `Odds` object as input and returns a JSON
    string. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun toOdds(field: Odds): String {
        return gson.toJson(field)
    }

    /* This is a method annotated with `@TypeConverter` that converts a JSON string to an object of
    type `Referees` using the Gson library. It takes a JSON string as input and returns a `Referees`
    object. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun fromReferees(json: String): Referees {
        return gson.fromJson(json, Referees::class.java)
    }

    /* This is a method annotated with `@TypeConverter` that converts an object of type `Referees` to a
    JSON string using the Gson library. It takes a `Referees` object as input and returns a JSON
    string. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun toReferees(field: Referees): String {
        return gson.toJson(field)
    }

    /* This is a method annotated with `@TypeConverter` that converts a JSON string to an MutableList of
    `Goals` objects using the Gson library. It takes a JSON string as input and returns an MutableList
    of `Goals` objects. This method is used for database operations in Kotlin. It uses a `TypeToken`
    to specify the type of the MutableList. */
    @TypeConverter
    fun fromGoalsList(json: String): MutableList<Goals> {
        val listType = object : TypeToken<MutableList<Goals>>() {}.type
        return gson.fromJson(json, listType)
    }

    /* This is a method annotated with `@TypeConverter` that converts an MutableList of `Goals` objects
    to a JSON string using the Gson library. It takes an MutableList of `Goals` objects as input and
    returns a JSON string. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun toGoalsList(goals: MutableList<Goals>): String {
        return gson.toJson(goals)
    }


    /* This is a method annotated with `@TypeConverter` that converts a JSON string to an MutableList of
    `Penalties` objects using the Gson library. It takes a JSON string as input and returns an
    MutableList of `Penalties` objects. It uses a `TypeToken` to specify the type of the MutableList.
    This method is used for database operations in Kotlin. */
    @TypeConverter
    fun fromPenaltiesList(json: String): MutableList<Penalties> {
        val listType = object : TypeToken<MutableList<Penalties>>() {}.type
        return gson.fromJson(json, listType)
    }

    /* This is a method annotated with `@TypeConverter` that converts an MutableList of `Penalties`
    objects to a JSON string using the Gson library. It takes an MutableList of `Penalties` objects as
    input and returns a JSON string. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun toPenaltiesList(goals: MutableList<Penalties>): String {
        return gson.toJson(goals)
    }


    /* This is a method annotated with `@TypeConverter` that converts a JSON string to an MutableList of
    `String` objects using the Gson library. It takes a JSON string as input and returns an
    MutableList of `String` objects. It uses a `TypeToken` to specify the type of the MutableList. This
    method is used for database operations in Kotlin. */
    @TypeConverter
    fun fromList(json: String): MutableList<String> {
        val listType = object : TypeToken<MutableList<String>>() {}.type
        return gson.fromJson(json, listType)
    }

    /* This is a method annotated with `@TypeConverter` that converts an MutableList of `String` objects
    to a JSON string using the Gson library. It takes an MutableList of `String` objects as input and
    returns a JSON string. This method is used for database operations in Kotlin. */
    @TypeConverter
    fun toList(goals: MutableList<String>): String {
        return gson.toJson(goals)
    }

    /* The above code is a function in Kotlin that is annotated with `@TypeConverter`. It takes a JSON
    string as input and converts it into an MutableList of `Referees` objects using the
    `gson.fromJson()` method. The `TypeToken` class is used to specify the type of the MutableList.
    This function is likely used in a Room database to convert the JSON string stored in the
    database into an MutableList of `Referees` objects when retrieving data. */
    @TypeConverter
    fun fromRefereesList(json: String): MutableList<Referees> {
        val listType = object : TypeToken<MutableList<Referees>>() {}.type
        return gson.fromJson(json, listType)
    }

    /* The above code is a function written in Kotlin that converts an MutableList of Referees objects
    into a JSON string using the Gson library. It is annotated with `@TypeConverter` which indicates
    that it is used for type conversion in Room database. */
    @TypeConverter
    fun toRefereesList(goals: MutableList<Referees>): String {
        return gson.toJson(goals)
    }


}