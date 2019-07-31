package com.example.quizflix.models

class Categories {

    var CategoryName : String? = null
    var CategoryID : Int? = null
    var CategoryImage : String? = null


    constructor(){

    }

    constructor(CategoryID: Int?, CategoryImage: String?, CategoryName: String?) {
        this.CategoryID = CategoryID
        this.CategoryImage = CategoryImage
        this.CategoryName = CategoryName
    }




}


