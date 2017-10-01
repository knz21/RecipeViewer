package com.knz21.android.recipeviewer.presentation

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.knz21.android.recipeviewer.R
import com.knz21.android.recipeviewer.databinding.ActivityRecipeBinding
import com.knz21.android.recipeviewer.databinding.RecipeItemBinding
import com.knz21.android.recipeviewer.di.RecipeModule
import com.knz21.android.recipeviewer.domain.struct.RecipesStruct
import com.squareup.picasso.Picasso
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

    private class RecipeListAdapter(private val context: Context, private val recipes: RecipesStruct) :
            RecyclerView.Adapter<RecipeItemViewHolder>() {
        override fun onBindViewHolder(holder: RecipeItemViewHolder?, position: Int) {
            holder?.binding?.run {
                recipeThumbnail.run { Picasso.with(context).load(recipes.getThumbUrl(position)).into(this) }
                recipeTitle.run { text = recipes.getTitle(position) }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecipeItemViewHolder =
                RecipeItemViewHolder(RecipeItemBinding.bind(View.inflate(context, R.layout.recipe_item, null)))

        override fun getItemCount(): Int = recipes.getSize()
    }

    private data class RecipeItemViewHolder(val binding: RecipeItemBinding) : RecyclerView.ViewHolder(binding.root)

    private class RecipeListDecoration(context: Context) : RecyclerView.ItemDecoration() {
        private val offset: Int by lazy { context.resources.getDimensionPixelSize(R.dimen.cell_offset) }

        override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
            outRect?.run {
                left = offset
                parent?.getChildAdapterPosition(view)?.let { top = if (it < 2) offset * 2 else 0 }
                right = offset
                bottom = offset * 2
            }
        }
    }
}
