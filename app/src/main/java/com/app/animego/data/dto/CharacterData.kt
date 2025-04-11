package com.app.animego.data.dto

import com.google.gson.annotations.SerializedName

data class CharacterData(
    @SerializedName("character")
    var character: Character? = null,
    @SerializedName("role")
    var role: String? = null,
    @SerializedName("favorites")
    var favorites: Int? = null,
    @SerializedName("voice_actors")
    var voiceActors: ArrayList<VoiceActors> = arrayListOf()
)