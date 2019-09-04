package com.example.quizflix.ui.categories

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
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
import com.example.quizflix.models.Questions
import com.example.quizflix.ui.QuizActivity
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_category_info.view.*
import org.w3c.dom.Text

class CategoryInfoActivity : AppCompatActivity() {

    lateinit var toOpenInfoCategoryID : String
    lateinit var mAuth : FirebaseAuth
    lateinit var mAuthListener : FirebaseAuth.AuthStateListener
    private val TAG = "CategoryInfoActivity"
    lateinit var mRecyclerView : RecyclerView
    lateinit var mDatabase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_info)
        //setupAuthListener()

        mAuth = FirebaseAuth.getInstance()

        toOpenInfoCategoryID = intent.extras?.get("CategoryID").toString()

        Log.d(TAG,"onCreate")

        mDatabase = FirebaseDatabase.getInstance().getReference("Categories")
        mRecyclerView = findViewById(R.id.category_info_recyclerview)
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        loadCategoryInfoRecyclerView(toOpenInfoCategoryID)

        Toast.makeText(this,"categori no " + toOpenInfoCategoryID, Toast.LENGTH_SHORT).show()

    }

    private fun loadCategoryInfoRecyclerView(toOpenInfoCategoryID: String){
        val query = mDatabase.orderByChild("CategoryID").equalTo(toOpenInfoCategoryID.toDouble())

            val option = FirebaseRecyclerOptions.Builder<Categories>()
                .setQuery(query,Categories::class.java)
                .setLifecycleOwner(this)
                .build()

            val FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Categories, CategoriesInfoViewHolder>(option){
                override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CategoriesInfoViewHolder {

                    val itemView = LayoutInflater.from(this@CategoryInfoActivity).inflate(R.layout.item_category_info,p0,false)
                    return CategoriesInfoViewHolder(itemView)
                }

                override fun onBindViewHolder(holder: CategoriesInfoViewHolder, position: Int, model: Categories) {

                    holder.itemView.back_button.setOnClickListener { view ->

                    }

                    holder.itemView.start_cardview.setOnClickListener { view->

                        intent = Intent(this@CategoryInfoActivity,QuizActivity::class.java)
                        intent.putExtra("CategoryID",model.CategoryID).toString()
                        startActivity(intent)
                    }

                    query.addValueEventListener(object : ValueEventListener{
                        override fun onCancelled(p0: DatabaseError?) {
                            Log.d(TAG,"oncanceled")
                        }

                        override fun onDataChange(p0: DataSnapshot?) {

                            holder.categoryInfoInfo.setText(model.CategoryInfo)
                            holder.categoryInfoName.setText(model.CategoryName)
                            Picasso.get().load(model.CategoryImage).into(holder.categoryInfoImg)
                        }

                    })
                }

            }

        mRecyclerView.adapter = FirebaseRecyclerAdapter
        FirebaseRecyclerAdapter.startListening()
    }



    class CategoriesInfoViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!){

        internal var backButton : ImageView = itemView!!.findViewById(R.id.back_button)
        internal var categoryInfoName : TextView = itemView!!.findViewById(R.id.category_info_name)
        internal var categoryInfoImg : ImageView = itemView!!.findViewById(R.id.category_info_img)
        internal var categoryInfoInfo : TextView = itemView!!.findViewById(R.id.category_info_info)
        internal var startCardView : CardView = itemView!!.findViewById(R.id.start_cardview)

    }

    /*
    * back_button
    * category_info_name
    * category_info_img
    * category_info_info
    * start_cardview
    * */
}











