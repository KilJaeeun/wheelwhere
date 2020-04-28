package com.example.kotlin_tmap.data.mapData

import com.example.kotlin_tmap.data.MapPoint

object MapPointRepository : MapPointInterface{

    private val MAP_POINT_DATA : MapPointData by lazy {
        MapPointData()
    }

    override fun mapPointList(pointList: (List<MapPoint>) -> Unit, size: Int) {

    }

    override fun addList(pointList: (List<MapPoint>) -> Unit, size: Int) {
        MAP_POINT_DATA.addList(pointList, size)

    }

}