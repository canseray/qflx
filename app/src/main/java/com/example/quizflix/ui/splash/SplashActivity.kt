package com.example.quizflix.ui.splash

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.net.ConnectivityManager
import com.example.quizflix.R
import com.example.quizflix.ui.home.HomeActivity
import com.example.quizflix.ui.login.LoginActivity
import com.example.quizflix.utils.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference


class SplashActivity : AppCompatActivity() {


    lateinit var mAuth : FirebaseAuth
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener



    fun networkConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }



    fun gecerliKullaniciVarMi(){
        mAuthListener = object : FirebaseAuth.AuthStateListener{
            override fun onAuthStateChanged(p0: FirebaseAuth) {
                var user = FirebaseAuth.getInstance().currentUser

                if (user != null) {
                    var intent = Intent(this@SplashActivity, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    var intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)

                }

            }
        }

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        mAuth = FirebaseAuth.getInstance()

        init()


    }


    fun init(){

        //uc saniye delay ekle

        if (networkConnection(this)==true){
            gecerliKullaniciVarMi()
        } else {
            var intent = Intent(this,NetworkErrorActivity::class.java)
            startActivity(intent)
            finish()
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
