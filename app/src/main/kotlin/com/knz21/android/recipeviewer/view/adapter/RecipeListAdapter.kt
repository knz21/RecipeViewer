package com.knz21.android.recipeviewer.view.adapter

import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.knz21.android.recipeviewer.R
import com.knz21.android.recipeviewer.databinding.RecipeItemBinding
import com.knz21.android.recipeviewer.domain.struct.RecipesStruct
import com.knz21.android.recipeviewer.presentation.RecipePresenter
import com.squareup.picasso.Picasso

class RecipeListAdapter(private val context: Context, private val presenter: RecipePresenter,
                        private var recipes: RecipesStruct) : RecyclerView.Adapter<RecipeItemViewHolder>() {
    private var recipeList: RecyclerView? = null

    override fun onBindViewHolder(holder: RecipeItemViewHolder?, position: Int) {
        holder?.binding?.run {
            recipeThumbnail.run { Picasso.with(context).load(recipes.getThumbUrl(position)).into(this) }
            recipeTitle.run {
                text = recipes.getTitle(position)
                setBackgroundColor(ResourcesCompat.getColor(context.resources,
                        if (recipes.isFavorite(position)) android.R.color.holo_orange_light else android.R.color.white, null))
            }
            root.setOnClickListener {
                recipeList?.getChildAdapterPosition(it)?.let {
                    presenter.toggleFavorite(recipes.getId(it), !recipes.isFavorite(it))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecipeItemViewHolder =
            RecipeItemViewHolder(RecipeItemBinding.bind(View.inflate(context, R.layout.recipe_item, null)))

    override fun getItemCount(): Int = recipes.getSize()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        recipeList = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        recipeList = null
    }

    fun update(recipes: RecipesStruct) {
        this.recipes = recipes
        notifyDataSetChanged()
    }
}

data class RecipeItemViewHolder(val binding: RecipeItemBinding) : RecyclerView.ViewHolder(binding.root)
