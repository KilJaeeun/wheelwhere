package com.example.kotlin_tmap.Async

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.example.kotlin_tmap.base.App
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView

class FindPoiTask : AsyncTask<String, Void, Void>() {

    var context = App.instance.context()
    var tmapView = TMapView(context)
    val tmapdata = TMapData()

    fun FindPoiTask(context: Context, tMapView: TMapView){
        this.context = context
        this.tmapView = tMapView
    }

    override fun doInBackground(vararg params: String?): Void? {
        tmapdata.findAllPOI(params[0], TMapData.FindAllPOIListenerCallback {it
            for (item in it){
                Log.d("주소로 찾기 : ", "POI name :  ${item.poiName}, POI Address : ${item.poiAddress}, POI point : ${item.poiPoint}")
            }
        })

        return null
    }
}