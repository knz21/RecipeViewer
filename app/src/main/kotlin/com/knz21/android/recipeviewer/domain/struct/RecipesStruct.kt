package com.knz21.android.recipeviewer.domain.struct

import com.knz21.android.recipeviewer.domain.model.Attribute
import com.knz21.android.recipeviewer.domain.model.Recipe
import java.util.*

class RecipesStruct(val data: MutableList<RecipeStruct>) {
    class RecipeStruct(val id: String, val type: String, val attributes: Map<String, String>) {
        var favoriteTime: Date? = null
    }

    companion object {
        private const val KEY_TITLE = "title"
        private const val KEY_THUMBNAIL_SQUARE_URL = "thumbnail-square-url"
    }

    private fun getItem(position: Int): RecipeStruct = data[position]

    fun getSize(): Int = data.size

    fun getId(position: Int): String = getItem(position).id

    fun getTitle(position: Int): String = getItem(position).attributes[KEY_TITLE] ?: ""

    fun getThumbUrl(position: Int): String? = getItem(position).attributes[KEY_THUMBNAIL_SQUARE_URL]

    fun isFavorite(position: Int): Boolean = getItem(position).favoriteTime != null

    fun getPosition(id: String): Int = data.indexOfFirst { it.id == id }

    fun setAllRecipes(recipes: RecipesStruct) {
        data.clear()
        data.addAll(recipes.data)
    }

    fun updateFavorite(id: String, favoriteTime: Date?) {
        data.find { it.id == id }?.favoriteTime = favoriteTime
    }
}

fun RecipesStruct.toList(): List<Recipe> = data.map { Recipe(it.id, it.type) }

fun RecipesStruct.toAttributeList(): List<Attribute> = mutableListOf<Attribute>().apply {
    data.forEach { it.attributes.forEach { entry -> add(Attribute(it.id, entry.key, entry.value)) } }
}
