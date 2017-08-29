package com.reggar.todomvp.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.Instant

class ConvertersTest {
    private val converters = Converters()

    @Test
    fun fromTimestamp_returnsCorrectDate() {
        /* Given */
        val expectedDate = Instant.now()
        val timeLong = expectedDate.toEpochMilli()

        /* When */
        val actualDate = converters.fromTimestamp(timeLong)

        /* Then */
        assertThat(actualDate).isEqualTo(actualDate)
    }

    @Test
    fun dateToTimestamp_returnsCorrectTimestamp() {
        /* Given */
        val expectedTime = 1503565350L
        val expectedDate = Instant.ofEpochMilli(expectedTime)

        /* When */
        val actualTime = converters.dateToTimestamp(expectedDate)

        /* Then */
        assertThat(actualTime).isEqualTo(expectedTime)
    }
}