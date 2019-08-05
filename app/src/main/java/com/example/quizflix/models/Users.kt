package com.example.quizflix.models


class Users {
    var email: String? = null
    var password: String? = null
    var profilImage: String? = null
    var userID: String? = null
    var username: String? = null

    constructor(){

    }

    constructor(email: String?, password: String?, profilImage: String?, userID: String?, username: String?) {
        this.email = email
        this.password = password
        this.profilImage = profilImage
        this.userID = userID
        this.username = username
    }
}



