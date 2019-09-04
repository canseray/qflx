package com.example.quizflix.ui.profile


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.quizflix.R
import com.example.quizflix.models.Users
import com.example.quizflix.utils.FirebaseUtils.Companion.mAuth
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile_edit.*
import kotlinx.android.synthetic.main.fragment_profile_edit.view.*
import kotlinx.android.synthetic.main.fragment_profile_edit.view.circleProfileImage
import java.lang.Exception

class ProfileEditFragment : Fragment() {

    lateinit var circleProfileImageFragment: CircleImageView
    lateinit var mUser : FirebaseUser
    lateinit var mDatabase : DatabaseReference
    lateinit var mAuth : FirebaseAuth
    lateinit var mStorage: StorageReference
    val RESIM_SEC = 100
    var profilImageUri : Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile_edit, container, false)
        // view'ın icinde layout bilesenleri var,islemleri burda yap
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference
        mStorage = FirebaseStorage.getInstance().reference
        mUser = mAuth.currentUser!!

        //progressBar.visibility = View.INVISIBLE

        getUserInfo(view)

        view.back_button.setOnClickListener {
            activity?.onBackPressed()
        }

        view.tvFotografiDegistir.setOnClickListener {

            var intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_PICK)
            startActivityForResult(intent,RESIM_SEC)

        }

        view.degisiklikleriKaydet.setOnClickListener {
            if (profilImageUri != null){
                var downloadDialog = DownloadFragment()
                downloadDialog.show(activity!!.supportFragmentManager,"downloadFragmenti")
                downloadDialog.isCancelable=false

                var uploadTask = mStorage.child("Users").child(mUser.uid).child("userID").child(profilImageUri!!.lastPathSegment)
                    .putFile(profilImageUri!!)
                    .addOnCompleteListener(object : OnCompleteListener<UploadTask.TaskSnapshot>{
                        override fun onComplete(p0: Task<UploadTask.TaskSnapshot>) {
                            if (p0!!.isSuccessful){
                                // Toast.makeText(activity,"Resim yüklendi"+p0!!.getResult().downloadUrl.toString(),Toast.LENGTH_SHORT).show()
                                mDatabase.child("Users").child(mUser.uid).child("profilImage")
                                    .setValue(p0!!.getResult().downloadUrl.toString())

                                downloadDialog.dismiss()
                                userNameInfoUpload(view,true)
                            }
                        }

                    })

                    .addOnFailureListener(object : OnFailureListener{
                        override fun onFailure(p0: Exception) {
                            userNameInfoUpload(view,false)
                        }

                    })

            } else {
                userNameInfoUpload(view,null)
            }
        }

        return view
    }

    private fun getUserInfo(view: View?){
        mDatabase.child("Users").child(mUser!!.uid).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {
                var userInfo = p0!!.getValue(Users::class.java)

                view!!.etUserName.setText(userInfo!!.username)
                view!!.etUserEmail.setText(userInfo!!.email)
                view!!.etUserPassword.setText(userInfo!!.password)

                var img = userInfo!!.profilImage

                Picasso.get().load(img!!).into(view.circleProfileImage)
            }

        })

    }


    //kullanıcı adını guncelle
    private fun userNameInfoUpload(view: View, profilImageChange: Boolean?){

        if(!mDatabase.child("Users").child(mUser.uid).child("username").equals(view.etUserName.text.toString())){
            if (view.etUserName.text.toString().toString().length>5){
                mDatabase.child("Users").orderByChild("username").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError?) {

                    }

                    override fun onDataChange(p0: DataSnapshot?) {
                        var usernameIsUsing = false //kullanımdamı

                        for (ds in p0!!.children){
                            var readingUsername = ds!!.getValue(Users::class.java)!!.username

                            if (readingUsername!!.equals(view.etUserName.text.toString())){
                                usernameIsUsing = true
                                uploadUserInfo(view,profilImageChange,false)
                                break
                            }
                        }

                        if (usernameIsUsing == false){
                            mDatabase.child("Users").child(mUser.uid).child("username").setValue(view.etUserName.text.toString())
                            uploadUserInfo(view,profilImageChange,true)
                        }
                    }

                })
            } else {
                Toast.makeText(activity,"Kullanıcı adı en az 6 karakter olmalıdır",Toast.LENGTH_SHORT).show()

            }

        } else {
            uploadUserInfo(view,profilImageChange,null)
        }

    }


    //profılbılgılerınıguncelle
    private fun uploadUserInfo(view: View, profilImageChange: Boolean? , userNameChange: Boolean?){

        var profilUpload: Boolean? = null //profılguncellelndımı

        if (!mDatabase.child("Users").child(mUser.uid).child("password").equals(view.etUserPassword.text.toString())){
            if (!view.etUserPassword.text.toString().trim().isNullOrEmpty()){
                mDatabase.child("Users").child(mUser.uid).child("password").setValue(view.etUserPassword.text.toString())
                profilUpload = true
            } else {
                Toast.makeText(activity,"şifre alanı bos olamaz",Toast.LENGTH_SHORT).show()
            }
        }

        if (profilImageChange == null && userNameChange == null && profilUpload == null){
            Toast.makeText(activity,"Değişiklik Yok",Toast.LENGTH_SHORT).show()

        } else if (userNameChange == false && (profilUpload == true || profilImageChange == true)){
            Toast.makeText(activity,"Bilgiler güncelledi ama kullanıcı adı KULLANIMDA",Toast.LENGTH_SHORT).show()

        } else{
            Toast.makeText(activity,"Kullanıcı Güncellendi",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RESIM_SEC && resultCode == AppCompatActivity.RESULT_OK && data!!.data != null){

            profilImageUri = data!!.data!!

            circleProfileImage.setImageURI(profilImageUri)
        }
    }


}
