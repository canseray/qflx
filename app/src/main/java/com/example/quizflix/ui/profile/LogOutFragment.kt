package com.example.quizflix.ui.profile


import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.example.quizflix.R
import com.google.firebase.auth.FirebaseAuth


class LogOutFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var alert = AlertDialog.Builder(this!!.activity!!)
            .setTitle("Quizflix'ten çıkıp yap")
            .setMessage("Emin misiniz?")
            .setIcon(R.drawable.ic_cry)
            .setPositiveButton("çıkış yap", object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    FirebaseAuth.getInstance().signOut()
                    activity!!.finish()
                }
            })

            .setNegativeButton("İptal", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dismiss()
                }
            }).create()

        return alert
    }
}
