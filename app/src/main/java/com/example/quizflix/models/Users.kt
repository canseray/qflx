package com.example.quizflix.models


class Users {
    var email: String? = null
    var password: String? = null
    var profilImage: String? = null
    var userID: String? = null
    var username: String? = null
    var quizPoint: String? = null
    var userTitle: String? = null
    var userTitleImage: String? = null

    constructor(){

    }

    constructor(email: String?, password: String?, profilImage: String?, userID: String?, username: String?,
                quizPoint: String?, userTitle: String?, userTitleImage: String?) {
        this.email = email
        this.password = password
        this.profilImage = profilImage
        this.userID = userID
        this.username = username
        this.quizPoint = quizPoint
        this.userTitle = userTitle
        this.userTitleImage = userTitleImage
    }
}



