package com.example.fincar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var providers: List<AuthUI.IdpConfig>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initProviders()

    }

    private fun initProviders() {
        providers = listOf(
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build()
        )
    }

    override fun onStart() {
        super.onStart()
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        if(currentFirebaseUser == null){
            showSignInOption()
        }else startMainActivity()
    }

    private fun startMainActivity() {
        startActivity(Intent(this,MainActivity::class.java))
    }

    private fun showSignInOption() {
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false) //set true to save credentials
                .setTheme(R.style.AuthUiTheme)
                .setLogo(R.mipmap.ic_launcher_round)
                .build(), LOGIN_REQUEST
        )
    }

    companion object {
        private const val LOGIN_REQUEST = 22
    }
}
