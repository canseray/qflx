package com.example.quizflix.ui.categories

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.quizflix.R
import com.example.quizflix.models.Categories
import com.example.quizflix.utils.MainActivity
import com.example.quizflix.ui.QuizActivity
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.squareup.picasso.Picasso


class CategoriesActivity : MainActivity(1) {

    private val TAG = "CategoriesActivity"
    lateinit var mRecyclerView : RecyclerView
    lateinit var mDatabase : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        setupBottomNavigation()
        Log.d(TAG,"onCreate")

//        val recyclerView = findViewById(R.id.categories_recyclerview) as RecyclerView
//
//        recyclerView.layoutManager = GridLayoutManager(this,2)
//
//        val categories = ArrayList<Categories>()
//
//        val adapter = CategoriesAdapter(categories)
//
//        recyclerView.adapter = adapter

        mDatabase = FirebaseDatabase.getInstance().getReference("Categories")
        mRecyclerView = findViewById(R.id.categories_recyclerview)
        mRecyclerView.layoutManager = GridLayoutManager(this,2)


        loadCategoriesRecyclerView()
    }

    private fun loadCategoriesRecyclerView(){

        val option = FirebaseRecyclerOptions.Builder<Categories>()
            .setQuery(mDatabase, Categories::class.java)
            .setLifecycleOwner(this)
            .build()

        val FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Categories, CategoriesViewHolder>(option){


            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CategoriesViewHolder {

                val itemView = LayoutInflater.from(this@CategoriesActivity).inflate(R.layout.cardview_item_categories,p0,false)
                return CategoriesViewHolder(itemView)

            }

            override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int, model: Categories) {
                val placeid = getRef(position).key.toString()

                holder.itemView.setOnClickListener{ view->
                    intent = Intent(this@CategoriesActivity, QuizActivity::class.java)
                    startActivity(intent)
                }

                mDatabase.child(placeid).addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(this@CategoriesActivity, "Error Occurred "+ p0.toException(), Toast.LENGTH_SHORT).show()
                        Log.d(TAG,"onCanceled")


                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        holder.categoryName.setText(model.CategoryName)
                        Picasso.get().load(model.CategoryImage).into(holder.categoryImage)
                        Log.d(TAG,"onDataChange")

                    }
                })
            }
        }

        mRecyclerView.adapter = FirebaseRecyclerAdapter
        FirebaseRecyclerAdapter.startListening()

    }

    class CategoriesViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!){

        internal var categoryName : TextView = itemView!!.findViewById<TextView>(R.id.categories_textview)
        internal var categoryImage : ImageView = itemView!!.findViewById<ImageView>(R.id.categories_image)

    }


}


