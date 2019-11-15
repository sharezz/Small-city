package com.sharezzorama.smallcity.data.entity.converters

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {

    companion object {
        private const val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS"

        @JvmStatic
        @TypeConverter
        fun stringToDate(string: String) = SimpleDateFormat(DATE_TIME_FORMAT).parse(string)

        @JvmStatic
        @TypeConverter
        fun dateToString(date: Date) = SimpleDateFormat(DATE_TIME_FORMAT).format(date)

    }
}