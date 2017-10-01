package com.knz21.android.recipeviewer.presentation

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.knz21.android.recipeviewer.R
import com.knz21.android.recipeviewer.databinding.ActivityRecipeBinding
import com.knz21.android.recipeviewer.di.RecipeModule
import com.knz21.android.recipeviewer.domain.struct.RecipesStruct
import com.knz21.android.recipeviewer.view.adapter.RecipeListAdapter
import com.knz21.android.recipeviewer.view.decoration.RecipeListDecoration
import timber.log.Timber
import javax.inject.Inject

class RecipeActivity : AppCompatActivity(), RecipePresenter.Contract {
    private val binding by lazy { DataBindingUtil.setContentView<ActivityRecipeBinding>(this, R.layout.activity_recipe) }
    @Inject lateinit var presenter: RecipePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as RecipeApp).component.plus(RecipeModule(this)).inject(this)
        binding.listView.layoutManager = GridLayoutManager(this, 2)
        binding.listView.addItemDecoration(RecipeListDecoration(this))
        binding.bottomBar.setOnTabSelectListener { Timber.i("tabselecterd") }
        binding.bottomBar.setOnTabReselectListener { binding.listView.smoothScrollToPosition(0) }
        presenter.getRecipes()
    }

    override fun onDestroy() {
        presenter.dispose()
        super.onDestroy()
    }

    override fun showRecipes(recipes: RecipesStruct) {
        Timber.i("#showRecipes")
        binding.listView.adapter = RecipeListAdapter(this, recipes)
    }
}
