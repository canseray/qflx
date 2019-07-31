package com.example.quizflix.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseUtils{

    companion object {
        var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        var mUserDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
        var mCategoriesDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference("Categories")
        var mQuestionsDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference("Questions")
        var mStorage: StorageReference = FirebaseStorage.getInstance().reference
    }

}