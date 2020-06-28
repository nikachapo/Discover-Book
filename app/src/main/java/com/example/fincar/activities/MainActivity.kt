package com.example.fincar.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.fincar.R
import com.example.fincar.Tools.showToast
import com.example.fincar.adapters.ViewPagerAdapter
import com.example.fincar.network.firebase.FirebaseDbHelper
import com.example.fincar.fragments.search_books.SearchBooksFragment
import com.example.fincar.fragments.profile.ProfileFragment
import com.example.fincar.fragments.starred.StarredBooksFragment
import com.example.fincar.network.firebase.CheckUserCallbacks
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        navView = findViewById(R.id.nav_view)

        checkUser()

        val fragments = arrayListOf(
            SearchBooksFragment(),StarredBooksFragment(), ProfileFragment())

        setUpViewPager(fragments)

        setUpNavView()

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
                R.id.navigation_profile -> 2
                else -> 0
            }
            viewPager.currentItem = currentItem
            true
        }
    }

    private fun checkUser() {
        FirebaseDbHelper.checkUser(
            object :
                CheckUserCallbacks {
                override fun onError(message: String) {
                    showToast(
                        applicationContext,
                        message
                    )
                }

                override fun onExists() {
                    showToast(
                        applicationContext,
                        "Welcome"
                    )
                }

                override fun onNotFound() {
                    startActivity(Intent(this@MainActivity, RegistrationActivity::class.java))
                }

            })
    }
}
