package com.example.quizflix.ui.home

import android.os.Bundle
import android.util.Log
import com.example.quizflix.R
import com.example.quizflix.utils.MainActivity

class HomeActivity : MainActivity(0) {

    private val TAG = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Log.d(TAG,"onCreate")
        setupBottomNavigation()

    }
}
