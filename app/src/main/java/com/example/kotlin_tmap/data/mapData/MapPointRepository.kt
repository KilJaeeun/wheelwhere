package com.example.kotlin_tmap.data.mapData

import android.content.Context
import com.example.kotlin_tmap.data.MapPoint
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView

object MapPointRepository : MapPointInterface{

    private val MAP_POINT_DATA : MapPointData by lazy {
        MapPointData()
    }

    override fun mapPointList(context: Context, mapView: TMapView, tmapPoint: TMapPoint)
        = MAP_POINT_DATA.mapPointList(context, mapView, tmapPoint)


    override fun addList(pointList: ArrayList<MapPoint>, size: Int)
            = MAP_POINT_DATA.addList(pointList, size)


}