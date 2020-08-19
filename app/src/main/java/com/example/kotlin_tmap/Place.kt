package com.example.kotlin_tmap


import com.google.gson.Gson
import java.io.File
import java.io.Serializable

class Place(
    var id: Int? = 0,
    var name: String,
    var is_helper: Boolean = false,
    var is_parking: Boolean = false,
    var is_tuck: Boolean = false,
    var is_elibator: Boolean = false,
    var is_toilet: Boolean = false,
    var description: String,
    var latitude: String,
    var longitude: String,
    var address: String,
    var star: String,
    var author: Any? = null
) : Serializable

fun main(args: Array<String>) {

    var buff = StringBuffer()
    File("place.json").forEachLine { buff.append(it) }

    val gson = Gson()


    println(buff)
}