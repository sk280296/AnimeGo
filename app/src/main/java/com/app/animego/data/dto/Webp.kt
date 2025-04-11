package com.app.animego.data.dto

import com.google.gson.annotations.SerializedName

data class Webp(
    @SerializedName("image_url")
    var imageUrl: String? = null,
    @SerializedName("small_image_url")
    var smallImageUrl: String? = null,
    @SerializedName("large_image_url")
    var largeImageUrl: String? = null
)

