package com.app.animego.domain.model

import com.app.animego.data.dto.Characters

data class CastListState(
    val loading: Boolean = false,
    var castList: Characters? = null,
    val error: String = ""
)