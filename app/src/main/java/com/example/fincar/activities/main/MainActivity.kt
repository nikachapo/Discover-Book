package com.example.fincar.activities.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.fincar.R
import com.example.fincar.activities.AddSellingBookActivity
import com.example.fincar.activities.registration.EXTRA_ACCOUNT
import com.example.fincar.activities.registration.RegistrationActivity
import com.example.fincar.app.Tools.cancelLoadingAnimation
import com.example.fincar.app.Tools.showToast
import com.example.fincar.app.Tools.startLoadingAnimation
import com.example.fincar.extensions.setVisibilityWithAnim
import com.example.fincar.network.firebase.account.AccountChecker
import com.example.fincar.network.firebase.account.CheckAccountCallbacks
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.search_toolbar_layout.*

class MainActivity : AppCompatActivity() {

    private lateinit var navView: BottomNavigationView

    private lateinit var loadingRootLayout: FrameLayout
    private lateinit var loadingAnimationView: LottieAnimationView
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var accountChecker: AccountChecker
    private lateinit var navController: NavController
    private lateinit var addBookButton: FloatingActionButton
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(searchToolbar)

        initViews()

        initViewModel()

        startLoadingAnimation(loadingRootLayout, loadingAnimationView)

        addLifecycleObservers()

        setUpNavigation()
    }

    private fun addLifecycleObservers() {
        accountChecker = AccountChecker(checkUserCallback)
        lifecycle.addObserver(accountChecker)
        lifecycle.addObserver(mainViewModel.accountDataObserver)
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getAccountLiveData().observe(this, Observer { account ->
            addBookButton.setOnClickListener {
                startActivity(
                    Intent(this, AddSellingBookActivity::class.java)
                        .putExtra(EXTRA_ACCOUNT, account)
                )
            }
        })
    }

    private fun setUpNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_store, R.id.navigation_profile)
        )
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_search) {
                searchEditText.setVisibilityWithAnim(View.VISIBLE)
                searchButton.setOnClickListener(null)
            } else {
                searchEditText.setVisibilityWithAnim(View.GONE)
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
        addBookButton = findViewById(R.id.addBookButton)
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
