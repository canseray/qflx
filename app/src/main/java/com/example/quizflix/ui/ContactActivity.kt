package com.example.quizflix.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.quizflix.R
import com.example.quizflix.utils.MainActivity

class ContactActivity : AppCompatActivity() {

    private val TAG = "ContactActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        Log.d(TAG,"onCreate")

    }
}
