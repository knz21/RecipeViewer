package com.knz21.recipeviewer.di

import com.knz21.recipeviewer.presentation.RecipeActivity
import com.knz21.recipeviewer.presentation.RecipePresenter
import dagger.Provides
import dagger.Subcomponent

class RecipeModule(private val contract: RecipePresenter.Contract) {

    @Provides
    fun provideContract() = contract
}

@Subcomponent(modules = arrayOf(RecipeModule::class))
interface RecipeComponent{
    fun inject(a: RecipeActivity)
}