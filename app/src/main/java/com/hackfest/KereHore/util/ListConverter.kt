package com.hackfest.KereHore.util

import androidx.room.TypeConverter

class ListConverter {
    @TypeConverter
    fun fromList(list: MutableList<String>): String? {
        return list.joinToString(separator = "-")
    }

    @TypeConverter
    fun listFromString(string: String): List<String> {
        return string.split("-")
    }
}