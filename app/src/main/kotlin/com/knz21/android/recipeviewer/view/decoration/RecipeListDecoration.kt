package com.knz21.android.recipeviewer.view.decoration

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.knz21.android.recipeviewer.R

class RecipeListDecoration(context: Context) : RecyclerView.ItemDecoration() {
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
