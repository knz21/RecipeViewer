package com.knz21.android.recipeviewer.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.knz21.android.recipeviewer.R
import com.knz21.android.recipeviewer.databinding.RecipeItemBinding
import com.knz21.android.recipeviewer.domain.struct.RecipesStruct
import com.squareup.picasso.Picasso

class RecipeListAdapter(private val context: Context, private val recipes: RecipesStruct) :
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

data class RecipeItemViewHolder(val binding: RecipeItemBinding) : RecyclerView.ViewHolder(binding.root)
