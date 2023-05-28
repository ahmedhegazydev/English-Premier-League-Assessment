package com.carefer.englishpremierleague.converter

import com.carefer.englishpremierleague.data.local.Converters
import com.carefer.englishpremierleague.data.model.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.BeforeEach

/* The com.carefer.englishpremierleague.converter.ConvertersTest class contains unit tests for the Converters class, which is responsible for
converting JSON strings to objects and vice versa in a soccer application. */
class ConvertersTest {

    /* The above code is declaring a private variable named `gson` of type `Gson` and marking it with
    the `lateinit` modifier, which means that it will be initialized later in the code. The `Gson`
    class is a library for converting JSON strings to Kotlin objects and vice versa. */
    private lateinit var gson: Gson

    @BeforeEach
    /**
     * The function initializes MockK and creates a mock object for gson.
     */
    fun setUp() {
        // Initialize MockK
        MockKAnnotations.init(this)
        gson = mockk()
    }

    @Test
    /**
     * The function tests if a JSON string can be converted to an Area object in Kotlin.
     */
    fun `fromArea should convert JSON string to Area object`() {
        // Arrange
        val json = "{\"id\": 1, \"name\": \"Area 1\"}"
        val expectedArea = Area(1, "Area 1")

        // Act
        val converters = Converters()
        val actualArea = converters.fromArea(json)

        // Assert
        assertEquals(expectedArea, actualArea)
    }

    @Test
    /**
     * The function tests if the `toArea` method in the `Converters` class correctly converts an `Area`
     * object to a JSON string.
     */
    fun `toArea should convert Area object to JSON string`() {
        // Arrange
        val area = Area(1, "Area 1")
        val expectedJson = "{\"id\":1,\"name\":\"Area 1\"}"

        // Act
        val converters = Converters()
        val actualJson = converters.toArea(area)

        // Assert
        assertEquals(expectedJson, actualJson)
    }

    @Test
    /**
     * The function tests if the `fromCompetition` method in the `Converters` class can correctly
     * convert a JSON string to a `Competition` object.
     */
    fun `fromCompetition should convert JSON string to Competition object`() {
        // Arrange
        val jsonString = "{\"id\": 1, \"name\": \"Competition 1\"}"
        val expectedCompetition = Competition(1, "Competition 1")

        // Act
        val converters = Converters()
        val actualCompetition = converters.fromCompetition(jsonString)

        // Assert
        assertEquals(expectedCompetition, actualCompetition)
    }

    @Test
    /**
     * The function tests whether the `toCompetition` method in the `Converters` class can convert a
     * `Competition` object to a JSON string.
     */
    fun `toCompetition should convert Competition object to JSON string`() {
        // Arrange
        val competition = Competition(1, "Competition 1")
        val expectedJsonString = "{\"id\":1,\"name\":\"Competition 1\"}"

        // Act
        val converters = Converters()
        val actualJsonString = converters.toCompetition(competition)

        // Assert
        assertEquals(expectedJsonString, actualJsonString)
    }

    @Test
    /**
     * The function tests if a JSON string can be converted to a Season object in Kotlin.
     */
    fun `fromSeason should convert JSON string to Season object`() {
        // Arrange
        val jsonString = "{\"id\":1,\"startDate\":\"startDate\"," +
                "\"endDate\":\"endDate\"," +
                "\"currentMatchday\":0," +
                "\"winner\":\"winner\"," +
                "\"stages\":[]}"
        val expectedSeason = Season(
            id = 1,
            startDate = "startDate",
            endDate = "endDate",
            currentMatchday = 0,
            winner = "winner",
            stages = mutableListOf()
        )

        // Act
        val converters = Converters()
        val actualSeason = converters.fromSeason(jsonString)

        // Assert
        assertEquals(expectedSeason, actualSeason)
    }

    @Test
    /**
     * The function tests if the `toSeason` method in the `Converters` class correctly converts a
     * `Season` object to a JSON string.
     */
    fun `toSeason should convert Season object to JSON string`() {
        // Arrange
        val season = Season(1, "Season 1", stages = mutableListOf())
        val expectedJsonString = "{\"id\":1,\"startDate\":\"Season 1\",\"stages\":[]}"

        // Act
        val converters = Converters()
        val actualJsonString = converters.toSeason(season)

        // Assert
        assertEquals(expectedJsonString, actualJsonString)
    }

