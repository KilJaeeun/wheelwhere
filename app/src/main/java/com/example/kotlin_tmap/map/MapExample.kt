package com.example.kotlin_tmap.map

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_tmap.*
import com.example.kotlin_tmap.base.App
import com.example.kotlin_tmap.util.permission
import com.skt.Tmap.*
import kotlinx.android.synthetic.main.activity_map_example.*


class MapExample : AppCompatActivity(), TMapGpsManager.onLocationChangedCallback {

    val context = App.instance.context()
    val tmapview = TMapView(context)


    //퍼미션 사용
    private lateinit var permission: permission
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_example)

        permission = permission()
        permission.checkPermission(this)

        //Tmap을 APIkey로 사용하기 위한 설정
        tmapview.setSKTMapApiKey("l7xxb06b75b17d194ea8adcb83bfec85c97c")
        tmapview.zoomLevel = 15
        tmapview.mapType = TMapView.MAPTYPE_STANDARD
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN)
        mapLinear.addView(tmapview)

        //현재 보는 방향
        tmapview.setCompassMode(true)

        //현위치 아이콘 표시
        tmapview.setIconVisibility(true)


        val markerItem = TMapMarkerItem()
        val bitmapIcon = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.boxicon
        )
        val tMapPoint = TMapPoint(37.579600, 126.976998)
        //  val tMapOverlay = TMapOverlay()
        // val strID = "TMapMarkerItem2"

//        val mOverlay =  ImageOverlay(this,tmapview )
//        class MapOverlay : TMapOverlayItem() {
//            override fun draw(canvas: Canvas?, mapView: TMapView?, showCallout: Boolean) {}
//        }
        btnMove.setOnClickListener {
            markerItem.icon = bitmapIcon
            markerItem.setPosition(0.5f, 1.0f)
            markerItem.tMapPoint = tMapPoint
            markerItem.name = "경복궁"
            item_name.setText("경복궁")
            item_location.setText("서울특별시 종로구 세종로 사직로 161")
            tmapview.addMarkerItem("markerItem", markerItem)
            tmapview.setCenterPoint(126.976998, 37.579600, true)
            location_card.setVisibility(LinearLayout.VISIBLE)
            
        }

        buttonZoomIn.setOnClickListener {
            tmapview.MapZoomIn()
        }

        buttonZoomOut.setOnClickListener {
            tmapview.MapZoomOut()
        }



        btnMyLocation.setOnClickListener {
            val tmapgps = TMapGpsManager(this)
            tmapgps.minTime = 1000 //얼마 간격으로 받아올지
            tmapgps.minDistance = 5F //얼마 정도 거리 차이 허용
            //tmapgps.provider  = TMapGpsManager.NETWORK_PROVIDER
            //연결된 인터넷으로 현 위치를 받는다. 실내일 때 유용.


            tmapgps.provider = TMapGpsManager.LOCATION_SERVICE


            tmapgps.OpenGps()

            //단말기의 현재 위치리를 화면 중심에 표시
            tmapview.setTrackingMode(true)
            tmapview.setSightVisible(true)
        }

        btn_map_of_map.setOnClickListener {
            val intent = Intent(this, MapExample::class.java)
            startActivity(intent)
            finish()
        }

        btn_list_of_map.setOnClickListener {
            val intent = Intent(this, PlaceList::class.java)
            startActivity(intent)
            finish()
        }
       btn_my_of_map.setOnClickListener {
            val intent = Intent(this, MyPage::class.java)
            startActivity(intent)
            finish()
        }

    }


    //권한 요청 결과 함수
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        permission.handlePermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onLocationChange(p0: Location?) {
        if (p0 != null) {
            tmapview.setLocationPoint(p0.longitude, p0.latitude)
        }
    }
}
