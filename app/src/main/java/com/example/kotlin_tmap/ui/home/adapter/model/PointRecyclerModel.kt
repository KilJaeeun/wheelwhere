package com.example.kotlin_tmap.ui.home.adapter.model

import com.example.kotlin_tmap.data.MapPoint

interface PointRecyclerModel{

    fun addItem(pointData : MapPoint)

    fun refreshList()

    fun notifychange()

}