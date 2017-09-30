package com.knz21.recipeviewer.domein.struct

import com.knz21.recipeviewer.domein.model.Recipe

data class RecipesStruct(val data: List<RecipeStruct>) {
    data class RecipeStruct(val id: String, val type: String, val attributes: Map<String, String>)

    companion object {
        fun create(recipeList: List<Recipe>): RecipesStruct {
            return RecipesStruct(listOf())// todo
        }
    }
}

fun RecipesStruct.toList(): List<Recipe> = listOf()