package com.example.fincar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.fincar.ui.home.HomeFragment
import com.example.fincar.ui.profile.ProfileFragment
import com.example.fincar.ui.starred.StarredBooksFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.viewPager)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val fragments = arrayListOf(
            HomeFragment(),StarredBooksFragment(), ProfileFragment())

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager,fragments)

        viewPager.offscreenPageLimit = 3
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
}
