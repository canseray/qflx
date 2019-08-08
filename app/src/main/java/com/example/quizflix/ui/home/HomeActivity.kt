package com.example.quizflix.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.quizflix.R
import com.example.quizflix.ui.login.LoginActivity
import com.example.quizflix.utils.MainActivity
import com.example.quizflix.utils.UniversalImageLoader
import com.google.firebase.auth.FirebaseAuth
import com.nostra13.universalimageloader.core.ImageLoader

class HomeActivity : MainActivity(0) {

    lateinit var mAuth : FirebaseAuth
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener

    private val TAG = "HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Log.d(TAG,"onCreate")

        setupBottomNavigation()

        setupAuthListener()
        mAuth = FirebaseAuth.getInstance()


        initImageLoader()

    }

    private fun initImageLoader() {

        var universalImageLoader = UniversalImageLoader(this)
        ImageLoader.getInstance().init(universalImageLoader.config)

    }

    private fun setupAuthListener(){
        mAuthListener = object : FirebaseAuth.AuthStateListener{
            override fun onAuthStateChanged(p0: FirebaseAuth) {
                var user = FirebaseAuth.getInstance().currentUser

                if (user == null){
                    var intent = Intent(this@HomeActivity, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()

                } else {

                }
            }

        }
    }




    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener (mAuthListener)
    }





    override fun onStop() {
        super.onStop()
        if (mAuthListener != null){
            mAuth.removeAuthStateListener (mAuthListener)
        }
    }
}
