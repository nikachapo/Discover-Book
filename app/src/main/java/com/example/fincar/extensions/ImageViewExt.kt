package com.example.fincar.extensions

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(uri: Uri?){
    Glide.with(context)
        .load(uri)
        .into(this)
}