package com.example.quizflix.ui.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.quizflix.R
import com.example.quizflix.models.Users
import com.example.quizflix.ui.home.HomeActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var mAuth : FirebaseAuth
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    lateinit var mRef : DatabaseReference




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        create_account.setOnClickListener {
            intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

     //   setupAuthListener()

            mAuth = FirebaseAuth.getInstance()
            mRef = FirebaseDatabase.getInstance().reference

        init()

    }



    fun init() {
            login_username_edittext.addTextChangedListener(watcher)
            login_password_edittext.addTextChangedListener(watcher)

            login_button.setOnClickListener {

                if (login_username_edittext.text.toString().length >=6 && login_password_edittext.text.toString().length >=6) {
                    progressBar3.visibility= View.VISIBLE

                    oturumAcacakKullaniciyiDenetle(
                        login_username_edittext.text.toString(),
                        login_password_edittext.text.toString()
                    )
                } else {
                    Toast.makeText(this,"Kullanıcı adı veya şifre en az 6 karakter olmalıdır",Toast.LENGTH_SHORT).show()

                }
            }
        }





    private fun oturumAcacakKullaniciyiDenetle(emailUsername:String , sifre: String){

        var kullaniciBulundu=false


        mRef.child("Users").orderByChild("email").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {

                if (p0!!.getValue() != null) {
                    for (ds in p0!!.children) {

                        var okunanKullanici = ds.getValue(Users::class.java)


                        if (!okunanKullanici!!.email!!.isNullOrEmpty() && okunanKullanici!!.email!!.toString().equals(emailUsername)) {

                            oturumAc(okunanKullanici, sifre)
                            kullaniciBulundu = true

                            break


                        } else if (!okunanKullanici!!.username!!.isNullOrEmpty() && okunanKullanici!!.username!!.toString().equals(emailUsername)) {

                            oturumAc(okunanKullanici, sifre)
                            kullaniciBulundu = true

                            break

                        }
                    }

                    if (kullaniciBulundu == false) {
                        progressBar3.visibility = View.GONE
                        Toast.makeText(this@LoginActivity, "Kullanıcı Bulunamadı", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })

    }





    private fun oturumAc(okunanKullanici : Users, sifre : String){

        var girisYapacakEmail=okunanKullanici.email.toString()

        mAuth.signInWithEmailAndPassword(girisYapacakEmail,sifre)
            .addOnCompleteListener (object  : OnCompleteListener<AuthResult>{
                override fun onComplete(p0: Task<AuthResult>) {
                    if (p0!!.isSuccessful){

                        Toast.makeText(this@LoginActivity,"oturum açıldı" ,Toast.LENGTH_SHORT).show()
                        progressBar3.visibility= View.GONE

                        var intent = Intent(this@LoginActivity,HomeActivity::class.java)
                        startActivity(intent)


                    } else {

                        Toast.makeText(this@LoginActivity,"kullanıcı adı/Şifre hatalı",Toast.LENGTH_SHORT).show()
                        progressBar3.visibility= View.GONE

                        //buraya kadar username ve emaıl kontrol edıldı onlar hatalı olamaz

                    }
                }

            })

    }






    var watcher:TextWatcher=object  : TextWatcher{
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            if (login_username_edittext.text.toString().length>3 && login_password_edittext.text.toString().length>3){

                login_button.isEnabled = true
                login_button.setTextColor(ContextCompat.getColor(this@LoginActivity,R.color.white))

            } else {
                login_button.isEnabled = false
                login_button.setTextColor(ContextCompat.getColor(this@LoginActivity,R.color.red))
            }
        }

    }





    private fun setupAuthListener(){
        mAuthListener = object : FirebaseAuth.AuthStateListener{
            override fun onAuthStateChanged(p0: FirebaseAuth) {
                var user = FirebaseAuth.getInstance().currentUser

                if (user != null){
                    var intent = Intent(this@LoginActivity,HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                } else {

                }
            }

        }
    }




//    override fun onStart() {
//        super.onStart()
//        mAuth.addAuthStateListener (mAuthListener)
//    }
//
//
//
//
//
//    override fun onStop() {
//        super.onStop()
//        if (mAuthListener != null){
//            mAuth.removeAuthStateListener (mAuthListener)
//        }
//    }
}










