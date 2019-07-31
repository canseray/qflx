package com.example.quizflix.ui.splash

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.net.ConnectivityManager
import com.example.quizflix.R
import com.example.quizflix.ui.login.LoginActivity
import com.example.quizflix.utils.MainActivity


class SplashActivity : AppCompatActivity() {

    private var currentUser : String? = null //test için yazdım firebase currentUser tanımlanıcak
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 3000

    internal val mRunnable: Runnable = Runnable {

        if (!isFinishing) {

            if (networkConnection(this) == true) {

                if (currentUser != null) {

                    val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()

                } else {
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
                }

            } else {

                val intent = Intent(applicationContext, NetworkErrorActivity::class.java)
             startActivity(intent)
                finish()

            }


        }
    }

    fun networkConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)


    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
}
