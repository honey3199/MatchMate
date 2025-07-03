package com.example.matchmate.data.local

import androidx.room.TypeConverter
import com.example.matchmate.data.models.Location
import com.example.matchmate.data.models.Picture
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun toLocation(value: String?): Location? {
        val gson = Gson()
        return gson.fromJson(value, Location::class.java)
    }

    @TypeConverter
    fun toStringLocation(item: Location?): String? {
        val gson = Gson()
        return gson.toJson(item)
    }

    @TypeConverter
    fun toPicture(value: String?): Picture? {
        val gson = Gson()
        return gson.fromJson(value, Picture::class.java)
    }

    @TypeConverter
    fun toStringPicture(item: Picture?): String? {
        val gson = Gson()
        return gson.toJson(item)
    }

    @TypeConverter
    fun fromStatus(status: Status): String = status.value

    @TypeConverter
    fun toStatus(value: String): Status = Status.from(value)
}