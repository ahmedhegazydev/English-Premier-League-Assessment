package com.carefer.englishpremierleague.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.carefer.englishpremierleague.util.DateUtil
import kotlinx.android.parcel.Parcelize


/**
 * The MatchesResponse data class represents the response received from an API call for a list of
 * matches.
 * @property {Filters?} filters - It is an object of the Filters class which contains various filters
 * that can be applied to the matches data.
 * @property {ResultSet?} resultSet - The `resultSet` property is of type `ResultSet` and contains
 * information about the matches returned in the response. It may include details such as the total
 * number of matches, the current page number, and the number of matches per page.
 * @property {Competition?} competition - The `competition` property is an instance of the
 * `Competition` class, which contains information about the competition that the matches belong to.
 * This could include the name of the competition, the season, the start and end dates, and other
 * relevant details.
 * @property {MutableList<Matches>} matches - `matches` is an MutableList of `Matches` objects. It contains
 * the list of matches returned in the API response. Each `Matches` object represents a single match
 * and contains information such as the match ID, date and time of the match, the teams playing, and
 * the score.
 */
data class MatchesResponse(

    @SerializedName("filters") var filters: Filters? = Filters(),
    @SerializedName("resultSet") var resultSet: ResultSet? = ResultSet(),
    @SerializedName("competition") var competition: Competition? = Competition(),
    @SerializedName("matches") var matches: MutableList<Matches> = arrayListOf()

)

/**
 * This is a Kotlin data class representing filters for a sports match, including season and matchday.
 * @property {String?} season - The season property is a String variable that represents the season of
 * a sports league or competition. It could be the year or a specific name assigned to a season.
 * @property {String?} matchday - `matchday` is a property of the `Filters` data class. It is a
 * nullable `String` variable that represents the matchday filter for a sports event. It can be used to
 * filter the events based on the specific matchday.
 */
@Parcelize
data class Filters(

    @SerializedName("season") var season: String? = null,
    @SerializedName("matchday") var matchday: String? = null

): Parcelable

/**
 * This is a Kotlin data class representing a result set with count, first, last, and played
 * properties, which can be serialized and deserialized using Gson.
 * @property {Int?} count - An integer value representing the total count of items in the result set.
 * @property {String?} first - The "first" property is a String that represents the first name of a
 * person or entity in a ResultSet object. It is annotated with @SerializedName("first") to indicate
 * that it should be mapped to the "first" field in the JSON representation of the object. The "?"
 * after the String type indicates
 * @property {String?} last - The "last" property is a String variable that represents the last name of
 * a person or entity. It is part of a data class called "ResultSet" that also contains other
 * properties such as "count", "first", and "played". The "@Parcelize" annotation is used to
 * automatically generate the
 * @property {Int?} played - The "played" property is an integer that represents the number of times
 * the result set has been played. It is annotated with @SerializedName("played") to indicate that it
 * should be deserialized from JSON using the "played" key. The @Parcelize annotation is used to
 * generate the necessary code for
 */
@Parcelize
data class ResultSet(

    @SerializedName("count") var count: Int? = null,
    @SerializedName("first") var first: String? = null,
    @SerializedName("last") var last: String? = null,
    @SerializedName("played") var played: Int? = null

): Parcelable

/**
 * This is a Kotlin data class representing a competition with properties such as id, name, code, type,
 * and emblem, which can be serialized and deserialized using Gson.
 * @property {Int?} id - An integer representing the unique identifier of the competition.
 * @property {String?} name - The name of the competition.
 * @property {String?} code - The "code" property is a String that represents a code associated with
 * the competition. It could be a unique identifier or a code used for classification purposes.
 * @property {String?} type - The "type" property in the Competition data class is a String that
 * represents the type of the competition, such as "League", "Cup", "Friendly", etc.
 * @property {String?} emblem - The "emblem" property is a string that represents the URL or path to
 * the image file of the competition's emblem or logo.
 */
@Parcelize
data class Competition(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("code") var code: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("emblem") var emblem: String? = null

): Parcelable

