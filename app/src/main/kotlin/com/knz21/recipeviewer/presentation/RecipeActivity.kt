package com.knz21.recipeviewer.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.knz21.recipeviewer.R
import com.knz21.recipeviewer.di.RecipeModule
import com.knz21.recipeviewer.domein.struct.RecipesStruct
import javax.inject.Inject

class RecipeActivity : AppCompatActivity(), RecipePresenter.Contract {

    @Inject lateinit var presenter: RecipePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as RecipeApp).component.plus(RecipeModule(this)).inject(this)
        setContentView(R.layout.activity_recipe)
        presenter.getRecipes()
    }

    override fun onDestroy() {
        presenter.dispose()
        super.onDestroy()
    }

    override fun showRecipes(recipes: RecipesStruct) {
        Toast.makeText(this, "show recipe", Toast.LENGTH_LONG).show()
    }
}
