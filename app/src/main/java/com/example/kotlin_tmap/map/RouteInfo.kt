package com.example.kotlin_tmap.map

import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.kotlin_tmap.Async.FindPathTask
import com.example.kotlin_tmap.R
import com.example.kotlin_tmap.base.App
import com.example.kotlin_tmap.util.permission
import com.skt.Tmap.*
import kotlinx.android.synthetic.main.activity_route_info.*
import java.security.Permission

class RouteInfo : AppCompatActivity() , TMapGpsManager.onLocationChangedCallback {
    
    //Global applicationContext 사용
    val context = App.instance.context()
    
    //지도 초기화
    val mapView = TMapView(context)

    private lateinit var Distance : String //버튼 클릭시 루트그려주고 거리 나타낼때 저장할 값
    private lateinit var permission : permission

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_info)

        permission = permission()
        permission.checkPermission(this)

        //Tmap을 APIkey로 사용하기 위한 설정
        mapView.setSKTMapApiKey("l7xx018af8f0f00743e8ac9cc583dcbf19d4")
        mapView.zoomLevel = 15
        mapView.mapType = TMapView.MAPTYPE_STANDARD
        mapView.setLanguage(TMapView.LANGUAGE_KOREAN)
        routeMapView.addView(mapView)

        //현재 보는 방향
        mapView.setCompassMode(true)

        //현위치 아이콘 표시
        mapView.setIconVisibility(true)


        //SKT타워부터 종각역까지 찍기
        btnRoute.setOnClickListener {
            val asyncPath : FindPathTask? = FindPathTask() //asyncPath를 지정 하고 FindPathTask로 init
            asyncPath!!.FindPathTask(context,mapView) //context와 mapview 넘겨줌.

            Distance = asyncPath.execute().toString() //실행. //현재는 위치 좌표를 넘겨서 보지 않음으로 그냥 execute

            //Distance에 대해선 다시 고칠 예정
            tvDistance.text = Distance

            mapView.setCenterPoint(126.985302,37.570841)
        }

        //내 위치로 이동하기
        btnMyLocation.setOnClickListener {
            val tmapgps = TMapGpsManager(this)
            tmapgps.minTime = 1000 //얼마 간격으로 받아올지
            tmapgps.minDistance = 5F //얼마 정도 거리 차이 허용

            tmapgps.provider = TMapGpsManager.LOCATION_SERVICE

            tmapgps.OpenGps()

            //단말기의 현재 위치리를 화면 중심에 표시
            mapView.setTrackingMode(true)
            mapView.setSightVisible(true)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        permission.handlePermissionsResult(requestCode,permissions,grantResults)
    }

    override fun onLocationChange(p0: Location?) {
        if (p0 != null) {
            mapView.setLocationPoint(p0.longitude, p0.latitude)
        }
    }
}
