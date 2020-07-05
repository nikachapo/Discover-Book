package com.example.fincar.activities.registration

import android.util.Patterns
import com.example.fincar.R
import com.example.fincar.app.App
import com.example.fincar.app.Tools
import com.example.fincar.databinding.ActivityRegistrationBinding

object Validator {

    fun isFirstNameValid(firstNameText: String, binding: ActivityRegistrationBinding): Boolean {
        val isValid = firstNameText.isNotEmpty()
        if (!isValid) {
            binding.firstNameInputLayout.editText?.error = "Enter first name"
        }
        return isValid
    }

    fun isLastNameValid(lastNameText: String, binding: ActivityRegistrationBinding): Boolean {
        val isValid = lastNameText.isNotEmpty()
        if (!isValid) {
            binding.lastNameInputLayout.editText?.error = "Enter last name"
        }
        return isValid
    }

    fun isValidEmail(emailText: String, binding: ActivityRegistrationBinding): Boolean {
        return if (emailText.isEmpty()) {
            binding.emailInputLayout.editText?.error = "Enter email"
            false
        } else {
            val isValid = Patterns.EMAIL_ADDRESS.matcher(emailText).matches()
            if (!isValid) {
                binding.emailInputLayout.editText?.error = "Email is not valid"
            }
            return isValid
        }
    }

    fun isBirthDateValid(birthDayText: String): Boolean {
        return if (birthDayText == App.getInstance().applicationContext.getString(R.string.birth_date)) {
            Tools.showToast(App.getInstance().applicationContext, "Please choose birth date")
            false
        } else true
    }

    fun isPhoneValid(phone: String, binding: ActivityRegistrationBinding): Boolean {
        return if (phone.isEmpty()) {
            binding.phoneInputLayout.editText?.error = "Enter phone"
            false
        } else true
    }
}