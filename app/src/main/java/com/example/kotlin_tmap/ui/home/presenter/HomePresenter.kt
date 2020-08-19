package com.example.kotlin_tmap.ui.home.presenter

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.kotlin_tmap.R
import com.example.kotlin_tmap.base.App
import com.example.kotlin_tmap.data.MapPoint
import com.example.kotlin_tmap.ui.home.HomeFragment
import com.example.kotlin_tmap.ui.home.adapter.model.PointRecyclerModel
import com.example.kotlin_tmap.ui.home.model.DataSource
import com.example.kotlin_tmap.ui.home.model.DataSourceObject
import com.skt.Tmap.*

class HomePresenter(val context: Context,
                    private val activity: Activity,
                    private val pointRecyclerModel: PointRecyclerModel,
                    private val view : HomeContract.View) : HomeContract.Presenter{

    private val requiredPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    companion object{
        lateinit var mapsView : TMapView
    }


    private val dataSourceObject : DataSourceObject by lazy {
        DataSourceObject(view, this@HomePresenter, pointRecyclerModel)
    }

    override fun loadMap(mapView : TMapView) {
        view.showLoading()
        mapView.run {
            setSKTMapApiKey("l7xxb06b75b17d194ea8adcb83bfec85c97c")
            zoomLevel = 15
            mapType = TMapView.MAPTYPE_STANDARD
            setLanguage(TMapView.LANGUAGE_KOREAN)
            setCompassMode(true)
            setIconVisibility(true)
        }

    }

    override fun myLocation(tmapsGps : TMapGpsManager) {
        tmapsGps.run {
            minTime = 1000
            minDistance = 5F
            provider = TMapGpsManager.LOCATION_SERVICE
            provider  = TMapGpsManager.NETWORK_PROVIDER
            provider = TMapGpsManager.GPS_PROVIDER
            OpenGps()
        }
    }


    override fun loadMyLocation(mapView: TMapView) {
        val tmapGps = TMapGpsManager(activity)
        tmapGps.run {
            minTime = 1000
            minDistance = 5F
            provider = TMapGpsManager.LOCATION_SERVICE
            OpenGps()
        }

        val locationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                val latitude = location.latitude
                val longitude = location.longitude
                mapView.setCenterPoint(longitude, latitude)
                mapView.setLocationPoint(longitude, latitude)
            }
            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        HomeFragment.locationManager.run {
            checkPermission()
            requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
            requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)
        }

        mapView.run {
            setTrackingMode(true)
            setSightVisible(true)
        }
    }

    override fun checkPermission() {
        val rejectedPermissionList = ArrayList<String>() //거절되었거나 아직 수락하지 않은 권한(퍼미션)을 저장할 문자열 배열 리스트
        val context = App.instance.context()
        for (permission in requiredPermissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                //만약 권한이 없다면 rejectedPermissionList에 추가
                rejectedPermissionList.add(permission)
            }
        }
        //거절된 퍼미션이 있다면...
        if (rejectedPermissionList.isNotEmpty()) {
            //권한 요청
            val array = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(context as Activity, rejectedPermissionList.toArray(array), 1)
        }else{ //권한 있음
        }
    }



    //Async호출
    override fun loadAroundData(context: Context, mapView: TMapView, tmapPoint: TMapPoint) {
        pointRecyclerModel.refreshList() //기존 리스트 초기화
        view.showLoading()
        dataSourceObject.FindAroundNamePOI(tmapPoint)
        mapsView = mapView
    }

    override fun putMarkeronMap(point: ArrayList<MapPoint>) { //가져온 정보 지도에 뿌려주기.
        var i = 0
        point.forEach {
            val bitmapIcon = BitmapFactory.decodeResource(context.resources, R.drawable.boxicon)

            val markerItem = TMapMarkerItem()
            markerItem.run {
                icon = bitmapIcon
                tMapPoint = TMapPoint(it.latitude, it.longitude)
                name = it.name
            }

            mapsView.addMarkerItem("markerItem $i", markerItem)
            i++
        }
        val centerPoint : TMapPoint = mapsView.centerPoint //현재 보이는 맵 가운데 좌표 가져오기.
        mapsView.setCenterPoint(centerPoint.longitude,centerPoint.latitude,true)
    }
}