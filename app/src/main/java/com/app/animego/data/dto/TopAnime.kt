package com.app.animego.data.dto

import com.google.gson.annotations.SerializedName

data class TopAnime(
    @SerializedName("pagination")
    var pagination: Pagination? = null,
    @SerializedName("data")
    var data: ArrayList<Data> = arrayListOf()
)


