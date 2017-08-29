package com.reggar.todomvp.data

import android.arch.persistence.room.TypeConverter
import org.threeten.bp.Instant

/**
 * A set of [TypeConverter]s for Room
 */
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? = value?.let { Instant.ofEpochMilli(it) }

    @TypeConverter
    fun dateToTimestamp(date: Instant?): Long? = date?.toEpochMilli()
}