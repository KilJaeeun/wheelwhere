package com.example.kotlin_tmap


//Register.kt

import java.io.Serializable

class Register(
    var username: String? = null,
    var password1: String? = null,
    var password2: String? = null
) : Serializable
class LocationRegister(
    var id:Int?=null,
    var name: String? = null,
    var address: String? = null,
    var is_toilet:Boolean,
    var is_elibator:Boolean,
    var is_parking:Boolean,
    var is_helper:Boolean,
    var description: String? = null,
    var latitude: String? = null,
    var longitude: String? = null,
    var star: Float? = null
) : Serializable
class CommentRegister(
    var id:Int?=null,
    var post: Int? = null,
    var text: String? = null,
    var star: Float? = null
) : Serializable