/**
 * This is a Kotlin data class representing an area with various properties such as id, name, code,
 * flag, and ensignUrl.
 * @property {Int?} id - An integer value representing the unique identifier of the area.
 * @property {String?} name - The name of the area.
 * @property {String?} code - The "code" property in the "Area" data class is a String variable that
 * represents the code of the area. It is annotated with "@SerializedName" to specify the name of the
 * corresponding JSON field during serialization and deserialization.
 * @property {String?} flag - The "flag" property in the "Area" data class is a string that represents
 * the URL of the flag image associated with the area.
 * @property {String?} ensignUrl - The `ensignUrl` property is a string that represents the URL of the
 * flag image for the area. It is annotated with `@SerializedName("ensignUrl")` to indicate that it
 * should be deserialized from JSON using the "ensignUrl" key. The `@Parcelize`
 */
@Parcelize
data class Area(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("code") var code: String? = null,
    @SerializedName("flag") var flag: String? = null,
    @SerializedName("ensignUrl") var ensignUrl: String? = null,

): Parcelable

/**
 * This is a Kotlin data class representing a season in a sports league, with properties such as start
 * and end dates, current matchday, winner, and stages.
 * @property {Int?} id - The ID of the season.
 * @property {String?} startDate - The start date of the season.
 * @property {String?} endDate - The "endDate" property in the "Season" data class represents the date
 * when the season ends. It is of type String and is annotated with "@SerializedName" to specify the
 * name of the corresponding JSON property during serialization and deserialization.
 * @property {Int?} currentMatchday - The "currentMatchday" property is an integer that represents the
 * current matchday of the season. It is used in sports leagues to keep track of the progress of the
 * season and to determine which teams are playing against each other on a given day.
 * @property {String?} winner - The "winner" property in the Season data class represents the name of
 * the team that won the season. It is of type String and is annotated with @SerializedName("winner")
 * to indicate that it should be mapped to the "winner" field in the JSON data when deserializing.
 * @property {MutableList<String>} stages - `stages` is a property of the `Season` data class that is an
 * `MutableList` of `String` objects. It is annotated with `@SerializedName("stages")` which means that
 * it will be serialized and deserialized using the JSON key "stages". This property represents the
 */
@Parcelize
data class Season(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("startDate") var startDate: String? = null,
    @SerializedName("endDate") var endDate: String? = null,
    @SerializedName("currentMatchday") var currentMatchday: Int? = null,
    @SerializedName("winner") var winner: String? = null,
    @SerializedName("stages") var stages: MutableList<String> = arrayListOf()

): Parcelable

/**
 * This is a data class called Coach that implements Parcelable and contains properties for id, name,
 * and nationality, all of which are serialized using the @SerializedName annotation.
 * @property {Int?} id - An integer variable that represents the unique identifier of a coach.
 * @property {String?} name - The name of a coach.
 * @property {String?} nationality - The "nationality" property is a String that represents the country
 * of origin or citizenship of the coach.
 */
@Parcelize
data class Coach(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("nationality") var nationality: String? = null

): Parcelable

/**
 * This is a data class representing a home team in a sports league, with various properties such as
 * ID, name, coach, lineup, and bench.
 * @property {Int?} id - An integer representing the ID of the home team.
 * @property {String?} name - The name of the home team.
 * @property {String?} shortName - The short name of the home team.
 * @property {String?} tla - TLA stands for "Three Letter Abbreviation". It is a unique three-letter
 * code assigned to each football club by the International Football Association Board (IFAB) and is
 * used to identify the club in various official documents and systems.
 * @property {String?} crest - The crest property is a String that represents the URL or file path of
 * the home team's crest or logo.
 * @property {Coach?} coach - The `coach` property is an instance of the `Coach` data class, which
 * contains information about the coach of the home team. It is annotated with `@SerializedName` to
 * specify the name of the corresponding JSON field when deserializing data from an API response.
 * @property {Int?} leagueRank - An integer representing the current rank of the home team in their
 * league standings.
 * @property {String?} formation - The formation of the home team in a soccer match. It is a string
 * value.
 * @property {MutableList<String>} lineup - An MutableList of String type that represents the starting
 * lineup of the home team.
 * @property {MutableList<String>} bench - `bench` is a property of the `HomeTeam` data class that
 * represents the list of players who are on the bench for the team. It is an `MutableList` of `String`
 * type.
 */
@Parcelize
data class HomeTeam(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("shortName") var shortName: String? = null,
    @SerializedName("tla") var tla: String? = null,
    @SerializedName("crest") var crest: String? = null,
    @SerializedName("coach") var coach: Coach? = Coach(),
    @SerializedName("leagueRank") var leagueRank: Int? = null,
    @SerializedName("formation") var formation: String? = null,
    @SerializedName("lineup") var lineup: MutableList<String> = arrayListOf(),
    @SerializedName("bench") var bench: MutableList<String> = arrayListOf()

): Parcelable

