package com.knz21.recipeviewer.infra.dao

import com.github.gfx.android.orma.annotation.OnConflict
import com.knz21.recipeviewer.di.ApplicationModule
import com.knz21.recipeviewer.domein.struct.RecipesStruct
import com.knz21.recipeviewer.domein.struct.toList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeDao @Inject constructor(ormaHolder: ApplicationModule.OrmaHolder) {
    private val orma = ormaHolder.orma

    fun insert(recipes: RecipesStruct) {
        orma.transactionSync { orma.prepareInsertIntoRecipe(OnConflict.REPLACE).executeAll(recipes.toList()) }
    }

    fun findAll(): RecipesStruct = RecipesStruct.create(orma.selectFromRecipe().toList())
}
