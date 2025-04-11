package com.app.animego.data.dto

import com.google.gson.annotations.SerializedName


data class VoiceActors(
    @SerializedName("person")
    var person: Person? = Person(),
    @SerializedName("language")
    var language: String? = null
)