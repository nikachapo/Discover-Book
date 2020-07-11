package com.example.fincar.app

import android.animation.Animator
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.airbnb.lottie.LottieAnimationView
import com.example.fincar.R
import com.example.fincar.fragments.DatePickerFragment
import kotlinx.android.synthetic.main.activity_add_selling_book.*
import kotlinx.android.synthetic.main.dialog_layout.*

object Tools {

    const val PICK_IMAGE = 12

    fun showToast(context: Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun startLoadingAnimation(loadingRootLayout: ViewGroup,
                              loadingAnimationView: LottieAnimationView) {

        loadingRootLayout.visibility = View.VISIBLE
        loadingAnimationView.playAnimation()

    }

    fun cancelLoadingAnimation(loadingRootLayout: ViewGroup,
                               loadingAnimationView: LottieAnimationView) {

        loadingRootLayout.visibility = View.GONE
        loadingAnimationView.cancelAnimation()

    }

    fun openImageChooser(activity: Activity) {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        activity.startActivityForResult(intent, PICK_IMAGE)
    }

    fun showDatePickerDialog(fragmentManager: FragmentManager) {
        val datePicker: DialogFragment = DatePickerFragment()
        datePicker.show(fragmentManager, "date picker")
    }

    fun showDialog(context: Context, title:String, assetName: String){
        val dialog = Dialog(context)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_layout)

        val params = dialog.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT

        dialog.window!!.attributes = params
        dialog.dialogTitleTV.text = title


        dialog.dialogAnimationView.setAnimation(assetName)

        dialog.dialogOkButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun startTransition(rootLayout: ViewGroup) {
        val viewTreeObserver = rootLayout.viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    circularRevealActivity(rootLayout)
                    rootLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }
    }

    fun startBackRevealTransition(rootLayout: ViewGroup, activity: Activity) {
        val cx: Int = rootLayout.width
        val cy: Int = rootLayout.bottom

        val finalRadius: Float =
            rootLayout.width.coerceAtLeast(rootLayout.height).toFloat()
        val circularReveal =
            ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, finalRadius, 0f)

        circularReveal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animator: Animator) {
                rootLayout.visibility = View.INVISIBLE
                activity.finish()
            }
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        circularReveal.duration = 1000
        circularReveal.start()

    }

    private fun circularRevealActivity(rootLayout: ViewGroup) {
        val cx: Int = rootLayout.right
        val cy: Int = rootLayout.bottom

        val finalRadius: Float =
            rootLayout.width.coerceAtLeast(rootLayout.height).toFloat()

        val circularReveal = ViewAnimationUtils.createCircularReveal(
            rootLayout,
            cx,
            cy, 0f,
            finalRadius
        )

        circularReveal.duration = 1000
        rootLayout.visibility = View.VISIBLE
        circularReveal.start()
    }
}