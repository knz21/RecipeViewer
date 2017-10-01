package com.knz21.android.recipeviewer.presentation

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.knz21.android.recipeviewer.R
import com.knz21.android.recipeviewer.databinding.ActivityRecipeBinding
import com.knz21.android.recipeviewer.di.RecipeModule
import com.knz21.android.recipeviewer.domain.struct.FavoriteTime
import com.knz21.android.recipeviewer.domain.struct.RecipesStruct
import com.knz21.android.recipeviewer.util.Progress
import com.knz21.android.recipeviewer.view.adapter.RecipeListAdapter
import com.knz21.android.recipeviewer.view.decoration.RecipeListDecoration
import javax.inject.Inject

class RecipeActivity : AppCompatActivity(), RecipePresenter.Contract {
    private val binding by lazy { DataBindingUtil.setContentView<ActivityRecipeBinding>(this, R.layout.activity_recipe) }
    private val recipes: RecipesStruct = RecipesStruct(mutableListOf())
    @Inject lateinit var presenter: RecipePresenter

    companion object {
        private const val COLUMN_SIZE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as RecipeApp).component.plus(RecipeModule(this)).inject(this)
        binding.listView.layoutManager = GridLayoutManager(this, COLUMN_SIZE)
        binding.listView.addItemDecoration(RecipeListDecoration(this))
        binding.bottomBar.setOnTabSelectListener { presenter.toggleRecipeList(recipes, it) }
        binding.bottomBar.setOnTabReselectListener { binding.listView.smoothScrollToPosition(0) }
        presenter.getRecipes()
        Progress.show(this)
    }

    override fun onDestroy() {
        presenter.dispose()
        super.onDestroy()
    }

    override fun showRecipes(recipes: RecipesStruct) {
        Progress.hide(this)
        this.recipes.setAllRecipes(recipes)
        binding.listView.adapter = RecipeListAdapter(this, presenter, recipes)
    }

    override fun toggleFavorite(id: String, time: FavoriteTime) {
        recipes.updateFavorite(id, if (time.hasTime) time.time else null)
        update(recipes)
        if (time.hasTime) Snackbar.make(binding.root, getString(R.string.snack_bar_message,
                recipes.getTitle(recipes.getPosition(id))), Snackbar.LENGTH_LONG)
                .setAction(R.string.snack_bar_dismiss, { presenter.toggleFavorite(id, false) })
                .show()
    }

    override fun updateList(recipes: RecipesStruct) {
        update(recipes)
    }

    private fun update(recipes: RecipesStruct) {
        (binding.listView.adapter as? RecipeListAdapter)?.update(recipes)
    }
}
