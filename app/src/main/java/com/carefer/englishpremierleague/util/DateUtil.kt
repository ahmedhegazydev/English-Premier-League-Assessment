package com.carefer.englishpremierleague.util

import java.text.SimpleDateFormat
import java.util.*

/* The DateUtil class provides a static method to convert a date string from one format to another. */
class DateUtil{

    companion object{
        /**
         * The function converts a date string from "yyyy-MM-dd'T'HH:mm:ss" format to "dd.MM.yyyy
         * HH:mm:ss" format.
         *
         * @param strDate The input date string in the format "yyyy-MM-dd'T'HH:mm:ss".
         * @return The function `changeDateFormat` takes a nullable `String` parameter `strDate` and
         * returns a `String`. If the `strDate` is null or empty, an empty string is returned.
         * Otherwise, the function tries to parse the `strDate` using the source date format
         * "yyyy-MM-dd'T'HH:mm:ss" and then formats it using the required date format "dd.MM.y
         */
        fun changeDateFormat(strDate: String?): String {
            if(strDate.isNullOrEmpty()){
               return ""
            }
            return try{
                val sourceSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                val requiredSdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
                requiredSdf.format(sourceSdf.parse(strDate) as Date)
            }catch (ex: Exception){
                ""
            }
        }
    }
}

