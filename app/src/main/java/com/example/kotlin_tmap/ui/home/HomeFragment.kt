package com.example.kotlin_tmap.ui.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.example.kotlin_tmap.Async.FindAroundTask
import com.example.kotlin_tmap.MainActivity
import com.example.kotlin_tmap.R
import com.example.kotlin_tmap.base.App
import com.example.kotlin_tmap.data.MapPoint
import com.example.kotlin_tmap.data.mapData.MapPointRepository
import com.example.kotlin_tmap.ui.home.presenter.HomeContract
import com.example.kotlin_tmap.ui.home.presenter.HomePresenter
import com.example.kotlin_tmap.util.permission
import com.skt.Tmap.TMapGpsManager
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), HomeContract.View {
    companion object{
        val context = App.instance.context()
        val locationManager : LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private val homePresenter : HomePresenter by lazy {
        val context = App.instance.context()
        HomePresenter(context, requireActivity(),this@HomeFragment)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.fragment_home, container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading()

        //view가 나타낸 이후 행동.
        val mapView = TMapView(requireContext())
        homePresenter.loadMap(mapView)
        showMap(mapView)

        val permission = permission()
        val status : Boolean = permission.checkStatus(requireContext())

        if (status){
            homePresenter.loadMyLocation(mapView) //내 위치 로드
        }else{
            Toast.makeText(context,"위치 권한 동의 먼저",Toast.LENGTH_SHORT).show()
        }

        hideLoading()


        btnFind.setOnClickListener{
            val tMapPoint : TMapPoint = mapView.centerPoint

            val asyncAround : FindAroundTask? = FindAroundTask(MapPointRepository) //asyncPath를 지정 하고 FindAroundTask init
            asyncAround!!.FindAroundTask(requireContext(),mapView) //context와 mapview 넘겨줌.

            asyncAround.execute(tMapPoint)
        }
    }


    

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showMap(mapView :TMapView) {
        HomeMapView.addView(mapView)
}




}
