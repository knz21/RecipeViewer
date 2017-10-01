package com.knz21.android.recipeviewer.domain.model

import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.PrimaryKey
import com.github.gfx.android.orma.annotation.Setter
import com.github.gfx.android.orma.annotation.Table
import java.util.*

@Table
data class Favorite(
        @PrimaryKey
        @Setter("id")
        val id: String,

        @Column
        @Setter("time")
        val time: Date
)