package com.example.kotlin_tmap.ui.home.presenter

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.kotlin_tmap.MainActivity
import com.example.kotlin_tmap.base.App
import com.example.kotlin_tmap.ui.home.HomeFragment
import com.skt.Tmap.TMapGpsManager
import com.skt.Tmap.TMapView

class HomePresenter(val context: Context,
                    val activity: Activity,
                    val view : HomeContract.View) : HomeContract.Presenter{



    private val requiredPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )


    override fun loadMap(mapView : TMapView) {
        view.showLoading()

        mapView.run {
            setSKTMapApiKey("l7xx018af8f0f00743e8ac9cc583dcbf19d4")
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
            Log.d("테스트 ", "Presenter의 Mylocation")
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
                Log.d("맵 테스트","$latitude // ${longitude}")
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
                Log.d("테스트 ", "권한이 없어 추가")
            }
        }
        //거절된 퍼미션이 있다면...
        if (rejectedPermissionList.isNotEmpty()) {
            Log.d("테스트 ", "권한이 없어 요청")
            //권한 요청
            val array = arrayOfNulls<String>(rejectedPermissionList.size)

            ActivityCompat.requestPermissions(context as Activity, rejectedPermissionList.toArray(array), 1)
        }else{
            Log.d("테스트 ", "권한이 있음")
        }
    }
}