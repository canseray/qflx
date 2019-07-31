package com.example.quizflix.models

class Questions {
    var AnswerA: String? = null
    var AnswerB: String? = null
    var AnswerC: String? = null
    var AnswerD: String? = null
    var CategoryID: Int? = null
    var CorrectAnswer: String? = null
    var QuestionID: Int? = null
    var QuestionText: String? = null
    var QuestionTitle: String? = null


    constructor(){

    }

    constructor(
        AnswerA: String?,
        AnswerB: String?,
        AnswerC: String?,
        AnswerD: String?,
        CategoryID: Int?,
        CorrectAnswer: String?,
        QuestionID: Int?,
        QuestionText: String?,
        QuestionTitle: String?
    ) {
        this.AnswerA = AnswerA
        this.AnswerB = AnswerB
        this.AnswerC = AnswerC
        this.AnswerD = AnswerD
        this.CategoryID = CategoryID
        this.CorrectAnswer = CorrectAnswer
        this.QuestionID = QuestionID
        this.QuestionText = QuestionText
        this.QuestionTitle = QuestionTitle
    }




}