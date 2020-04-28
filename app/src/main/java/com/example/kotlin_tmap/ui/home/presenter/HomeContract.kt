package com.example.kotlin_tmap.ui.home.presenter

import android.app.Activity
import android.content.Context
import com.skt.Tmap.TMapGpsManager
import com.skt.Tmap.TMapView

interface HomeContract {

    interface View{
        fun showLoading()
        fun hideLoading()
        fun showMap(mapView: TMapView)
    }

    interface Presenter {
        fun loadMap(mapView: TMapView)
        fun myLocation(tmapsGps: TMapGpsManager)
        fun loadMyLocation(mapView: TMapView)
        fun checkPermission()
    }
}