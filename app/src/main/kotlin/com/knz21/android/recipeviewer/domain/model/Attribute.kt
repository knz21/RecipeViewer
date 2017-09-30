package com.knz21.android.recipeviewer.domain.model

import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.PrimaryKey
import com.github.gfx.android.orma.annotation.Setter
import com.github.gfx.android.orma.annotation.Table

@Table
data class Attribute(
        @PrimaryKey
        @Setter("id")
        val id: String,

        @Column
        @Setter("key")
        val key: String,

        @Column
        @Setter("value")
        val value: String
)