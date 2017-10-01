package com.knz21.android.recipeviewer.util

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.knz21.android.recipeviewer.R

object Progress {
    fun show(context: Context) {
        if (context is Activity) (context.findViewById(android.R.id.content) as ViewGroup)
                .addView(LayoutInflater.from(context).inflate(R.layout.progress, null))
    }

    fun hide(context: Context) {
        if (context is Activity)
            (context.findViewById(android.R.id.content) as ViewGroup).run {
                repeat(childCount) {
                    getChildAt(it)?.let { if (it.tag == context.getString(R.string.tag_progress)) removeView(it) }
                }
            }
    }
}