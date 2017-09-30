package com.knz21.android.recipeviewer.infra.repositoryu

import com.knz21.android.recipeviewer.domain.model.Contributor
import com.knz21.android.recipeviewer.domain.struct.RecipesStruct
import com.knz21.android.recipeviewer.infra.api.RecipeClient
import com.knz21.android.recipeviewer.infra.dao.RecipeDao
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(private val client: RecipeClient, private val dao: RecipeDao) {

    fun getContributors(): Observable<List<Contributor>> {
        return client.fetchContributors()
                .doOnNext { users -> dao.insert(users) }
                .onErrorReturn { dao.findAll() }
    }

    fun getRecipes(): Observable<RecipesStruct> {
        return client.fetchRecipes()
                .doOnNext { dao.insert2(it) }
                .onErrorReturn { dao.findAll2() }
    }
}