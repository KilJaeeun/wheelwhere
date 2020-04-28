package com.example.kotlin_tmap.Async

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.example.kotlin_tmap.base.App
import com.example.kotlin_tmap.data.MapPoint
import com.example.kotlin_tmap.data.mapData.MapPointRepository
import com.example.kotlin_tmap.map.SearchPOI
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.activity_search_p_o_i.*

class FindAroundTask(
    private val mapPointRepository: MapPointRepository
) : AsyncTask<TMapPoint, Void, ArrayList<MapPoint>>() {
    var context = App.instance.context()
    var tmapView = TMapView(context)
    val tmapdata = TMapData()

    var mapPoint : MapPoint?= null
    var poiList = ArrayList<MapPoint>()
    var list = mutableListOf<MapPoint>()
    var dataList = ArrayList<MapPoint>()
    val searchPOI = SearchPOI()

    fun FindAroundTask(context: Context, tMapView: TMapView){
        this.context = context
        this.tmapView = tMapView
    }




    override fun doInBackground(vararg params: TMapPoint?): ArrayList<MapPoint> {
        tmapdata.findAroundNamePOI(params[0],"편의점;은행",1,99, TMapData.FindAroundNamePOIListenerCallback {
            var a = 1
            for (item in it){
                Log.d("편의 시설 : ", "POI name :  ${item.poiName}, POI Address : ${item.poiAddress}, POI point : ${item.poiPoint}")
                poiList.add(MapPoint(item.poiName,item.poiPoint.latitude,item.poiPoint.longitude))  //저장
            }
            Log.d("편의시설 갯수 : ", "${poiList.size}")

            mapPointRepository.mapPointList({
                it.forEach {
                    //RecyclerView Model로 연결해야지.

                }
            },99)




        })

        return poiList

    }
}