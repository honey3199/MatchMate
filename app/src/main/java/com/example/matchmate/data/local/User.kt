package com.example.matchmate.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.matchmate.data.models.Location
import com.example.matchmate.data.models.Picture
import kotlinx.serialization.SerialName

@Entity(tableName = "user")
data class User(
    @PrimaryKey @ColumnInfo @SerialName("id") val id: String,
    @ColumnInfo @SerialName("email") val email: String,
    @ColumnInfo @SerialName("age") val age: Int,
    @ColumnInfo @SerialName("gender") val gender: String,
    @ColumnInfo @SerialName("location") val location: Location,
    @ColumnInfo @SerialName("name") val name: String,
    @ColumnInfo @SerialName("phone") val phone: String,
    @ColumnInfo @SerialName("picture") val picture: Picture,
    @ColumnInfo @SerialName("profession") var profession: String = "Job",
    @ColumnInfo @SerialName("education") val education: String = "Graduate",
    @ColumnInfo(name = "status") var status: Status = Status.NOT_VIEWED
)

fun User.getDetails(): String {
    return """
        Name       : $name
        Age        : $age
        Gender     : ${gender.capitalize()}
        Profession : $profession
        Education  : $education
        Location   : ${location.city}, ${location.state}, ${location.country}
        Phone      : $phone
    """.trimIndent()
}

enum class Status(val value: String) {
    SELECTED("SELECTED"),
    REJECTED("REJECTED"),
    NOT_VIEWED("not_viewed");

    companion object {
        fun from(value: String): Status = Status.entries.find { it.value == value }
            ?: NOT_VIEWED
    }
}

