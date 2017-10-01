package com.knz21.android.recipeviewer.domain.struct

import com.knz21.android.recipeviewer.domain.model.Attribute
import com.knz21.android.recipeviewer.domain.model.Recipe

class RecipesStruct(val data: List<RecipeStruct>) {
    class RecipeStruct(val id: String, val type: String, val attributes: Map<String, String>)

    companion object {
        private const val KEY_TITLE = "title"
        private const val KEY_THUMBNAIL_SQUARE_URL = "thumbnail-square-url"
    }

    private fun getItem(position: Int): RecipeStruct = data[position]

    fun getSize(): Int = data.size

    fun getTitle(position: Int): String = getItem(position).attributes[KEY_TITLE] ?: ""

    fun getThumbUrl(position: Int): String? = getItem(position).attributes[KEY_THUMBNAIL_SQUARE_URL]
}

fun RecipesStruct.toList(): List<Recipe> = data.map { Recipe(it.id, it.type) }

fun RecipesStruct.toAttributeList(): List<Attribute> = mutableListOf<Attribute>().apply {
    data.forEach { it.attributes.forEach { entry -> add(Attribute(it.id, entry.key, entry.value)) } }
}
