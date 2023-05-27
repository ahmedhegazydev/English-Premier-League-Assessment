package com.carefer.englishpremierleague.di

import android.util.Log
import com.carefer.englishpremierleague.BuildConfig
import com.carefer.englishpremierleague.network.api.MatchesApi
import com.carefer.englishpremierleague.network.api.ApiHelper
import com.carefer.englishpremierleague.network.api.ApiHelperImpl
import com.carefer.englishpremierleague.util.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/* This code is defining a Dagger Hilt module called `ApiModule` that provides singleton instances of
the `HttpLoggingInterceptor`, `OkHttpClient`, `Retrofit`, and `MatchesApi` classes. These classes
are used to handle network requests and responses for an API. The `@Provides` annotation is used to
specify methods that provide instances of these classes, and the `@Singleton` annotation is used to
ensure that only one instance of each class is created and used throughout the application. The
`@InstallIn` annotation is used to specify that the module should be installed in the
`SingletonComponent`, which is a Dagger Hilt component that provides singleton instances of
dependencies. */
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    /* `private const val TAG = "MatchesApp"` is defining a constant string value `TAG` with the value
    "MatchesApp". This value is used as a tag for logging messages in the `HttpLoggingInterceptor`
    instance created in the `provideOkHttpClient()` method. The tag is used to identify the source
    of the log message in the logcat output. */
    private const val TAG = "MatchesApp"

    /* This code is providing a singleton instance of the `OkHttpClient` class, which is used to make
    HTTP requests and handle responses. The `@Provides` annotation specifies that this method
    provides an instance of `OkHttpClient`, and the `@Singleton` annotation ensures that only one
    instance of `OkHttpClient` is created and used throughout the application. The method uses an
    `if` statement to check if the app is in debug mode, and if so, it creates an instance of
    `HttpLoggingInterceptor` to log HTTP requests and responses. It then adds this interceptor to
    the `OkHttpClient.Builder()` and builds the `OkHttpClient`. If the app is not in debug mode, it
    simply builds an `OkHttpClient` without any interceptors. */
    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d(TAG, message)
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()


    /* This code is providing a singleton instance of the `Retrofit` class, which is used to create a
    REST client for making API requests. The `@Provides` annotation specifies that this method
    provides an instance of `Retrofit`, and the `@Singleton` annotation ensures that only one
    instance of `Retrofit` is created and used throughout the application. The method takes an
    instance of `OkHttpClient` as a parameter, which is used to configure the `Retrofit` instance.
    The `Retrofit.Builder()` is used to create a new `Retrofit` instance, and the
    `addConverterFactory()` method is used to specify the converter factory used for serialization
    and deserialization of JSON data. In this case, the `GsonConverterFactory` is used to convert
    JSON data to and from Kotlin objects using the `Gson` library. The `baseUrl()` method is used to
    specify the base URL of the API, and the `client()` method is used to specify the `OkHttpClient`
    instance to use for making requests. Finally, the `build()` method is called to create the
    `Retrofit` instance. */
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .build()

    /* This code is providing a singleton instance of the `MatchesApi` interface, which is used to
    define the endpoints and methods for making API requests. The `@Provides` annotation specifies
    that this method provides an instance of `MatchesApi`, and the `@Singleton` annotation ensures
    that only one instance of `MatchesApi` is created and used throughout the application. The
    /* This code is providing a singleton instance of the `ApiHelper` interface using the
    `ApiHelperImpl` class as the implementation. The `@Provides` annotation specifies that this
    method provides an instance of `ApiHelper`, and the `@Singleton` annotation ensures that only
    one instance of `ApiHelper` is created and used throughout the application. The `ApiHelperImpl`
    instance is passed as a parameter to the method and returned as the singleton instance of
    `ApiHelper`. This allows other classes in the application to use the `ApiHelper` interface to
    make API requests without having to create a new instance of `ApiHelperImpl` each time. */
    method takes an instance of `Retrofit` as a parameter, which is used to create the `MatchesApi`
    instance using the `retrofit.create()` method. This method creates a new implementation of the
    `MatchesApi` interface using the `Retrofit` instance and returns it as a singleton instance. */
    @Provides
    @Singleton
    fun provideMatchesApi(retrofit: Retrofit): MatchesApi = retrofit.create(MatchesApi::class.java)

    /* This code is providing a singleton instance of the `ApiHelper` interface using the
    `ApiHelperImpl` class as the implementation. The `@Provides` annotation specifies that this
    method provides an instance of `ApiHelper`, and the `@Singleton` annotation ensures that only
    one instance of `ApiHelper` is created and used throughout the application. The `ApiHelperImpl`
    instance is passed as a parameter to the method and returned as the singleton instance of
    `ApiHelper`. This allows other classes in the application to use the `ApiHelper` interface to
    make API requests without having to create a new instance of `ApiHelperImpl` each time. */
    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

}