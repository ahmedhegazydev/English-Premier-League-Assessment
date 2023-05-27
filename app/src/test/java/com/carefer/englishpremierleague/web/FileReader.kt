package com.carefer.englishpremierleague.web

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.carefer.englishpremierleague.EnglishPremierLeagueApplication
import java.io.IOException
import java.io.InputStreamReader

/* This is a Kotlin object that contains a function called `readStringFromFile`. This function reads a
string from a file in the application's assets folder. The function takes a `fileName` parameter,
which is a string that represents the name of the file that needs to be read. The function returns a
`String` that contains the contents of the file with the given `fileName`. The function uses the
`Context` and `InputStream` classes to read the file and the `StringBuilder` and `InputStreamReader`
classes to build the string from the file contents. If an `IOException` occurs while reading the
file, the function throws the exception. */
object FileReader {
    /**
     * The function reads a string from a file in the assets folder of an Android application.
     *
     * @param fileName The parameter `fileName` is a string that represents the name of the file that
     * needs to be read.
     * @return The function `readStringFromFile` returns a `String` that contains the contents of a
     * file with the given `fileName`.
     */
    fun readStringFromFile(fileName: String): String {
        try {
            val context: Context =
                (InstrumentationRegistry.getInstrumentation().context.applicationContext as EnglishPremierLeagueApplication)
            val inputStream = context.assets.open(fileName)
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream, "UTF-8")
            reader.readLines().forEach {
                builder.append(it)
            }
            return builder.toString()
        } catch (e: IOException) {
            throw e
        }
    }
}