    @Test
    /**
     * The function tests if a JSON string can be converted to a HomeTeam object.
     */
    fun `fromHomeTeam should convert JSON string to HomeTeam object`() {
        // Arrange
        val jsonString = "{\"id\":1,\"name\":\"Home Team 1\"}"
        val expectedHomeTeam = HomeTeam(1, "Home Team 1")

        // Act
        val converters = Converters()
        val actualHomeTeam = converters.fromHomeTeam(jsonString)

        // Assert
        assertEquals(expectedHomeTeam, actualHomeTeam)
    }

    @Test
    /**
     * The function tests whether the `toHomeTeam` method in the `Converters` class correctly converts
     * a `HomeTeam` object to a JSON string.
     */
    fun `toHomeTeam should convert HomeTeam object to JSON string`() {
        // Arrange
        val homeTeam = HomeTeam(
            id = 1,
            name = "Home Team 1",
            coach = Coach(),
            lineup = mutableListOf(),
            bench = mutableListOf()
        )
        val expectedJsonString =
            "{\"id\":1,\"name\":\"Home Team 1\",\"coach\":{},\"lineup\":[],\"bench\":[]}"

        // Act
        val converters = Converters()
        val actualJsonString = converters.toHomeTeam(homeTeam)

        // Assert
        assertEquals(expectedJsonString, actualJsonString)
    }

    @Test
    /**
     * The function tests if a JSON string can be converted to an AwayTeam object.
     */
    fun `fromAwayTeam should convert JSON string to AwayTeam object`() {
        // Arrange
        val jsonString = "{\"id\":1,\"name\":\"Away Team 1\"}"
        val expectedAwayTeam = AwayTeam(1, "Away Team 1")

        // Act
        val converters = Converters()
        val actualAwayTeam = converters.fromAwayTeam(jsonString)

        // Assert
        assertEquals(expectedAwayTeam, actualAwayTeam)
    }

    @Test
    /**
     * The function tests whether the `toAwayTeam` method in the `Converters` class correctly converts
     * an `AwayTeam` object to a JSON string.
     */
    fun `toAwayTeam should convert AwayTeam object to JSON string`() {
        // Arrange
        val awayTeam = AwayTeam(
            id = 1,
            name = "Away Team 1",
            coach = Coach(),
            lineup = mutableListOf(),
            bench = mutableListOf()
        )
        val expectedJsonString =
            "{\"id\":1,\"name\":\"Away Team 1\",\"coach\":{},\"lineup\":[],\"bench\":[]}"

        // Act
        val converters = Converters()
        val actualJsonString = converters.toAwayTeam(awayTeam)

        // Assert
        assertEquals(expectedJsonString, actualJsonString)
    }

    @Test
    /**
     * The function tests if a JSON string can be converted to a Score object using a Converters class.
     */
    fun `fromScore should convert JSON string to Score object`() {
        // Arrange
        val jsonString = "{\"home\": 2, \"away\": 1}"
        val expectedScore = Score(2, 1)

        // Act
        val converters = Converters()
        val actualScore = converters.fromScore(jsonString)

        // Assert
        assertEquals(expectedScore, actualScore)
    }


    @Test
    /**
     * The `toScore` function in Kotlin converts a `Score` object to a JSON string and tests if the
     * conversion is correct.
     */
    fun `toScore should convert Score object to JSON string`() {
        // Arrange
        val score = Score(home = 2, away = 1, fullTime = FullTime(), halfTime = HalfTime())
        val expectedJsonString = "{\"home\":2,\"away\":1,\"fullTime\":{},\"halfTime\":{}}"

        // Act
        val converters = Converters()
        val actualJsonString = converters.toScore(score)

        // Assert
        assertEquals(expectedJsonString, actualJsonString)
    }


    @Test
    /**
     * The function tests whether the `fromGoals` method in the `Converters` class can correctly
     * convert a JSON string to a `Goals` object.
     */
    fun `fromGoals should convert JSON string to Goals object`() {
        // Arrange
        val jsonString = "{\"minute\": 2, \"injuryTime\": injuryTime}"
        val expectedGoals = Goals(2, "injuryTime")

        // Act
        val converters = Converters()
        val actualGoals = converters.fromGoals(jsonString)

        // Assert
        assertEquals(expectedGoals, actualGoals)
    }

