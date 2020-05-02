package com.example.kotlin_tmap.ui.home

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_tmap.MainActivity
import com.example.kotlin_tmap.R
import com.example.kotlin_tmap.base.App
import com.example.kotlin_tmap.ui.home.adapter.PointRecyclerAdapter
import com.example.kotlin_tmap.ui.home.presenter.HomeContract
import com.example.kotlin_tmap.ui.home.presenter.HomePresenter
import com.example.kotlin_tmap.util.permission
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), HomeContract.View {

    companion object{
        val context = App.instance.context()
        val locationManager : LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private val homePresenter : HomePresenter by lazy {
        val context = App.instance.context()

        HomePresenter(context, requireActivity(),  pointRecyclerAdapter ,this@HomeFragment)
    }

    private val pointRecyclerAdapter : PointRecyclerAdapter by lazy{
        PointRecyclerAdapter(requireContext())
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading()

        val mapView = TMapView(context) //mapview 재정의

        val linearLayoutManager = LinearLayoutManager(this@HomeFragment.context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        recycler_view.run {
            adapter = pointRecyclerAdapter
            layoutManager = linearLayoutManager
            isNestedScrollingEnabled = true
            setHasFixedSize(true)

        }
        
        /*지도 기본 정보 로드*/
        homePresenter.loadMap(mapView)
        showMap(mapView)

        /*Permission Check*/
        val permission = permission()
        val status : Boolean = permission.checkStatus(requireContext())

        if (status){ homePresenter.loadMyLocation(mapView)}
        else{ Toast.makeText(context,"위치 권한 동의 먼저",Toast.LENGTH_SHORT).show()}


        hideLoading()

        /*주변 편의점 찾기 클릭 이벤트*/
        btnFind.setOnClickListener{
            val tMapPoint : TMapPoint = mapView.centerPoint
            homePresenter.loadAroundData(requireContext(),mapView,tMapPoint) //호출
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

    override fun onDestroyView() {
        super.onDestroyView()
        val linear : LinearLayout = HomeMapView
        linear.removeAllViews()
    }
}
