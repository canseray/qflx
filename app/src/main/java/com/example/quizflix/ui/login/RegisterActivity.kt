package com.example.quizflix.ui.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import com.example.quizflix.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    //register_username_edittext
    //register_email_edittext
    //register_password_edittext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        login_account.setOnClickListener {
            intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        register_username_edittext.addTextChangedListener(watcher)
        register_email_edittext.addTextChangedListener(watcher)
        register_password_edittext.addTextChangedListener(watcher)
    }

    var watcher : TextWatcher = object : TextWatcher{
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if(s!!.length>5){
                if (register_username_edittext.text.toString().length>5 &&
                        register_email_edittext.text.toString().length>5 &&
                        register_password_edittext.text.toString().length>5){

                    register_button.isEnabled=true
                    register_button.setTextColor(ContextCompat.getColor(applicationContext,R.color.white))


                }else{
                    register_button.isEnabled=false
                    register_button.setTextColor(ContextCompat.getColor(applicationContext,R.color.grey))
                }

            }else{
                register_button.isEnabled=false
                register_button.setTextColor(ContextCompat.getColor(applicationContext,R.color.grey))
            }
        }

    }
}
