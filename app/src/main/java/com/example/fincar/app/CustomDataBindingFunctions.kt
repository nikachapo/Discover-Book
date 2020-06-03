package com.example.fincar.app

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object CustomDataBindingFunctions {

    @JvmStatic
    @BindingAdapter("imageFromUrl")
    fun bindImageFromUrl(
        view: ImageView, imageUrl: String?
    ) {
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(imageUrl)
                .into(view)
        }
    }
}