/**
 * This is a Kotlin data class representing a full-time score in a sports match, with home and away
 * scores as nullable integer properties.
 * @property {Int?} home - The "home" property is an integer that represents the score of the home team
 * in a sports game. It is annotated with @SerializedName("home") to indicate that it should be
 * serialized and deserialized using the JSON key "home". The "?" after the type "Int" indicates that
 * it is nullable
 * @property {Int?} away - `away` is a property of the `FullTime` data class that represents the number
 * of goals scored by the away team in a football match. It is of type `Int?`, which means it can be
 * null. The `@SerializedName` annotation is used to specify the name of the JSON
 */
@Parcelize
data class FullTime(

    @SerializedName("home") var home: Int? = null,
    @SerializedName("away") var away: Int? = null

): Parcelable

/**
 * This is a Kotlin data class representing the half-time scores of a sports match, with properties for
 * the home and away teams' scores.
 * @property {Int?} home - The "home" property is an integer value representing the number of goals
 * scored by the home team during the first half of a soccer match.
 * @property {Int?} away - `away` is a property of the `HalfTime` data class. It is of type `Int?` and
 * is annotated with `@SerializedName("away")`, which means that it will be serialized and deserialized
 * using the JSON key "away". This property represents the number of goals scored by
 */
@Parcelize
data class HalfTime(

    @SerializedName("home") var home: Int? = null,
    @SerializedName("away") var away: Int? = null

): Parcelable

/**
 * The above code defines a data class Score with properties related to a sports match score.
 * @property {Int?} home - The number of goals scored by the home team in a match.
 * @property {Int?} away - The `away` property is a nullable integer that represents the number of
 * goals scored by the away team in a match. It is part of the `Score` data class that also contains
 * other properties such as `home` (number of goals scored by the home team), `winner` (the team
 * @property {String?} winner - The "winner" property in the "Score" data class represents the team
 * that won the match. It is a string value that can be either "home", "away", or "draw" (if the match
 * ended in a tie).
 * @property {String?} duration - The duration property represents the length of the game, typically in
 * minutes. It can be used to indicate whether the game was a regular time game or if it went into
 * extra time or penalties. The value of this property is usually a string, such as "90" or "120+".
 * @property {FullTime?} fullTime - `fullTime` is an object of the `FullTime` class. It is a property
 * of the `Score` class and is annotated with `@SerializedName("fullTime")` to indicate that it should
 * be serialized/deserialized with the JSON key "fullTime". The `FullTime`
 * @property {HalfTime?} halfTime - `halfTime` is a property of the `Score` data class that represents
 * the score of the match at half-time. It is an instance of the `HalfTime` data class, which contains
 * two properties: `home` and `away`, representing the scores of the home and away teams respectively
 */
@Parcelize
data class Score(

    @SerializedName("home") var home: Int? = null,
    @SerializedName("away") var away: Int? = null,
    @SerializedName("winner") var winner: String? = null,
    @SerializedName("duration") var duration: String? = null,
    @SerializedName("fullTime") var fullTime: FullTime? = FullTime(),
    @SerializedName("halfTime") var halfTime: HalfTime? = HalfTime()

): Parcelable

/**
 * The above code defines a data class called "Team" with two properties, "id" and "name", that can be
 * serialized and deserialized using the Parcelize and SerializedName annotations.
 * @property {Int?} id - The id property is an integer variable that represents the unique identifier
 * of a team.
 * @property {String?} name - The "name" property is a variable of type String that represents the name
 * of a team. It is annotated with @SerializedName("name") to indicate that it is mapped to a JSON
 * property with the same name during serialization and deserialization. The @Parcelize annotation is
 * used to generate the necessary code
 */
@Parcelize
data class Team(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,

    ): Parcelable


/**
 * The above code defines a data class named Scorer with two properties, id and name, that are
 * annotated with @SerializedName and implement the Parcelable interface.
 * @property {Int?} id - The "id" property is an integer variable that represents the unique identifier
 * of a Scorer object. It is annotated with @SerializedName("id") to indicate that it should be
 * serialized and deserialized using the "id" key in JSON data.
 * @property {String?} name - The "name" property is a variable of type String that represents the name
 * of a scorer. It is annotated with @SerializedName("name") to indicate that it should be serialized
 * and deserialized using the JSON key "name". The "?" after the String type indicates that it is
 * nullable, meaning it can
 */
