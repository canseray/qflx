package com.example.quizflix.ui.login


import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.example.quizflix.R
import com.example.quizflix.models.Users
import com.example.quizflix.ui.home.HomeActivity
import com.example.quizflix.utils.EventbusDataEvents
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class RegisterFragment : Fragment() {

    //progressbar?
    //videouda telefonkodufragmentte kulandıgı progressbarı registerActivityde kullanıp
    //email kontrolu esnasında calıstır

    var comeEmail = ""
    lateinit var progressbar : ProgressBar
    lateinit var mAuth : FirebaseAuth
    lateinit var mRef:DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        var view= inflater!!.inflate(R.layout.fragment_register, container, false)

        view.email_move_text.setText(comeEmail)

        progressbar = view.register_fragment_progress

        view.login_account.setOnClickListener {
            var intent = Intent(activity,LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        mAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser !=null){
            mAuth.signOut()
        }
        //geçerli kullanıcı varsa signout yap da deneyelım yenısını

        mRef = FirebaseDatabase.getInstance().reference

        view.register_username_edittext.addTextChangedListener(watcher)
        view.register_password_edittext.addTextChangedListener(watcher)

        view.next_button.setOnClickListener {

            progressbar.visibility = View.VISIBLE

            var userNameKullanimdaMi = false
            mRef.child("Users").addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError?) {

                }

                override fun onDataChange(p0: DataSnapshot?) {

                    if (p0!!.getValue() != null){

                        for (user in p0!!.children){
                            var okunanKullanici = user.getValue(Users::class.java)
                            if (okunanKullanici!!.username!!.equals(view.register_username_edittext.text.toString())){
                                Toast.makeText(activity,"Kullanıcı adı kullanımda",Toast.LENGTH_SHORT).show()
                                userNameKullanimdaMi=true
                                progressbar.visibility = View.INVISIBLE
                                break
                            }
                        }

                        if (userNameKullanimdaMi == false){


                            var parola = view.register_password_edittext.text.toString()
                            var kullaniciAdi = view.register_username_edittext.text.toString()
                            var profilFoto = (R.drawable.ic_contact).toString()
                            var quizPuani = " 0 "
                            var quizRutbesi = "çaylak"
                            var quizRutbeImage = (R.drawable.ic_categories).toString()



                            mAuth.createUserWithEmailAndPassword(comeEmail,parola)
                                .addOnCompleteListener ( object : OnCompleteListener<AuthResult>{
                                    override fun onComplete(p0: Task<AuthResult>) {
                                        if (p0!!.isSuccessful){
                                            Toast.makeText(activity,"oturum acildi",Toast.LENGTH_SHORT).show()

                                            //oturum acan kullanicinin verilerini db e kaydedelim

                                            var userID = mAuth.currentUser!!.uid.toString()

                                            var kaydedilecekKullanici = Users(comeEmail,parola,profilFoto,userID,kullaniciAdi,quizPuani,quizRutbesi,quizRutbeImage)

                                            mRef.child("Users").child(userID).setValue(kaydedilecekKullanici)
                                                .addOnCompleteListener(object : OnCompleteListener<Void>{
                                                    override fun onComplete(p0: Task<Void>) {
                                                        if (p0!!.isSuccessful){
                                                            Toast.makeText(activity,"kullanici kaydedildi",Toast.LENGTH_SHORT).show()
                                                            progressbar.visibility = View.INVISIBLE
                                                            var intent = Intent(activity,HomeActivity::class.java)
                                                            startActivity(intent)

                                                        } else {

                                                            mAuth.currentUser!!.delete()
                                                                .addOnCompleteListener(object  : OnCompleteListener<Void>{
                                                                    override fun onComplete(p0: Task<Void>) {
                                                                        if (p0!!.isSuccessful){
                                                                            Toast.makeText(activity,"kullanici kaydedilmedi",Toast.LENGTH_SHORT).show()
                                                                            progressbar.visibility = View.INVISIBLE


                                                                        }
                                                                    }

                                                                })
                                                        }
                                                    }

                                                })




                                        } else {
                                            Toast.makeText(activity,"oturum acilamadi",Toast.LENGTH_SHORT).show()
                                            progressbar.visibility = View.INVISIBLE


                                        }
                                    }

                                })


                        }
                    }
                }

            })

        }

        return view

    }




    var watcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if(s!!.length > 5){
                if (register_username_edittext.text.toString().length>5 && register_password_edittext.text.toString().length>5){
                    next_button.isEnabled = true
                    next_button.setTextColor(ContextCompat.getColor(activity!!, R.color.white))
                } else {
                    next_button.isEnabled = false
                    next_button.setTextColor(ContextCompat.getColor(activity!!, R.color.red))
                }
            } else {
                next_button.isEnabled = false
                next_button.setTextColor(ContextCompat.getColor(activity!!, R.color.red))


            }
        }

    }






    //////////event bus/////////////

    @Subscribe(sticky = true)
    internal fun emailEventBus(createAccountData : EventbusDataEvents.sendEmailAnotherScreen){

        comeEmail = createAccountData.sendEmail!!
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

    //////////event bus////////////


}
