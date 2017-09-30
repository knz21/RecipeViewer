package com.knz21.android.recipeviewer.domain.struct

import com.knz21.android.recipeviewer.domain.model.Recipe

class RecipesStruct(val data: List<RecipeStruct>) {
    class RecipeStruct(val id: String, val type: String, val attributes: Map<String, String>)

    companion object {
        fun create(recipeList: List<Recipe>): RecipesStruct {
            return RecipesStruct(listOf())// todo
        }
    }
}

fun RecipesStruct.toList(): List<Recipe> = listOf()
