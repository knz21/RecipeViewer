package com.knz21.android.recipeviewer.domain.model

import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.PrimaryKey
import com.github.gfx.android.orma.annotation.Setter
import com.github.gfx.android.orma.annotation.Table
import com.google.gson.annotations.SerializedName

@Table
data class Contributor(
    @SerializedName("login")
    @PrimaryKey(auto = false)
    @Setter("name")
    val name: String,

    @Column
    @Setter("avatarUrl")
    val avatarUrl: String?,

    @Column
    @Setter("htmlUrl")
    val htmlUrl: String?,

    @Column
    @Setter("contributions")
    val contributions: Int
)