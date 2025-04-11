package com.app.animego.data.dto

import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("mal_id")
    var malId: Int? = null,
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("images")
    var images: CharacterImages? = CharacterImages(),
    @SerializedName("name")
    var name: String? = null
)