@Parcelize
data class Scorer(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null

): Parcelable

/**
 * This is a Kotlin data class representing a goal in a sports match, with various properties such as
 * minute, injury time, type, team, scorer, assist, and score.
 * @property {Int?} minute - The minute in which the goal was scored.
 * @property {String?} injuryTime - The injury time during which the goal was scored. It is a String
 * type and is nullable.
 * @property {String?} type - The "type" property in the "Goals" data class represents the type of goal
 * scored, such as a penalty, free kick, or regular goal.
 * @property {Team?} team - The "team" property is an instance of the "Team" class, which contains
 * information about the team that scored the goal. It is annotated with "@SerializedName" to specify
 * the name of the corresponding field in the JSON response.
 * @property {Scorer?} scorer - The "scorer" property is a reference to the player who scored the goal.
 * It is of type "Scorer", which is likely another data class that contains information about the
 * player such as their name, number, and position.
 * @property {String?} assist - The player who provided the assist for the goal.
 * @property {Score?} score - The "score" property is of type "Score" and is annotated with
 * "@SerializedName("score")". It represents the current score of the game at the time the goal was
 * scored.
 */
@Parcelize
data class Goals(

    @SerializedName("minute") var minute: Int? = null,
    @SerializedName("injuryTime") var injuryTime: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("team") var team: Team? = Team(),
    @SerializedName("scorer") var scorer: Scorer? = Scorer(),
    @SerializedName("assist") var assist: String? = null,
    @SerializedName("score") var score: Score? = Score()

): Parcelable

/**
 * This is a Kotlin data class representing a player with an ID and name, annotated with @Parcelize and
 * @SerializedName.
 * @property {Int?} id - The "id" property is an integer variable that represents the unique identifier
 * of a player. It is annotated with "@SerializedName" to specify the name of the corresponding JSON
 * field when serialized or deserialized. It is also nullable, meaning it can have a null value.
 * @property {String?} name - The name property is a String variable that holds the name of a player.
 * It is annotated with @SerializedName("name") to indicate that it is mapped to a JSON property with
 * the same name. The default value is null, which means that it can be left uninitialized. The
 * @Parcelize annotation is
 */
@Parcelize
data class Player(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null

): Parcelable

/**
 * This is a data class called Penalties that contains information about a player's penalties,
 * including the player's name, team, and whether or not they scored.
 * @property {Player?} player - The "player" property is a nullable variable of type "Player" that is
 * annotated with "@SerializedName" to specify the name of the corresponding JSON field when
 * serialized/deserialized. It is initialized with a default value of a new instance of the "Player"
 * class.
 * @property {Team?} team - The "team" property is a variable of type "Team" which is a custom data
 * class. It is annotated with "@SerializedName" which is used to map the JSON key to the corresponding
 * property in the data class. This property represents the team that received the penalty.
 * @property {Boolean?} scored - The "scored" property is a Boolean variable that indicates whether or
 * not a penalty was scored. It can have two possible values: true or false.
 */
@Parcelize
data class Penalties(

    @SerializedName("player") var player: Player? = Player(),
    @SerializedName("team") var team: Team? = Team(),
    @SerializedName("scored") var scored: Boolean? = null

): Parcelable

/**
 * The Odds data class represents the probabilities of a home win, draw, and away win in a sports
 * match.
 * @property {Double?} homeWin - A nullable Double property representing the odds of the home team
 * winning in a sports match.
 * @property {Double?} draw - `draw` is a property of the `Odds` data class that represents the odds of
 * a draw in a sports match. It is of type `Double` and is annotated with `@SerializedName("draw")` to
 * indicate that it should be deserialized from JSON using the "draw" key
 * @property {Double?} awayWin - `awayWin` is a property of the `Odds` data class that represents the
 * odds of the away team winning in a sports match. It is of type `Double?`, which means it can either
 * hold a `Double` value or be null. The `@SerializedName` annotation is used
 */
@Parcelize
data class Odds(

    @SerializedName("homeWin") var homeWin: Double? = null,
    @SerializedName("draw") var draw: Double? = null,
    @SerializedName("awayWin") var awayWin: Double? = null

): Parcelable

