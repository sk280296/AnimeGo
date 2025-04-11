package com.app.animego.data.dto

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("mal_id")
    var malId: Int? = null,
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("images")
    var images: CharacterImages? = null,
    @SerializedName("name")
    var name: String? = null
)