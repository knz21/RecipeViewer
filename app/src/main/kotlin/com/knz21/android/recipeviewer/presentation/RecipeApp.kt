package com.knz21.android.recipeviewer.presentation

import android.app.Application
import com.knz21.android.recipeviewer.di.ApplicationComponent
import com.knz21.android.recipeviewer.di.ApplicationModule
import com.knz21.android.recipeviewer.di.DaggerApplicationComponent
import timber.log.Timber

class RecipeApp : Application() {
    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
    }

    override fun onCreate() {
        Timber.plant(Timber.DebugTree())
    }
}
