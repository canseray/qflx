package com.example.quizflix.ui.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.quizflix.R
import com.example.quizflix.models.Users
import com.example.quizflix.utils.EventbusDataEvents
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_register.*
import org.greenrobot.eventbus.EventBus

class RegisterActivity : AppCompatActivity() , FragmentManager.OnBackStackChangedListener {

    lateinit var manager : FragmentManager
    lateinit var mAuth: FirebaseAuth
    lateinit var mRef: DatabaseReference
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
        mRef = FirebaseDatabase.getInstance().reference
        manager = supportFragmentManager
        manager.addOnBackStackChangedListener(this)

        progressBar = register_activity_progress

        progressBar.visibility = View.INVISIBLE

        init()
    }


    private fun init() {
        login_account.setOnClickListener {
            var intent = Intent(this,LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }
        register_email_edittext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length >= 10) {
                    next_button.isEnabled = true
                    next_button.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
                } else {
                    next_button.isEnabled = false
                    next_button.setTextColor(ContextCompat.getColor(applicationContext, R.color.red))
                }
            }
        })
        next_button.setOnClickListener {

            progressBar.visibility = View.VISIBLE


            if (isValidEmail(register_email_edittext.text.toString())) {

                var emailKullanimdaMi=false

                mRef.child("Users").addListenerForSingleValueEvent(object  : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError?) {
                    }

                    override fun onDataChange(p0: DataSnapshot?) {

                        if (p0!!.getValue() != null){

                            for (user in p0!!.children){

                                var okunanKullanici= user.getValue(Users::class.java)
                                if (okunanKullanici!!.email!!.equals(register_email_edittext.text.toString())){
                                    Toast.makeText(this@RegisterActivity,"Email Kullanımda",Toast.LENGTH_SHORT).show()
                                    emailKullanimdaMi=true
                                    break
                                }
                            }

                            if (emailKullanimdaMi == false){
                                loginRoot.visibility = View.GONE
                                loginContainer.visibility = View.VISIBLE
                                var transaction = supportFragmentManager.beginTransaction()
                                transaction.replace(R.id.loginContainer, RegisterFragment())
                                transaction.addToBackStack("email fragmente gecir register")
                                transaction.commit()
                                EventBus.getDefault()
                                    .postSticky(EventbusDataEvents.sendEmailAnotherScreen(register_email_edittext.text.toString()))
                            }
                        }

                        else {
                            loginRoot.visibility = View.GONE
                            loginContainer.visibility = View.VISIBLE
                            var transaction = supportFragmentManager.beginTransaction()
                            transaction.replace(R.id.loginContainer, RegisterFragment())
                            transaction.addToBackStack("email fragmente gecir register")
                            transaction.commit()
                            EventBus.getDefault()
                                .postSticky(EventbusDataEvents.sendEmailAnotherScreen(register_email_edittext.text.toString()))
                        }
                    }
                })
            } else {
                Toast.makeText(this,"Lütfen geçerli e-mail adresi giriniz",Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE

            }
        }
    }







    override fun onBackPressed() {
        loginRoot.visibility = View.VISIBLE
        super.onBackPressed()
    }






    override fun onBackStackChanged() {
        val nextedCount = manager.backStackEntryCount
        if (nextedCount==0){
            loginRoot.visibility = View.VISIBLE
        }
    }






    fun isValidEmail(emailToControl:String):Boolean{
        if(emailToControl==null){
            return false
        }
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailToControl).matches()
    }



}










