package com.example.quizflix.ui.profile


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quizflix.R
import kotlinx.android.synthetic.main.fragment_profile_edit.view.*

class ProfileEditFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile_edit, container, false)
        // view'ın icinde layout bilesenleri var,islemleri burda yap

        view.back_button.setOnClickListener {
            activity?.onBackPressed()
        }

        return view
    }


}
