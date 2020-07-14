package com.example.fincar.extensions

import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup

fun View.setVisibilityWithAnim(visibility: Int) {
    val transition: Transition = Fade()
    transition.duration = 300
    transition.addTarget(this)
    TransitionManager.beginDelayedTransition(parent as ViewGroup?, transition)
    setVisibility(visibility)
}