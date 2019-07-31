package com.example.quizflix.ui.profile

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.quizflix.R
import kotlinx.android.synthetic.main.activity_profile_options.*


class ProfileOptionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_options)
        setupBackButton()
        setupFragmentNavigations()
        
    }

    fun setupBackButton(){
        back_button.setOnClickListener {
            intent = Intent(this,ProfileActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
    }

    fun setupFragmentNavigations(){
        option1_what.setOnClickListener {
            profileOptionsRoot.visibility = View.GONE
            var transaction =supportFragmentManager.beginTransaction()
            transaction.replace(R.id.profileOptionsContainer,AboutFragment())
            transaction.addToBackStack("abouteklendi")
            transaction.commit()
        }
        option2_editprofile.setOnClickListener {
            profileOptionsRoot.visibility = View.GONE
            var transaction =supportFragmentManager.beginTransaction()
            transaction.replace(R.id.profileOptionsContainer,ProfileEditFragment())
            transaction.addToBackStack("editprofileeklendi")
            transaction.commit()
        }
        option3_sendquestion.setOnClickListener {
            profileOptionsRoot.visibility = View.GONE
            var transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.profileOptionsContainer,ContactFragment())
            transaction.addToBackStack("contacteklendi")
            transaction.commit()
        }
        option4_changepassword.setOnClickListener {
            profileOptionsRoot.visibility = View.GONE
            var transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.profileOptionsContainer,ChangePasswordFragment())
            transaction.addToBackStack("changepassworkeklendi")
            transaction.commit()
        }
        option5_signout.setOnClickListener {
            profileOptionsRoot.visibility = View.GONE
            var transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.profileOptionsContainer,LogOutFragment())
            transaction.addToBackStack("logouteklendi")
            transaction.commit()
        }
    }

    override fun onBackPressed() {
        profileOptionsRoot.visibility = View.VISIBLE
        super.onBackPressed()
    }
}
