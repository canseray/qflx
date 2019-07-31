package com.example.quizflix.ui.home

import android.os.Bundle
import android.util.Log
import com.example.quizflix.R
import com.example.quizflix.utils.MainActivity
import com.example.quizflix.utils.UniversalImageLoader
import com.nostra13.universalimageloader.core.ImageLoader

class HomeActivity : MainActivity(0) {

    private val TAG = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Log.d(TAG,"onCreate")
        setupBottomNavigation()

        initImageLoader()

    }

    private fun initImageLoader() {

        var universalImageLoader = UniversalImageLoader(this)
        ImageLoader.getInstance().init(universalImageLoader.config)

    }
}
