package com.knz21.android.recipeviewer.infra.repository

import com.knz21.android.recipeviewer.domain.struct.FavoriteTime
import com.knz21.android.recipeviewer.domain.struct.RecipesStruct
import com.knz21.android.recipeviewer.infra.api.RecipeClient
import com.knz21.android.recipeviewer.infra.dao.RecipeDao
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(private val client: RecipeClient, private val dao: RecipeDao) {

    fun getRecipes(): Observable<RecipesStruct> =
            client.fetchRecipes()
                    .doOnNext { dao.insert(it) }
                    .doOnNext { it.data.forEach { it.favoriteTime = dao.findFavoriteTime(it.id) } }
                    .onErrorReturn { dao.findAll() }

    fun toggleFavorite(id: String, isFavorite: Boolean): Observable<FavoriteTime> =
            Observable.create {
                it.onNext(
                        if (isFavorite) FavoriteTime(dao.saveFavorite(id), true)
                        else FavoriteTime(Calendar.getInstance().time, false).apply {
                            dao.deleteFavorite(id)
                        }
                )
                it.onComplete()
            }
}
