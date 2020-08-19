package com.example.kotlin_tmap.Async

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import android.util.Log
import com.example.kotlin_tmap.base.App
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapPolyLine
import com.skt.Tmap.TMapView
import java.lang.Exception

class FindPathTask : AsyncTask<TMapPoint, Void, Double>() {
    var context = App.instance.context()
    var tmapView = TMapView(context)

    fun FindPathTask(context: Context, tMapView: TMapView){ //RouteInfo에서 넘어올 context와 mapview
        this.context = context
        this.tmapView = tMapView
    }

    override fun onPostExecute(result: Double?) { 
        super.onPostExecute(result)
    }


    override fun doInBackground(vararg params: TMapPoint?): Double? { //미리 지정해둔 두 지점
        val tMapPointStart = TMapPoint(37.570841, 126.985302) //SKT타워
        val tMapPointEnd = TMapPoint(37.551135, 126.988205) //종각역

        var tmapPoly: TMapPolyLine? = null //tmapPoly 널 허용
        try {
            tmapPoly = TMapData().findPathData(tMapPointStart, tMapPointEnd) //지정해둔 두 지점으로 연결
            tmapPoly.lineColor = Color.BLUE
            tmapPoly.lineWidth = 2F

            //지도에 보여줌
            tmapView.addTMapPolyLine("Line", tmapPoly)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return tmapPoly?.distance


    }
}