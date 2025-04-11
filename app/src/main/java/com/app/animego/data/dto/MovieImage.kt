package com.app.animego.data.dto

import com.google.gson.annotations.SerializedName

data class MovieImage(
    @SerializedName("jpg")
    var jpg: Jpg? = null,
    @SerializedName("webp")
    var webp: Webp? = null
)

