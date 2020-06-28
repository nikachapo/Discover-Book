package com.example.fincar.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.fincar.R
import com.example.fincar.Tools.showToast
import com.example.fincar.fragments.profile.UserModel
import com.example.fincar.databinding.ActivityRegistrationBinding
import com.example.fincar.network.firebase.FirebaseDbHelper
import com.example.fincar.fragments.DatePickerFragment
import com.example.fincar.network.firebase.RegistrationCallbacks
import com.google.firebase.auth.FirebaseUser
import java.text.DateFormat
import java.util.*

const val EXTRA_USER = "extra-user"

class RegistrationActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private var currentUserModel: UserModel? = null
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_registration
        )

        getUserFromIntent(intent)

        setListeners()

    }

    private fun setListeners() {
        binding.chooseDateButton.setOnClickListener {
            val datePicker: DialogFragment = DatePickerFragment()
            datePicker.show(supportFragmentManager, "date picker")
        }

        binding.saveButton.setOnClickListener {
            if (isValidEmail(binding.emailInputLayout.editText?.text.toString())
                && isPhoneValid(binding.phoneInputLayout.editText?.text.toString())
                && isFirstNameValid(binding.firstNameInputLayout.editText?.text.toString())
                && isLastNameValid(binding.lastNameInputLayout.editText?.text.toString())
                && isBirthDateValid(binding.birthDateTextView.text.toString())
            ) {

                registerUser()
            }
        }
    }

    private fun getUserFromIntent(intent: Intent) {
        if (intent.hasExtra(EXTRA_USER)) {
            currentUserModel = intent.getParcelableExtra(EXTRA_USER) as UserModel
            binding.user = currentUserModel
        }
    }

    private fun registerUser() {
        val user = userModelWithCurrentData()
        FirebaseDbHelper.registerUser(user, object : RegistrationCallbacks {
            override fun onError(message: String) {
                showToast(this@RegistrationActivity, message)
            }

            override fun onSuccessRegistration() {
                showToast(
                    this@RegistrationActivity,
                    "Saved"
                )
                finish()
            }

        })
    }

    private fun userModelWithCurrentData(): UserModel {
        val email = binding.emailInputLayout.editText?.text.toString()
        val phone = binding.phoneInputLayout.editText?.text.toString()
        val firstName = binding.firstNameInputLayout.editText?.text.toString()
        val lastName = binding.lastNameInputLayout.editText?.text.toString()
        val birthDate = binding.birthDateTextView.text.toString()
        val location = binding.locationInputLayout.editText?.text.toString()

        return UserModel(
            email,
            phone,
            firstName,
            lastName,
            birthDate,
            location
        )
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val currentDateString: String =
            DateFormat.getDateInstance().format(calendar.time)
        binding.birthDateTextView.text = currentDateString
        binding.invalidateAll()
    }

    private fun isFirstNameValid(firstNameText: String): Boolean {
        val isValid = firstNameText.isNotEmpty()
        if (!isValid) {
            binding.firstNameInputLayout.editText?.error = "Enter first name"
        }
        return isValid
    }

    private fun isLastNameValid(lastNameText: String): Boolean {
        val isValid = lastNameText.isNotEmpty()
        if (!isValid) {
            binding.lastNameInputLayout.editText?.error = "Enter last name"
        }
        return isValid
    }

    private fun isValidEmail(emailText: String): Boolean {
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

    private fun isBirthDateValid(birthDayText: String): Boolean {
        return if (birthDayText == getString(R.string.birth_date)) {
            showToast(this, "Please choose birth date")
            false
        } else true
    }

    private fun isPhoneValid(phone: String): Boolean {
        return if (phone.isEmpty()) {
            binding.phoneInputLayout.editText?.error = "Enter phone"
            false
        } else true
    }

}
