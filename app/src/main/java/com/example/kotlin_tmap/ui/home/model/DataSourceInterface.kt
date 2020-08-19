package com.example.kotlin_tmap.ui.home.model

import com.skt.Tmap.TMapPoint

interface DataSourceInterface {

    fun FindAroundNamePOI(point : TMapPoint)
}