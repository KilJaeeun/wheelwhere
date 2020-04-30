package com.example.kotlin_tmap.Async

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.example.kotlin_tmap.base.App
import com.example.kotlin_tmap.data.MapPoint
import com.example.kotlin_tmap.data.mapData.MapPointData
import com.example.kotlin_tmap.map.SearchPOI
import com.example.kotlin_tmap.ui.home.adapter.model.PointRecyclerModel
import com.example.kotlin_tmap.ui.home.presenter.HomePresenter
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import java.lang.Exception

class FindAroundTask(private val homePresenter: HomePresenter,
                     private val pointRecyclerModel: PointRecyclerModel) : AsyncTask<TMapPoint, Void, ArrayList<MapPoint>>(){
    var context = App.instance.context()
    var tmapView = TMapView(context)
    val tmapdata = TMapData()

    var mapPoint : MapPoint?= null

    var list = mutableListOf<MapPoint>()
    var dataList = ArrayList<MapPoint>()
    val searchPOI = SearchPOI()
    val activity = Activity()

    val mapPointData = MapPointData()

    fun FindAroundTask(context: Context, tMapView: TMapView){
        this.context = context
        this.tmapView = tMapView
    }

    companion object{
        var poiList = ArrayList<MapPoint>()

    }

    override fun doInBackground(vararg params: TMapPoint?): ArrayList<MapPoint> {
        try{
            val list = ArrayList<MapPoint>()

            poiList.clear()//최기화 하고 시작.

            tmapdata.findAroundNamePOI(params[0],"편의점;은행",1,49, TMapData.FindAroundNamePOIListenerCallback { it ->
                it.forEach {
                    Log.d("편의 시설 : ", "POI name :  ${it.poiName}, POI point : ${it.poiPoint}")
                    poiList.add(MapPoint(it.poiName,it.poiPoint.latitude,it.poiPoint.longitude))  //저장
                    //homePresenter.addDataList(poiList)
                }

                Log.d("편의시설 갯수 : ", "${poiList.size}")
            })

            Log.d("리턴 이후 테스트","$poiList.size")
            return poiList

        }catch (e: Exception){
            e.printStackTrace()
            return poiList
        }
    }

    override fun onPostExecute(result: ArrayList<MapPoint>?) {
        super.onPostExecute(result)

        //null 허용
        result?.forEach {
            Log.d("OnPostExecute : " , "${it.name} // ${it.latitude} //${it.longitude} }")
        }
    }


}