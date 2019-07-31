package com.example.quizflix.utils

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.quizflix.R
import com.example.quizflix.ui.ContactActivity
import com.example.quizflix.ui.NewsActivity
import com.example.quizflix.ui.categories.CategoriesActivity
import com.example.quizflix.ui.home.HomeActivity
import com.example.quizflix.ui.profile.ProfileActivity
import kotlinx.android.synthetic.main.bottom_navigation_view.*


abstract class MainActivity(val navNumber: Int) : AppCompatActivity() {

   private val TAG = "MainActivity"

    fun setupBottomNavigation() {
        bottom_navigation_view.setIconSize(29f, 29f)
        bottom_navigation_view.enableItemShiftingMode(false)
        bottom_navigation_view.setTextVisibility(false)
        bottom_navigation_view.enableShiftingMode(false)
        bottom_navigation_view.enableAnimation(false)
        for (i in 0 until bottom_navigation_view.menu.size()) {
            bottom_navigation_view.setIconTintList(i, null)
        }
        bottom_navigation_view.setOnNavigationItemSelectedListener {
            val nextActivity =
                when (it.itemId) {
                    R.id.nav_item_home -> HomeActivity::class.java
                    R.id.nav_item_categories -> CategoriesActivity::class.java
                    R.id.nav_item_profile -> ProfileActivity::class.java
                    else -> {
                        Log.e(TAG,"unknown nav item clicket $it")
                        null
                    }
                }
            if (nextActivity != null) {
                val intent = Intent(this, nextActivity)
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                startActivity(intent)
                overridePendingTransition(0,0)
                true
            } else {
                false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(bottom_navigation_view != null){
            bottom_navigation_view.menu.getItem(navNumber).isChecked = true

        }
    }



}

