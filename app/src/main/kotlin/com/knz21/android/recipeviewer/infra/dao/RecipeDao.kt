package com.knz21.android.recipeviewer.infra.dao

import com.github.gfx.android.orma.annotation.OnConflict
import com.knz21.android.recipeviewer.di.ApplicationModule
import com.knz21.android.recipeviewer.domain.model.Contributor
import com.knz21.android.recipeviewer.domain.struct.RecipesStruct
import com.knz21.android.recipeviewer.domain.struct.toList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeDao @Inject constructor(ormaHolder: ApplicationModule.OrmaHolder) {
    private val orma = ormaHolder.orma

    fun insert(contributors: List<Contributor>) {
        orma.transactionSync { orma.prepareInsertIntoContributor(OnConflict.REPLACE).executeAll(contributors) }
    }

    fun findAll(): List<Contributor> = orma.selectFromContributor().toList()

    fun insert2(recipes: RecipesStruct) {
        orma.transactionSync { orma.prepareInsertIntoRecipe(OnConflict.REPLACE).executeAll(recipes.toList()) }
    }

    fun findAll2(): RecipesStruct = RecipesStruct.create(orma.selectFromRecipe().toList())
}