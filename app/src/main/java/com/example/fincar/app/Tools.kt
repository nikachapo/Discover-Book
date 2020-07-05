package com.example.fincar.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.fincar.fragments.DatePickerFragment

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

}