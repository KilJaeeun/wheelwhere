package com.example.kotlin_tmap.data.mapData

import com.example.kotlin_tmap.data.MapPoint

interface MapPointInterface{

    fun mapPointList(pointList : (List<MapPoint>) -> Unit, size : Int)

    fun addList(pointList : (List<MapPoint>) -> Unit, size : Int)
}