package com.cerenb.samples.apps.rijksmuseumcollections.ui

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cerenb.samples.apps.rijksmuseumcollections.R

fun ImageView.loadImage(url: String, context: Context) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.ic_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}