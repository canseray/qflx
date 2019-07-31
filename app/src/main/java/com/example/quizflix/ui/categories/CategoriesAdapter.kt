//package com.example.quizflix.ui
//
//import android.support.v7.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import com.example.quizflix.R
//import com.example.quizflix.models.Categories
//import com.squareup.picasso.Picasso
//
//class CategoriesAdapter (val categoriesList: ArrayList<Categories>) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>(){
//
//    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
//        val v = LayoutInflater.from(p0?.context).inflate(R.layout.cardview_item_categories,p0,false)
//        return ViewHolder(v)
//
//    }
//
//    override fun getItemCount(): Int {
//        return categoriesList.size
//    }
//
//    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
//        val categories: Categories = categoriesList[p1]
//
//        p0?.categoryNameTextView.text = categories.CategoryName
//
//        Picasso.get()
//            .load(categories.CategoryImage)
//            //.placeholder(android.R.drawable.)
//            //.error("")
//            .into(p0.categoryImageImageView)
//
//
//
//    }
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val categoryNameTextView = itemView.findViewById(R.id.categories_textview) as TextView
//        val categoryImageImageView = itemView.findViewById(R.id.categories_image) as ImageView
//
//    }
//}