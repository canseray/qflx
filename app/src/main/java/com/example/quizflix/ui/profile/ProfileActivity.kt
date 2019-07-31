package com.example.quizflix.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.quizflix.R
import com.example.quizflix.utils.MainActivity
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : MainActivity(2) {

    private val TAG = "ProfileActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupBottomNavigation()
        Log.d(TAG,"onCreate")
        setupOptions()
    }

    fun setupOptions(){
        options_profile.setOnClickListener{
            var intent = Intent(this,ProfileOptionsActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivityForResult(intent,2)
        }
    }
}
