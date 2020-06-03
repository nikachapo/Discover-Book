package com.example.fincar

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

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
        splashIcon.animation = AnimationUtils
            .loadAnimation(applicationContext, R.anim.slide_in_left);
        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser

        Handler().postDelayed(Runnable { /* Create an Intent that will start the LoginActivity. */
            if(currentFirebaseUser == null){
                showSignInOption()
            }else startMainActivity()
        }, 2000)
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
