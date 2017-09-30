package com.knz21.recipeviewer.infra.repository

import com.knz21.recipeviewer.domein.struct.RecipesStruct
import com.knz21.recipeviewer.infra.dao.RecipeDao
import com.knz21.recipeviewer.infra.api.RecipeClient
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(private val client: RecipeClient, private val dao: RecipeDao) {

    fun getRecipes(): Observable<RecipesStruct> {
        return client.fetchRecipes()
                .doOnNext { dao.insert(it) }
                .onErrorReturn { dao.findAll() }
    }
}