package com.knz21.android.recipeviewer.infra.api

import com.knz21.android.recipeviewer.domain.model.Contributor
import com.knz21.android.recipeviewer.domain.struct.RecipesStruct
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RecipeService {
    @GET("/videos_sample.json")
    fun fetchRecipes(): Observable<RecipesStruct>


    @GET("/repos/{owner}/{repo}/contributors")
    fun fetchContributors(@Path("owner") owner: String,
                          @Path("repo") repo: String,
                          @Query("per_page") perPage: Int): Observable<List<Contributor>>
}