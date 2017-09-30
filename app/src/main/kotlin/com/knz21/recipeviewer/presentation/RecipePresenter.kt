package com.knz21.recipeviewer.presentation

import com.knz21.recipeviewer.domein.struct.RecipesStruct
import com.knz21.recipeviewer.extension.addTo
import com.knz21.recipeviewer.extension.applySchedulers
import com.knz21.recipeviewer.infra.repository.RecipeRepository
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class RecipePresenter @Inject constructor(private val contract: Contract, private val repository: RecipeRepository) {
    private val disposables = CompositeDisposable()

    fun getRecipes() {
        repository.getRecipes()
                .applySchedulers()
                .subscribe(
                        { contract.showRecipes(it) },
                        { Timber.e(it, "#getContributors") }
                )
                .addTo(disposables)
    }

    fun dispose() {
        disposables.dispose()
    }

    interface Contract {
        fun showRecipes(recipes: RecipesStruct)
    }
}