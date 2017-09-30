package com.knz21.android.recipeviewer.presentation

import com.knz21.android.recipeviewer.domain.model.Contributor
import com.knz21.android.recipeviewer.extension.addTo
import com.knz21.android.recipeviewer.extension.applySchedulers
import com.knz21.android.recipeviewer.infra.repositoryu.RecipeRepository
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class RecipePresenter @Inject constructor(private val contract: Contract, private val repository: RecipeRepository) {
    private val disposables = CompositeDisposable()

    fun getContributors() {
        repository.getContributors()
                .applySchedulers()
                .subscribe(
                        { contract.showContributors(it) },
                        { Timber.e(it, "#getContributors") }
                )
                .addTo(disposables)
    }

    fun getRecipes() {
        repository.getRecipes()
                .applySchedulers()
//                .subscribe(
//                        { contract.showRecipes(it) },
//                        { Timber.e(it, "#getContributors") }
//                )
//                .addTo(disposables)
    }


    fun dispose() {
        disposables.dispose()
    }

    interface Contract {
        fun showContributors(contributors: List<Contributor>)
    }
}