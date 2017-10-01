package com.knz21.android.recipeviewer.infra.dao

import com.github.gfx.android.orma.annotation.OnConflict
import com.knz21.android.recipeviewer.di.ApplicationModule
import com.knz21.android.recipeviewer.domain.struct.RecipesStruct
import com.knz21.android.recipeviewer.domain.struct.toAttributeList
import com.knz21.android.recipeviewer.domain.struct.toList
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
            db.selectFromRecipe().toList().run {
                RecipesStruct(map { RecipesStruct.RecipeStruct(it.id, it.type, findAttribute(it.id)) })
            }

    private fun findAttribute(id: String) =
            mapOf(*db.relationOfAttribute().idEq(id).toList().map { it.key to it.value }.toTypedArray())
}
