package com.knz21.recipeviewer.infra.api

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeClient @Inject constructor(private val service: RecipeService) {
    fun fetchRecipes() = service.fetchRecipes()
}