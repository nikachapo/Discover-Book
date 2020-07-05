package com.example.fincar.activities.registration

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.fincar.R
import com.example.fincar.app.Tools
import com.example.fincar.app.Tools.showToast
import com.example.fincar.bean.UserModel
import com.example.fincar.databinding.ActivityRegistrationBinding
import com.example.fincar.network.firebase.FirebaseDbHelper
import com.example.fincar.network.firebase.RegistrationCallbacks
import com.example.fincar.network.firebase_storage.FirebaseStorageHelper
import com.example.fincar.network.firebase_storage.UploadFileListener
import kotlinx.android.synthetic.main.activity_registration.*
import java.text.DateFormat
import java.util.*

const val EXTRA_USER = "extra-user"

class RegistrationActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private var imageUri: Uri? = null
    private var imageUrl: String = ""

    private var currentUserModel: UserModel? = null
    private lateinit var binding: ActivityRegistrationBinding

    private val firebaseDbHelper = FirebaseDbHelper.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)

        getUserFromIntent(intent)

        setListeners()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Tools.PICK_IMAGE && resultCode == RESULT_OK && data != null) {

            imageUri = data.data

            Glide.with(this)
                .load(imageUri)
                .into(profileImageView)

        }

    }

    private fun setListeners() {

        binding.chooseImageLayout.setOnClickListener {
            Tools.openImageChooser(this)
        }

        binding.birthDateTextView.setOnClickListener {
            Tools.showDatePickerDialog(supportFragmentManager)
        }

        binding.saveButton.setOnClickListener {

            if (isEveryFieldValid()) { registerUser() }

        }
    }

    private fun isEveryFieldValid(): Boolean {
        return (Validator.isValidEmail(binding.emailInputLayout.editText?.text.toString(), binding)
                && Validator.isPhoneValid(
            binding.phoneInputLayout.editText?.text.toString(),
            binding
        )
                && Validator.isFirstNameValid(
            binding.firstNameInputLayout.editText?.text.toString(),
            binding
        )
                && Validator.isLastNameValid(
            binding.lastNameInputLayout.editText?.text.toString(),
            binding
        )
                && Validator.isBirthDateValid(binding.birthDateTextView.text.toString()))
    }

    private fun getUserFromIntent(intent: Intent) {
        if (intent.hasExtra(EXTRA_USER)) {
            currentUserModel = intent.getParcelableExtra(EXTRA_USER) as UserModel
            binding.user = currentUserModel

            Glide.with(this)
                .load(currentUserModel!!.imageUrl)
                .into(profileImageView)

            binding.chooseImageLayout.isClickable = false

        }
    }

    private fun registerUser() {

        if (imageUri != null) {
            FirebaseStorageHelper.putFileToStorage(imageUri!!, object : UploadFileListener {
                override fun onSuccess(url: String) {
                    imageUrl = url
                    writeDataToRD()
                }

                override fun onError(message: String) {
                    showToast(this@RegistrationActivity, message)
                }

            })
        }

    }

    private fun writeDataToRD() {
        val user = userModelWithCurrentData()
        user.imageUrl = imageUrl

        firebaseDbHelper.registerUser(user, object : RegistrationCallbacks {
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
        birthDateTextView.text = currentDateString
    }

}
