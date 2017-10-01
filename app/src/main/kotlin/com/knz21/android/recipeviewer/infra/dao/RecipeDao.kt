package com.knz21.android.recipeviewer.infra.dao

import com.github.gfx.android.orma.annotation.OnConflict
import com.knz21.android.recipeviewer.di.ApplicationModule
import com.knz21.android.recipeviewer.domain.model.Favorite
import com.knz21.android.recipeviewer.domain.struct.RecipesStruct
import com.knz21.android.recipeviewer.domain.struct.toAttributeList
import com.knz21.android.recipeviewer.domain.struct.toList
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeDao @Inject constructor(ormaHolder: ApplicationModule.OrmaHolder) {
    private val db = ormaHolder.orma

    fun insert(recipes: RecipesStruct) {
        db.transactionSync {
            db.prepareInsertIntoRecipe(OnConflict.REPLACE).executeAll(recipes.toList())
            db.prepareInsertIntoAttribute(OnConflict.REPLACE).executeAll(recipes.toAttributeList())
        }
    }

    fun findAll(): RecipesStruct =
            db.selectFromRecipe().run {
                RecipesStruct(map {
                    RecipesStruct.RecipeStruct(it.id, it.type, findAttribute(it.id)).apply {
                        favoriteTime = findFavoriteTime(it.id)
                    }
                }.toMutableList())
            }

    private fun findAttribute(id: String): Map<String, String> =
            mapOf(*db.relationOfAttribute().idEq(id).toList().map { it.key to it.value }.toTypedArray())

    fun findFavoriteTime(id: String): Date? = db.selectFromFavorite().idEq(id).firstOrNull()?.time

    fun saveFavorite(id: String): Date = Calendar.getInstance().time.apply {
        db.transactionSync { db.prepareInsertIntoFavorite(OnConflict.REPLACE).execute(Favorite(id, this)) }
    }

    fun deleteFavorite(id: String) {
        db.transactionSync { db.deleteFromFavorite().idEq(id).execute() }
    }
}