/**
 * This is a Kotlin data class representing referees with properties such as id, name, type, and
 * nationality, and it implements the Parcelable interface.
 * @property {Int?} id - An integer value representing the unique identifier of a referee.
 * @property {String?} name - The name of the referee.
 * @property {String?} type - The "type" property in the "Referees" data class is a String variable
 * that represents the type of the referee. It could be "main", "assistant", or any other type that is
 * relevant to the context of the application.
 * @property {String?} nationality - The "nationality" property is a String that represents the country
 * of origin or citizenship of the referee.
 */
@Parcelize
data class Referees(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("nationality") var nationality: String? = null

): Parcelable

/* The above code is defining a data class named "Matches" which is annotated with "@Parcelize" and
"@Entity(tableName = "matches_table")". It contains properties related to a football match such as
area, competition, season, id, utcDate, status, minute, injuryTime, attendance, venue, matchday,
stage, group, lastUpdated, homeTeam, awayTeam, score, goals, penalties, bookings, substitutions,
odds, and referees. It also has a computed property named "formattedPublishedAt" which returns a
formatted date string using the "lastUpdated" property and a */
@Parcelize
@Entity(tableName = "matches_table")
data class Matches(

    @SerializedName("area") var area: Area? = Area(),
    @SerializedName("competition") var competition: Competition? = Competition(),
    @SerializedName("season") var season: Season? = Season(),

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("utcDate") var utcDate: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("minute") var minute: String? = null,
    @SerializedName("injuryTime") var injuryTime: Int? = null,
    @SerializedName("attendance") var attendance: String? = null,
    @SerializedName("venue") var venue: String? = null,
    @SerializedName("matchday") var matchday: Int? = null,
    @SerializedName("stage") var stage: String? = null,
    @SerializedName("group") var group: String? = null,
    @SerializedName("lastUpdated") var lastUpdated: String? = null,
    @SerializedName("homeTeam") var homeTeam: HomeTeam? = HomeTeam(),
    @SerializedName("awayTeam") var awayTeam: AwayTeam? = AwayTeam(),
    @SerializedName("score") var score: Score? = Score(),
    @SerializedName("goals") var goals: MutableList<Goals> = arrayListOf(),
    @SerializedName("penalties") var penalties: MutableList<Penalties> = arrayListOf(),
    @SerializedName("bookings") var bookings: MutableList<String> = arrayListOf(),
    @SerializedName("substitutions") var substitutions: MutableList<String> = arrayListOf(),
    @SerializedName("odds") var odds: Odds? = Odds(),
    @SerializedName("referees") var referees: MutableList<Referees> = arrayListOf()

) : Parcelable{
    val formattedPublishedAt : String get() {
        lastUpdated?.let {
            return DateUtil.changeDateFormat(lastUpdated)
        }
        return ""
    }
}

/**
 * This is a data class representing an away team in a sports league, with various properties such as
 * name, coach, and lineup.
 * @property {Int?} id - An integer representing the ID of the away team.
 * @property {String?} name - The name of the away team.
 * @property {String?} shortName - The short name of the away team.
 * @property {String?} tla - "tla" stands for "Three Letter Abbreviation". It is a unique three-letter
 * code assigned to each football club by the International Football Association Board (IFAB) and is
 * used to identify the club in various official documents and systems.
 * @property {String?} crest - The crest property is a String that represents the URL or file path of
 * the away team's crest or logo.
 * @property {Coach?} coach - The "coach" property is an instance of the "Coach" data class, which
 * contains information about the coach of the away team.
 * @property {Int?} leagueRank - The ranking of the team in their respective league.
 * @property {String?} formation - The formation property represents the tactical formation used by the
 * away team in a football match. It is a String type property.
 * @property {MutableList<String>} lineup - The lineup property is an MutableList of strings that
 * represents the players in the away team's starting lineup for a match.
 * @property {MutableList<String>} bench - `bench` is a property of the `AwayTeam` data class. It is an
 * `MutableList` of `String` type and represents the list of players who are on the bench for the away
 * team in a match.
 */
@Parcelize
data class AwayTeam(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("shortName") var shortName: String? = null,
    @SerializedName("tla") var tla: String? = null,
    @SerializedName("crest") var crest: String? = null,
    @SerializedName("coach") var coach: Coach? = Coach(),
    @SerializedName("leagueRank") var leagueRank: Int? = null,
    @SerializedName("formation") var formation: String? = null,
    @SerializedName("lineup") var lineup: MutableList<String> = arrayListOf(),
    @SerializedName("bench") var bench: MutableList<String> = arrayListOf()

): Parcelable