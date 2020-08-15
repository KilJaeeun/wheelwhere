package com.example.wheelwhere

import com.google.gson.Gson
import java.io.File
import java.io.Serializable

class Place(
    var id: Int? = 0,
    var name: String,
    var is_toilet: Boolean = false,
    var is_elibator: Boolean = false,
    var is_parking: Boolean = false,
    var is_helper: Boolean = false,
    var address: String,
    var is_tuck: Boolean = false,
    var description: String,
    var latitude: String,
    var longitude: String,
    var star: String,
    var author: Any? = null
) : Serializable

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