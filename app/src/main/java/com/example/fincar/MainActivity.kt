package com.example.fincar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.fincar.adapters.ViewPagerAdapter
import com.example.fincar.firebase.FirebaseDbHelper
import com.example.fincar.fragments.search_books.SearchBooksFragment
import com.example.fincar.fragments.profile.ProfileFragment
import com.example.fincar.fragments.starred.StarredBooksFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.viewPager)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        FirebaseDbHelper.checkUser(FirebaseDbHelper.getCurrentUser(),
        object :
            FirebaseDbHelper.CheckUserCallBacks {
            override fun onCancel(errorMessage: String?) {
                showToast(applicationContext, errorMessage!!)
            }

            override fun onExists() {
                showToast(applicationContext,"Welcome")
            }

            override fun onNotFound() {
                startActivity(Intent(this@MainActivity, RegistrationActivity::class.java))
            }

        })

        val fragments = arrayListOf(
            SearchBooksFragment(),StarredBooksFragment(), ProfileFragment())

        viewPager.adapter = ViewPagerAdapter(
            supportFragmentManager,
            fragments
        )

        viewPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                navView.menu.getItem(position).isChecked = true
            }
        })

        navView.setOnNavigationItemSelectedListener {
            val currentItem:Int = when(it.itemId){
                R.id.navigation_home -> 0
                R.id.navigation_starred -> 1
                R.id.navigation_profile -> 2
                else -> 0
            }
            viewPager.currentItem = currentItem
            true
        }


    }

    companion object{
        fun showToast(context: Context?,message:String?){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}
