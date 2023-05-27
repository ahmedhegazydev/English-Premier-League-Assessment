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

    private lateinit var gson: Gson

    @BeforeEach
    fun setUp() {
        // Initialize MockK
        MockKAnnotations.init(this)
        gson = mockk()
    }

    @Test
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