package com.example.quizflix.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.quizflix.R
import com.example.quizflix.models.Questions
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_quiz.*


class QuizActivity : AppCompatActivity() {

    lateinit var toOpenQuestionID : String
    private val TAG = "QuestionActivity"
    lateinit var mRecyclerView : RecyclerView
    lateinit var mDatabase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        Log.d(TAG,"onCreate")


        toOpenQuestionID = intent.extras?.get("CategoryID").toString()

        mDatabase = FirebaseDatabase.getInstance().getReference("Questions")
        mRecyclerView = findViewById(R.id.question_recyclerview)
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        loadQuestionsRecyclerView(toOpenQuestionID)

        Toast.makeText(this,"categori no " + toOpenQuestionID,Toast.LENGTH_SHORT).show()


    }

    private fun loadQuestionsRecyclerView(toOpenQuestionID: String) {

    val query = mDatabase.orderByChild("CategoryID").equalTo(toOpenQuestionID.toDouble())

        val option = FirebaseRecyclerOptions.Builder<Questions>()
            .setQuery(query, Questions::class.java)
            .setLifecycleOwner(this)
            .build()


        val FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Questions, QuestionsViewHolder>(option) {

            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): QuestionsViewHolder {

                val itemView = LayoutInflater.from(this@QuizActivity).inflate(R.layout.question_item,p0,false)
                return QuestionsViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: QuestionsViewHolder, position: Int, model: Questions) {
              // val placeid = getRef(position).key.toString()

                query.addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        Log.d(TAG,"onCanceled")
                    }

                    override fun onDataChange(p0: DataSnapshot) {

                        holder.questionText.setText(model.QuestionText)
                        holder.answerA.setText(model.AnswerA)
                        holder.answerB.setText(model.AnswerB)
                        holder.answerC.setText(model.AnswerC)
                        holder.answerD.setText(model.AnswerD)
                      //  holder.categoryName.setText(model.QuestionTitle)
                        Log.d(TAG,"onDataChange")
                    }

                })



            }
        }

        mRecyclerView.adapter = FirebaseRecyclerAdapter
        FirebaseRecyclerAdapter.startListening()
    }

    class QuestionsViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!){

        internal var questionText : TextView = itemView!!.findViewById<TextView>(R.id.question_textview)
        internal var answerA : TextView = itemView!!.findViewById<TextView>(R.id.a_answer)
        internal var answerB : TextView = itemView!!.findViewById<TextView>(R.id.b_answer)
        internal var answerC : TextView = itemView!!.findViewById<TextView>(R.id.c_answer)
        internal var answerD : TextView = itemView!!.findViewById<TextView>(R.id.d_answer)
        //internal var categoryName : TextView = itemView!!.findViewById<TextView>(R.id.category_name_textview)

    }

}

//question_recyclerview
//category_name_textview


//question_item (layout)
//question_textview
//a_answer