    @Test
    /**
     * The function tests if a JSON string can be converted to a Penalties object in Kotlin.
     */
    fun `fromPenalties should convert JSON string to Penalties object`() {
        val jsonString = "{\"player\": {" +
                "\"id\": 1," +
                "\"name\": name" +
                "}, " +
                "\"team\": {" +
                "\"id\": 1," +
                "\"name\": name" +
                "}, " +
                "\"scored\": false}"
        val expectedPenalties =
            Penalties(
                player = Player(id = 1, name = "name"),
                team = Team(id = 1, name = "name"), scored = false
            )

        // Act
        val converters = Converters()
        val actualPenalties = converters.fromPenalties(jsonString)

        // Assert
        assertEquals(expectedPenalties, actualPenalties)
    }

    @Test
    /**
     * The function tests whether the `toPenalties` method in the `Converters` class correctly converts
     * a `Penalties` object to a JSON string.
     */
    fun `toPenalties should convert Penalties object to JSON string`() {
        // Arrange
        val penalties = Penalties(
            player = Player(id = 1, name = "name"),
            team = Team(id = 1, name = "name"), scored = false
        )
//        val expectedJsonString = "{\"home\": 3, \"away\": 2}"
        val expectedJsonString = "{\"player\":{" +
                "\"id\":1," +
                "\"name\":\"name\"" +
                "}," +
                "\"team\":{" +
                "\"id\":1," +
                "\"name\":\"name\"" +
                "}," +
                "\"scored\":false}"
        // Act
        val converters = Converters()
        val actualJsonString = converters.toPenalties(penalties)

        // Assert
        assertEquals(expectedJsonString, actualJsonString)
    }

    @Test
    /**
     * The function tests if a JSON string can be converted to an Odds object in Kotlin.
     */
    fun `fromOdds should convert JSON string to Odds object`() {
        // Arrange
        val jsonString = "{\"homeWin\":2.5,\"draw\":3.0,\"awayWin\":2.8}"
        val expectedOdds = Odds(homeWin = 2.5, draw = 3.0, awayWin = 2.8)

        // Act
        val converters = Converters()
        val actualOdds = converters.fromOdds(jsonString)

        // Assert
        assertEquals(expectedOdds, actualOdds)
    }


    @Test
    /**
     * The function tests whether the `toOdds` method in the `Converters` class correctly converts an
     * `Odds` object to a JSON string.
     */
    fun `toOdds should convert Odds object to JSON string`() {
        // Arrange
        val odds = Odds(homeWin = 2.5, 3.0, awayWin = 2.8)
        val expectedJsonString = "{\"homeWin\":2.5,\"draw\":3.0,\"awayWin\":2.8}"

        // Act
        val converters = Converters()
        val actualJsonString = converters.toOdds(odds)

        // Assert
        assertEquals(expectedJsonString, actualJsonString)
    }

    @Test
    /**
     * The function tests if a JSON string can be converted to a Referees object using a Converters
     * class.
     */
    fun `fromReferees should convert JSON string to List of Referees`() {
        // Arrange
        val jsonString = "{" +
                "\"id\":1," +
                "\"name\": \"John Doe\"," +
                "\"type\": \"type\"," +
                " \"nationality\": \"USA\"}"
        val expectedReferees = Referees(
            id = 1,
            name = "John Doe",
            type = "type",
            nationality = "USA"
        )

        // Act
        val converters = Converters()
        val actualReferees = converters.fromReferees(jsonString)

        // Assert
        assertEquals(expectedReferees, actualReferees)
    }


    @Test
    /**
     * The function tests whether a Kotlin method can convert a list of Referees to a JSON string.
     */
    fun `toReferees should convert List of Referees to JSON string`() {
        // Arrange
        val referees =
            Referees(
                id = 1,
                name = "John Doe",
                type = "type",
                nationality = "USA"
            )
        val expectedJsonString = "{" +
                "\"id\":1," +
                "\"name\":\"John Doe\"," +
                "\"type\":\"type\"," +
                "\"nationality\":\"USA\"}"
        // Act
        val converters = Converters()
        val actualJsonString = converters.toReferees(referees)

        // Assert
        assertEquals(expectedJsonString, actualJsonString)
    }

