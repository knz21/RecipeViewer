package com.knz21.android.recipeviewer.infra.api

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeClient @Inject constructor(private val service: RecipeService) {
    fun fetchRecipes() = service.fetchRecipes()

    fun fetchContributors() = service.fetchContributors("DroidKaigi", "conference-app-2017", 100)
}