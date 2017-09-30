package com.knz21.android.recipeviewer.di

import com.knz21.android.recipeviewer.presentation.RecipeActivity
import com.knz21.android.recipeviewer.presentation.RecipePresenter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class RecipeModule(private val contract: RecipePresenter.Contract) {

  @Provides
  fun provideContract() = contract
}

@Subcomponent(modules = arrayOf(RecipeModule::class))
interface RecipeComponent {
  fun inject(a: RecipeActivity)
}