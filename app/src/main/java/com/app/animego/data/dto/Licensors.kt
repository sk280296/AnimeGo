package com.app.animego.data.dto

import com.google.gson.annotations.SerializedName

data class Licensors(
    @SerializedName("mal_id")
    var malId: Int? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("url")
    var url: String? = null
)

