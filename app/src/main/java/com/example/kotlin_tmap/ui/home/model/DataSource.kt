package com.example.kotlin_tmap.ui.home.model

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_tmap.base.App
import com.example.kotlin_tmap.data.MapPoint
import com.example.kotlin_tmap.ui.home.adapter.model.PointRecyclerModel
import com.example.kotlin_tmap.ui.home.presenter.HomeContract
import com.example.kotlin_tmap.ui.home.presenter.HomePresenter
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import java.lang.Exception

class DataSource(private val view : HomeContract.View,
              private val homePresenter: HomePresenter,
              private val pointRecyclerModel: PointRecyclerModel) : AppCompatActivity(), DataSourceInterface {

    companion object{
        var poiList = ArrayList<MapPoint>()
        var context = App.instance.context()
        private val tmapdata = TMapData()
    }

    override fun FindAroundNamePOI(point : TMapPoint){
        poiList.clear()
        tmapdata.findAroundNamePOI(point,"편의점",1,49,
            TMapData.FindAroundNamePOIListenerCallback {
                try{
                    it.forEach {
                        poiList.add(MapPoint(it.poiName,it.poiPoint.latitude,it.poiPoint.longitude))  //저장
                    }
                    poiList.forEach {
                        pointRecyclerModel.addItem(it)
                    }

                    runOnUiThread(UIClass()) //UI핸들러 사용

                }catch (e : Exception){
                    e.printStackTrace()
                }
            })
    }

    inner class UIClass():Runnable{
        override fun run() {
            pointRecyclerModel.notifychange()
            homePresenter.putMarkeronMap(poiList)
            view.hideLoading()
        }
    }
}