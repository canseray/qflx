package com.example.quizflix.models

class Categories {

    var CategoryName : String? = null
    var CategoryID : Int? = null
    var CategoryImage : String? = null
    var CategoryInfo : String? = null


    constructor(){

    }

    constructor(CategoryID: Int?, CategoryImage: String?, CategoryName: String? , CategoryInfo: String?) {
        this.CategoryID = CategoryID
        this.CategoryImage = CategoryImage
        this.CategoryName = CategoryName
        this.CategoryInfo = CategoryInfo
    }




}


