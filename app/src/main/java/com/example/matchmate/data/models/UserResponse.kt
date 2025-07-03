package com.example.matchmate.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("results")
    val results: List<Results>
)

data class Results(
    @SerialName("dob")
    val dob: Dob,
    @SerialName("email")
    val email: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("id")
    val id: Id,
    @SerialName("location")
    val location: Location,
    @SerialName("name")
    val name: Name,
    @SerialName("phone")
    val phone: String,
    @SerialName("picture")
    val picture: Picture
)

@Serializable
data class Dob(
    @SerialName("age")
    val age: Int
)

@Serializable
data class Id(
    @SerialName("name")
    val name: String,
    @SerialName("value")
    val value: String
)

@Serializable
data class Location(
    @SerialName("city")
    val city: String,
    @SerialName("country")
    val country: String,
    @SerialName("state")
    val state: String,
)

@Serializable
data class Name(
    @SerialName("first")
    val first: String,
    @SerialName("last")
    val last: String
)

@Serializable
data class Picture(
    @SerialName("large")
    val large: String,
    @SerialName("medium")
    val medium: String,
    @SerialName("thumbnail")
    val thumbnail: String
)