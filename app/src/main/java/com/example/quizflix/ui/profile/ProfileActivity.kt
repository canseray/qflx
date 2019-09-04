package com.example.quizflix.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.quizflix.R
import com.example.quizflix.models.Users
import com.example.quizflix.ui.login.LoginActivity
import com.example.quizflix.utils.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : MainActivity(2) {

    lateinit var mAuth : FirebaseAuth
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    lateinit var mDatabase : DatabaseReference
    lateinit var mUser : FirebaseUser


    private val TAG = "ProfileActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupBottomNavigation()
        Log.d(TAG,"onCreate")
        setupAuthListener()
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference
        mUser = mAuth.currentUser!!
        setupOptions()


        getUserInfo()


    }

    fun setupOptions(){
        options_profile.setOnClickListener{
            var intent = Intent(this,ProfileOptionsActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivityForResult(intent,2)
        }
    }


    private fun setupAuthListener(){
        mAuthListener = object : FirebaseAuth.AuthStateListener{
            override fun onAuthStateChanged(p0: FirebaseAuth) {
                var user = FirebaseAuth.getInstance().currentUser

                if (user == null){
                    var intent = Intent(this@ProfileActivity, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()
                } else {

                }
            }

        }
    }

    private fun getUserInfo(){
        mDatabase.child("Users").child(mUser!!.uid).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {

                var userInfo = p0!!.getValue(Users::class.java)


                quizflixpuan.setText(userInfo!!.quizPoint)
                usernametext_profile.setText(userInfo!!.username)
                quizTitle.setText(userInfo!!.userTitle)
                Picasso.get().load(userInfo!!.userTitleImage).into(quizTitleImg)
                Picasso.get().load(userInfo!!.profilImage).into(profile_image)

            }

        })
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
