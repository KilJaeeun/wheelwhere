package com.example.kotlin_tmap.map

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.example.kotlin_tmap.Async.FindAroundTask
import com.example.kotlin_tmap.Async.FindPoiTask
import com.example.kotlin_tmap.R
import com.example.kotlin_tmap.base.App
import com.example.kotlin_tmap.data.MapPoint
import com.example.kotlin_tmap.data.mapData.MapPointRepository
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.activity_reverse_geo.*
import kotlinx.android.synthetic.main.activity_search_p_o_i.*


class SearchPOI : AppCompatActivity() {
    val context = App.instance.context()
    val poiMap = TMapView(context)

    private lateinit var strData : String
    private var poiItem : ArrayList<TMapPOIItem>?= null
    private var item = TMapPOIItem()
    var dataList = ArrayList<MapPoint>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_p_o_i)


        //Tmap을 APIkey로 사용하기 위한 설정
        poiMap.setSKTMapApiKey("l7xxb06b75b17d194ea8adcb83bfec85c97c")
        poiMap.zoomLevel = 15
        poiMap.mapType = TMapView.MAPTYPE_STANDARD
        poiMap.setLanguage(TMapView.LANGUAGE_KOREAN)
        mapViewPoi.addView(poiMap) //Linear에 세팅

        //현재 보는 방향
        poiMap.setCompassMode(true)

        //현위치 아이콘 표시
        poiMap.setIconVisibility(true)

        //내 위치 주변으로 검색 데이터 찾기
        btnSearch.setOnClickListener {
            strData = etSearchWord.text.toString()

            val asyncPOI : FindPoiTask? = FindPoiTask() //asyncPath를 지정 하고 FindPathTask로 init
            asyncPOI!!.FindPoiTask(context,poiMap) //context와 mapview 넘겨줌.

            asyncPOI.execute(strData)
        }


        //내 위치 주변으로 편의 시설(편의점,은행) 찾기
        btnAround.setOnClickListener {
            val tMapPoint : TMapPoint = poiMap.centerPoint

            /*val asyncAround : FindAroundTask? = FindAroundTask() //asyncPath를 지정 하고 FindAroundTask init
            asyncAround!!.FindAroundTask(context,poiMap) //context와 mapview 넘겨줌.

            dataList.clear()
            dataList = ArrayList<MapPoint>()
            val handler = Handler()
            dataList = asyncAround.execute(tMapPoint).get()
*/
        }


    }
}
