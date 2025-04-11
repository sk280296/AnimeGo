package com.app.animego.data.dto

import com.google.gson.annotations.SerializedName

data class AnimeDetail(
    @SerializedName("data")
    var data: Data? = null
)

