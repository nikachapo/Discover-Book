package com.example.fincar.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.airbnb.lottie.LottieAnimationView
import com.example.fincar.R
import com.example.fincar.activities.registration.RegistrationActivity
import com.example.fincar.app.Tools.showToast
import com.example.fincar.adapters.ViewPagerAdapter
import com.example.fincar.fragments.home.HomeFragment
import com.example.fincar.network.firebase.FirebaseDbHelper
import com.example.fincar.fragments.search_books.SearchBooksFragment
import com.example.fincar.fragments.profile.ProfileFragment
import com.example.fincar.fragments.starred.StarredBooksFragment
import com.example.fincar.network.firebase.CheckUserCallbacks
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var navView: BottomNavigationView

    private lateinit var loadingRootLayout: FrameLayout
    private lateinit var loadingAnimationView: LottieAnimationView

    private val firebaseDbHelper = FirebaseDbHelper.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycle.addObserver(firebaseDbHelper)

        initViews()

        checkUser()

        val fragments = arrayListOf(
            HomeFragment(),StarredBooksFragment(), SearchBooksFragment(), ProfileFragment())

        setUpViewPager(fragments)

        setUpNavView()

    }

    private fun initViews() {
        viewPager = findViewById(R.id.viewPager)
        navView = findViewById(R.id.nav_view)
        loadingRootLayout = findViewById(R.id.loadingRootLayout)
        loadingAnimationView = findViewById(R.id.loadingAnimationView)
    }

    private fun setUpViewPager(
        fragments: ArrayList<Fragment>
    ) {
        viewPager.adapter = ViewPagerAdapter(
            supportFragmentManager,
            fragments
        )

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                navView.menu.getItem(position).isChecked = true
            }
        })
    }

    private fun setUpNavView() {
        navView.setOnNavigationItemSelectedListener {
            val currentItem: Int = when (it.itemId) {
                R.id.navigation_home -> 0
                R.id.navigation_starred -> 1
                R.id.navigation_search -> 2
                R.id.navigation_profile -> 3
                else -> 0
            }
            viewPager.currentItem = currentItem
            true
        }
    }

    private fun checkUser() {
        loadingRootLayout.visibility = View.VISIBLE
        loadingAnimationView.playAnimation()
        firebaseDbHelper.checkUser(
            checkUserCallback
        )
    }

    private val checkUserCallback = object : CheckUserCallbacks {
        override fun onError(message: String) {
            closeLoading()
            showToast(
                applicationContext,
                message
            )
        }

        override fun onExists() {
            closeLoading()
        }

        override fun onNotFound() {
            closeLoading()
            startActivity(Intent(this@MainActivity, RegistrationActivity::class.java))
        }

    }

    private fun closeLoading(){
        loadingRootLayout.visibility = View.GONE
        loadingAnimationView.cancelAnimation()
    }
}
