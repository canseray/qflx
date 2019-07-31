package com.example.quizflix.models


class User {
    var Email: String? = null
    var Password: String? = null
    var ProfilImage: String? = null
    var UserID: Int? = null
    var Username: String? = null

    constructor(){

    }

    constructor(Email: String?, Password: String?, ProfilImage: String?, UserID: Int?, Username: String?) {
        this.Email = Email
        this.Password = Password
        this.ProfilImage = ProfilImage
        this.UserID = UserID
        this.Username = Username
    }
}



