package com.example.fincar.extensions

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation

fun View.setVisibilityWithAnim(visibility: Int) {
    val alphaAnimation = if (visibility == View.VISIBLE) {
        AlphaAnimation(0F, 1F)
    } else {
        AlphaAnimation(1F, 0F)
    }
    alphaAnimation.duration = 200
    alphaAnimation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {
            if (visibility == View.VISIBLE) {
                this@setVisibilityWithAnim.visibility = View.VISIBLE
            }
        }

        override fun onAnimationEnd(animation: Animation?) {
            this@setVisibilityWithAnim.visibility = visibility
        }

        override fun onAnimationStart(animation: Animation?) {

        }

    })
    startAnimation(alphaAnimation)
}