package com.example.kotlin_tmap.data.mapData

import android.content.Context
import com.example.kotlin_tmap.data.MapPoint
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView

interface MapPointInterface{

    fun mapPointList(context: Context, mapView: TMapView, tmapPoint: TMapPoint)

    fun addList(pointList: ArrayList<MapPoint>, size: Int)
}