    @Test
    /**
     * The function tests the conversion of a JSON string to a list of goals using Kotlin.
     */
    fun `fromGoalsList should convert JSON string to List of Goals`() {
        // Arrange
        val jsonString =
            "[{\"minute\": 2, \"injuryTime\": injuryTime},{\"minute\": 2, \"injuryTime\": injuryTime}]"
        val expectedGoals = listOf(
            Goals(2, "injuryTime"),
            Goals(2, "injuryTime")
        )

        // Act
        val converters = Converters()
        val actualGoals = converters.fromGoalsList(jsonString)

        // Assert
        assertEquals(expectedGoals, actualGoals)
    }

    @Test
    /**
     * The function tests whether a Kotlin function can convert a list of goals to a JSON string.
     */
    fun `toGoalsList should convert List of Goals to JSON string`() {
        // Arrange
        val goals = mutableListOf(
            Goals(
                2,
                "injuryTime",
                team = Team(),
                score = Score(fullTime = FullTime(), halfTime = HalfTime()),
            ),
            Goals(
                2,
                "injuryTime",
                team = Team(),
                score = Score(fullTime = FullTime(), halfTime = HalfTime()),
            ),
        )
        val expectedJsonString =
            "[{\"minute\":2,\"injuryTime\":\"injuryTime\",\"team\":{},\"scorer\":{},\"score\":{\"fullTime\":{},\"halfTime\":{}}},{\"minute\":2,\"injuryTime\":\"injuryTime\",\"team\":{},\"scorer\":{},\"score\":{\"fullTime\":{},\"halfTime\":{}}}]"
        // Act
        val converters = Converters()
        val actualJsonString = converters.toGoalsList(goals)

        // Assert
        assertEquals(expectedJsonString, actualJsonString)
    }

    @Test
    /**
     * The function tests the conversion of a JSON string to a list of penalties using Kotlin.
     */
    fun `fromPenaltiesList should convert JSON string to List of Penalties`() {
        // Arrange
        val jsonString = "[" +
                "{\"player\":{" +
                "\"id\":1," +
                "\"name\":\"name\"" +
                "}," +
                "\"team\":{" +
                "\"id\":1," +
                "\"name\":\"name\"" +
                "}," +
                "\"scored\":false}," +
                "{\"player\":{" +
                "\"id\":1," +
                "\"name\":\"name\"" +
                "}," +
                "\"team\":{" +
                "\"id\":1," +
                "\"name\":\"name\"" +
                "}," +
                "\"scored\":false}" +
                "]"
        val expectedPenalties = listOf(
            Penalties(
                player = Player(id = 1, name = "name"),
                team = Team(id = 1, name = "name"), scored = false
            ), Penalties(
                player = Player(id = 1, name = "name"),
                team = Team(id = 1, name = "name"), scored = false
            )
        )

        // Act
        val converters = Converters()
        val actualPenalties = converters.fromPenaltiesList(jsonString)

        // Assert
        assertEquals(expectedPenalties, actualPenalties)
    }

    @Test
    /**
     * The function tests whether a Kotlin function can convert a list of penalties to a JSON string.
     */
    fun `toPenaltiesList should convert List of Penalties to JSON string`() {
        // Arrange
        val penalties = mutableListOf(
            Penalties(
                player = Player(id = 1, name = "name"),
                team = Team(id = 1, name = "name"), scored = false
            ), Penalties(
                player = Player(id = 1, name = "name"),
                team = Team(id = 1, name = "name"), scored = false
            )
        )
        val expectedJsonString = "[" +
                "{\"player\":{" +
                "\"id\":1," +
                "\"name\":\"name\"" +
                "}," +
                "\"team\":{" +
                "\"id\":1," +
                "\"name\":\"name\"" +
                "}," +
                "\"scored\":false}," +
                "{\"player\":{" +
                "\"id\":1," +
                "\"name\":\"name\"" +
                "}," +
                "\"team\":{" +
                "\"id\":1," +
                "\"name\":\"name\"" +
                "}," +
                "\"scored\":false}" +
                "]"
        // Act
        val converters = Converters()
        val actualJsonString = converters.toPenaltiesList(penalties)

        // Assert
        assertEquals(expectedJsonString, actualJsonString)
    }


}