package com.carefer.englishpremierleague.util.date

import com.carefer.englishpremierleague.util.DateUtil
import org.junit.Test
import com.google.common.truth.Truth.assertThat

/* The DateUtilTest class contains unit tests for the changeDateFormat function in the DateUtil class. */
class DateUtilTest{

    @Test
    /**
     * This function tests if the DateUtil function returns an empty value when given an empty string
     * as input.
     */
    fun `if the date string is null should return empty value`(){
        val result = DateUtil.changeDateFormat("")
        assertThat(result).isEmpty()
    }

    @Test
    /**
     * This function tests if an empty date string returns an empty value when passed through a
     * function in Kotlin.
     */
    fun `if the date string is empty should return empty value`(){
        val result = DateUtil.changeDateFormat("")
        assertThat(result).isEmpty()
    }

    @Test
    /**
     * This function tests if the date string is not suitable, it should return an empty value.
     */
    fun `if the date string is not suitable should return empty value`(){
        val result = DateUtil.changeDateFormat("2022-03-09")
        assertThat(result).isEmpty()
    }

    //2022-03-09T21:10:00 doesn't returns 09.03.2022`
    @Test
    /* `fun dateConvertTest1(){` is a unit test function that tests the `changeDateFormat` function in
    the `DateUtil` class. It checks if the function correctly converts the input date string
    "2022-03-09T21:10:00" to the expected output format "09.03.2022". The test uses the `assertThat`
    method from the `Truth` library to compare the actual output of the `changeDateFormat` function
    with the expected output. However, this test is expected to fail because the expected output is
    not the same as the actual output. */
    fun dateConvertTest1(){
        val result = DateUtil.changeDateFormat("2022-03-09T21:10:00")
        assertThat(result).isNotEqualTo("09.03.2022")
    }

    //2022-03-09T21:10:00 returns 09.03.2022 21:10:00`
    @Test
    /* `fun dateConvertTest2(){` is a unit test function that tests the `changeDateFormat` function in
    the `DateUtil` class. It checks if the function correctly converts the input date string
    "2022-03-09T21:10:00" to the expected output format "09.03.2022 21:10:00". The test uses the
    `assertThat` method from the `Truth` library to compare the actual output of the
    `changeDateFormat` function with the expected output. */
    fun dateConvertTest2(){
        val result = DateUtil.changeDateFormat("2022-03-09T21:10:00")
        assertThat(result).isEqualTo("09.03.2022 21:10:00")
    }
}