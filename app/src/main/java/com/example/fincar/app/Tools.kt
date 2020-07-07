package com.example.fincar.app

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.fincar.R
import com.example.fincar.fragments.DatePickerFragment
import kotlinx.android.synthetic.main.dialog_layout.*

object Tools {

    const val PICK_IMAGE = 12

    fun showToast(context: Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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


}