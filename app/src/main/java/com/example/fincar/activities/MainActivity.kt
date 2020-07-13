package com.example.fincar.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.fincar.R
import com.example.fincar.activities.registration.RegistrationActivity
import com.example.fincar.app.Tools.cancelLoadingAnimation
import com.example.fincar.app.Tools.showToast
import com.example.fincar.app.Tools.startLoadingAnimation
import com.example.fincar.network.firebase.account.AccountChecker
import com.example.fincar.network.firebase.account.CheckAccountCallbacks
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.search_toolbar_layout.*

class MainActivity : AppCompatActivity() {

    private lateinit var navView: BottomNavigationView

    private lateinit var loadingRootLayout: FrameLayout
    private lateinit var loadingAnimationView: LottieAnimationView
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var accountChecker: AccountChecker
    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(searchToolbar)

        initViews()

        startLoadingAnimation(loadingRootLayout, loadingAnimationView)

        accountChecker = AccountChecker(checkUserCallback)
        lifecycle.addObserver(accountChecker)

        setUpNavigation()
    }

    private fun setUpNavigation() {
        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_store, R.id.navigation_profile)
        )
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.navigation_search){
                searchEditText.visibility = View.VISIBLE
                searchButton.setOnClickListener(null)
            }else{
                searchEditText.visibility = View.GONE
                searchButton.setOnClickListener(searchClickListener)
            }

        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private val searchClickListener = View.OnClickListener {
        navController.navigate(R.id.navigation_search)
    }

    private fun initViews() {
        navView = findViewById(R.id.nav_view)
        loadingRootLayout = findViewById(R.id.loadingRootLayout)
        loadingAnimationView = findViewById(R.id.loadingAnimationView)
        searchEditText = findViewById(R.id.searchEditText)
        searchButton = findViewById(R.id.searchButton)
    }

    private val checkUserCallback = object :
        CheckAccountCallbacks {
        override fun onError(message: String) {
            cancelLoadingAnimation(loadingRootLayout, loadingAnimationView)
            showToast(applicationContext, message)
        }

        override fun onExists() {
            cancelLoadingAnimation(loadingRootLayout, loadingAnimationView)
        }

        override fun onNotFound() {
            cancelLoadingAnimation(loadingRootLayout, loadingAnimationView)
            startActivity(Intent(this@MainActivity, RegistrationActivity::class.java))
        }
    }
}
