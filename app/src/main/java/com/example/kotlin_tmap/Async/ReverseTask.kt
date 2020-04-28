package com.example.kotlin_tmap.Async

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.example.kotlin_tmap.base.App
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import java.lang.Exception

class ReverseTask : AsyncTask<TMapPoint, Void, String>() {

    var context = App.instance.context()
    var mapView = TMapView(context)
    private lateinit var address: String

    fun ReverseTask(context: Context, mapView: TMapView){
        this.context = context
        this.mapView = mapView


    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

    }

    override fun doInBackground(vararg params: TMapPoint?): String? {
        try {
            //받아온 주소 값의 위도 경도
            val latitude: Double = params[0]!!.latitude
            val longitude: Double = params[0]!!.longitude
            address = TMapData().convertGpsToAddress(latitude, longitude) //현재 보이는 곳의 정중앙의 좌표
            Log.d("주소 테스트",address)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    return address
    }
}