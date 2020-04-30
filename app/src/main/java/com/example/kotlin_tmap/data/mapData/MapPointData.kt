package com.example.kotlin_tmap.data.mapData

import android.content.Context
import com.example.kotlin_tmap.Async.FindAroundTask
import com.example.kotlin_tmap.data.MapPoint
import com.example.kotlin_tmap.data.mapData.AsyncContract.MapPointContract
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView

class MapPointData : MapPointInterface, MapPointContract{
    override fun mapPointList(context: Context, mapView: TMapView, tmapPoint: TMapPoint) {
        val list = mutableListOf<MapPoint>()

       /* val asyncAround : FindAroundTask? = FindAroundTask() //asyncPath를 지정 하고 FindAroundTask init
        asyncAround!!.FindAroundTask(context,mapView) //context와 mapview 넘겨줌.
        asyncAround.execute(tmapPoint)*/


    }

    override fun addList(pointList: ArrayList<MapPoint>, size: Int) {
        val list = mutableListOf<MapPoint>()
    }

    override fun getDataList() {
    }

}