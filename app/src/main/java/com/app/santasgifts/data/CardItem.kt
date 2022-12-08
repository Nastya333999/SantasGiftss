package com.app.santasgifts.data

import androidx.annotation.DrawableRes
import java.util.*

data class CardItem(

    @DrawableRes val resId: Int,
    val flipState: FlipState,
    val id: String = UUID.randomUUID().toString()
)

enum class FlipState{
    FRONT, BACK, INVISIBLE
}