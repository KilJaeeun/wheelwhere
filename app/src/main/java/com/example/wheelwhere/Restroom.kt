package com.example.wheelwhere

import com.google.gson.Gson
import java.io.File

fun main(args: Array<String>) {
    //val raw_data = File("restroom_edit.json")

    /*
    val jsonList =
        """[{"title":"Kotlin Tutorial #1","author":"bezkoder","categories":["Kotlin, Basic"]},
			{"title":"Kotlin Tutorial #2","author":"bezkoder","categories":["Kotlin, Practice"]}]"""

    print(jsonList)
    */

    var buff = StringBuffer()
    File("restroom_edit.json").forEachLine { buff.append(it) }

    val gson = Gson()
    

    //println(buff)

    //println(raw_data)
}

class Restroom(
    var type: String,
    var name: String,
    var address: String,
    var oldAddress: String,
    var isUniSex: String,
    var maleBig: String,
    var maleSmall: String,
    var femaleBig: String,
    var org: String,
    var contact: String,
    var time: String,
    var est: String,
    var lat: String,
    var lon: String,
    var date: String
) {
}