package com.example.fincar.activities

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.airbnb.lottie.LottieAnimationView
import com.example.fincar.R
import com.example.fincar.activities.registration.RegistrationActivity
import com.example.fincar.adapters.ViewPagerAdapter
import com.example.fincar.app.Tools.cancelLoadingAnimation
import com.example.fincar.app.Tools.showToast
import com.example.fincar.app.Tools.startLoadingAnimation
import com.example.fincar.fragments.home.HomeFragment
import com.example.fincar.fragments.profile.ProfileFragment
import com.example.fincar.fragments.search_books.SearchBooksFragment
import com.example.fincar.fragments.store.StoreFragment
import com.example.fincar.network.firebase.account.AccountChecker
import com.example.fincar.network.firebase.account.CheckAccountCallbacks
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var navView: BottomNavigationView

    private lateinit var loadingRootLayout: FrameLayout
    private lateinit var loadingAnimationView: LottieAnimationView

    private lateinit var accountChecker: AccountChecker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        startLoadingAnimation(loadingRootLayout, loadingAnimationView)
        accountChecker = AccountChecker(checkUserCallback)

        lifecycle.addObserver(accountChecker)

        val fragments = arrayListOf(
            HomeFragment(), StoreFragment(),
            SearchBooksFragment(), ProfileFragment()
        )

        setUpViewPager(fragments)

        setUpNavView()

    }

    private fun initViews() {
        viewPager = findViewById(R.id.viewPager)
        navView = findViewById(R.id.nav_view)
        loadingRootLayout = findViewById(R.id.loadingRootLayout)
        loadingAnimationView = findViewById(R.id.loadingAnimationView)
    }

    private fun setUpViewPager(fragments: ArrayList<Fragment>) {
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, fragments)
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener)
    }

    private fun setUpNavView() {
        navView.setOnNavigationItemSelectedListener {
            var currentItem = 0
            when (it.itemId) {
                R.id.navigation_home -> currentItem = 0
                R.id.navigation_store -> currentItem = 1
                R.id.navigation_search -> currentItem = 2
                R.id.navigation_profile -> currentItem = 3
                R.id.navigation_add_book -> {
                    openAddBookActivity()
                }
            }
            viewPager.currentItem = currentItem
            true
        }
    }

    private fun openAddBookActivity() {
        startActivity(Intent(this, AddSellingBookActivity::class.java))
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

    private val viewPagerPageChangeListener = object : ViewPager.OnPageChangeListener {
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
    }
}
