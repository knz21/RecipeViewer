package com.knz21.recipeviewer.infra.api

import com.knz21.recipeviewer.domein.struct.RecipesStruct
import io.reactivex.Observable
import retrofit2.http.GET

interface RecipeService {
    @GET("/videos_sample.json")
    fun fetchRecipes(): Observable<RecipesStruct>
}