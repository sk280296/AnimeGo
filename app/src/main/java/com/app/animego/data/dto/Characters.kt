package com.app.animego.data.dto

import com.google.gson.annotations.SerializedName

data class Characters(
    @SerializedName("data")
    var data: ArrayList<CharacterData> = arrayListOf()
)

