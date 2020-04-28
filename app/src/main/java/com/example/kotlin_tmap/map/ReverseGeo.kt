package com.example.kotlin_tmap.map

import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.kotlin_tmap.Async.ReverseTask
import com.example.kotlin_tmap.R
import com.example.kotlin_tmap.base.App
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.activity_map_example.*
import kotlinx.android.synthetic.main.activity_reverse_geo.*
import java.lang.Exception

class ReverseGeo : AppCompatActivity() {

    val context = App.instance.context()
    val geoMap = TMapView(context)

    //비동기 결과값 저장 할 곳
    private  lateinit var result:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reverse_geo)

        //Tmap을 APIkey로 사용하기 위한 설정
        geoMap.setSKTMapApiKey("l7xx018af8f0f00743e8ac9cc583dcbf19d4")
        geoMap.zoomLevel = 15
        geoMap.mapType = TMapView.MAPTYPE_STANDARD
        geoMap.setLanguage(TMapView.LANGUAGE_KOREAN)
        GeoMap.addView(geoMap) //Linear에 세팅

        //현재 보는 방향
        geoMap.setCompassMode(true)

        //현위치 아이콘 표시
        geoMap.setIconVisibility(true)
        
        //주소를 얻어올 위도 경도 설정 => 나중에 바꿔야지
        //val tMapPoint = TMapPoint(37.579600, 126.976998)




        //버튼 클릭 시 동작
        btnReverse.setOnClickListener {

            val asyncReverse : ReverseTask? = ReverseTask() //asyncPath를 지정 하고 FindPathTask로 init
            asyncReverse!!.ReverseTask(context,geoMap) //context와 mapview 넘겨줌.

            //현재 보이는 지도의 가운데 지점을 구해옴
            var tMapPoint : TMapPoint = geoMap.centerPoint

            try {
                //get을 통해 결과를 가져올 경우, 혹여나 처리양이 많다면 리턴값을 늦게 받아올수도 있기에 try/catch 감싼다.
                result = asyncReverse.execute(tMapPoint).get()
            }catch (e:Exception){
                e.printStackTrace()
            }

            //Async로 받아온 주소를 표시
            tvResultGeo.text = result
        }




    }
}
