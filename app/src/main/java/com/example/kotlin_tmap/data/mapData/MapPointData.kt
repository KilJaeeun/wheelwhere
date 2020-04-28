package com.example.kotlin_tmap.data.mapData

import com.example.kotlin_tmap.data.MapPoint

class MapPointData : MapPointInterface{
    override fun mapPointList(pointList: (List<MapPoint>) -> Unit, size: Int) {
        val list = mutableListOf<MapPoint>()
        for(index in 1..size){

            val name =""
            val lat = 0.0
            val lon = 1.0

            list.add(MapPoint(name, lat, lon))
        }
        pointList(list)
    }

    override fun addList(pointList: (List<MapPoint>) -> Unit, size: Int) {
        val list = mutableListOf<MapPoint>()
        for (index in 1..size){

        }
        pointList(list)

    }

}