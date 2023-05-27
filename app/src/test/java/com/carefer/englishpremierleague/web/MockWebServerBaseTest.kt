package com.carefer.englishpremierleague.web

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.carefer.englishpremierleague.network.api.MatchesApi
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/* The above class is an abstract base class for testing HTTP requests using a mock web server in
Kotlin. */
abstract class MockWebServerBaseTest {

    /* `lateinit var mockServer: MockWebServer` is declaring a mutable property `mockServer` of type
    `MockWebServer` that will be initialized later. The `lateinit` keyword is used to indicate that
    the property will be initialized before it is used, and allows the property to be declared
    without an initial value. */
    lateinit var mockServer: MockWebServer

    @Before
    open fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        this.configureMockServer(context)
    }

    @After
    open fun tearDown() {
        this.stopMockServer()
    }

    /**
     * This is an abstract function in Kotlin that returns a boolean value indicating whether the mock
     * server is enabled or not.
     */
    abstract fun isMockServerEnabled(): Boolean

    /**
     * This function configures a mock server if it is enabled.
     *
     * @param context The `context` parameter is an instance of the `Context` class in Android. It
     * provides access to application-specific resources and classes, as well as information about the
     * application environment. It is commonly used to obtain references to system services, access
     * resources, and launch activities. In this case, it is
     */
    open fun configureMockServer(context: Context) {
        if (isMockServerEnabled()) {
            mockServer = MockWebServer()
            mockServer.start()
        }
    }

    /**
     * The function stops the mock server if it is enabled.
     */
    open fun stopMockServer() {
        if (isMockServerEnabled()) {
            mockServer.shutdown()
        }
    }

    /**
     * This function mocks an HTTP response with a given file name and response code.
     *
     * @param fileName The name of the JSON file that contains the mock response data.
     * @param responseCode The responseCode parameter is an integer value that represents the HTTP
     * status code that the mock server should return in response to the request. For example, a value
     * of 200 represents a successful response, while a value of 404 represents a "not found" error.
     */
    open fun mockHttpResponse(fileName: String, responseCode: Int) =
        mockServer.enqueue(MockResponse().setResponseCode(responseCode).setBody(getJson(fileName)))

    /**
     * This function sets the response code for a mock HTTP response in Kotlin.
     *
     * @param responseCode The responseCode parameter is an integer value that represents the HTTP
     * response code that will be returned by the mock server when a request is made to it. For
     * example, a response code of 200 indicates a successful response, while a response code of 404
     * indicates that the requested resource was not found.
     */
    open fun mockHttpResponse(responseCode: Int) =
        mockServer.enqueue(MockResponse().setResponseCode(responseCode))

    /**
     * This function reads a JSON file from a given path and returns its contents as a string.
     *
     * @param path The `path` parameter is a string that represents the file path of the JSON file that
     * needs to be read.
     * @return a string that represents the contents of a JSON file located at the specified path.
     */
    private fun getJson(path: String): String {
        return FileReader.readStringFromFile(path)
    }

    /**
     * This function provides a test API service using Retrofit and GsonConverterFactory in Kotlin.
     *
     * @return The function `provideTestApiService()` returns an instance of `MatchesApi` which is
     * created using Retrofit library with a base URL set to the URL of a mock server, a
     * `GsonConverterFactory` for JSON serialization and deserialization, and an `OkHttpClient` for
     * making HTTP requests.
     */
    fun provideTestApiService(): MatchesApi {
        return Retrofit.Builder().baseUrl(
            mockServer.url("/")
        ).addConverterFactory(
            GsonConverterFactory.create()
        )
            .client(OkHttpClient.Builder().build()).build().create(MatchesApi::class.java)
    }
}